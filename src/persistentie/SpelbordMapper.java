package persistentie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import domein.Moveable;
import domein.MoveableType;
import domein.Spelbord;
import domein.Veld;
import domein.VeldType;
/**
 * Stelt de mapper voor die met de database communiceert.
 * @author g85
 */
public class SpelbordMapper {
	private static final String GET_SPELBORDEN = "SELECT * FROM ID222177_g85.spelbord WHERE spelNaam = ?";
	private static final String GET_SPELBORD = "SELECT * FROM ID222177_g85.spelbord INNER JOIN ID222177_g85.veld ON spelbord.spelbordNaam = veld.spelbordNaam WHERE spelbord.spelbordNaam = ?";
	private static final String INSERT_SPELBORD = "INSERT INTO ID222177_g85.spelbord(spelbordNaam,volgorde,spelNaam) VALUES(?, ?, ?)";
	private static final String INSERT_VELDEN = "INSERT INTO ID222177_g85.veld(x,y,doel,veldType,moveable,spelbordNaam) VALUES(?, ?, ?, ?, ?, ?)";
	/**
	 * Geeft het spelbord met de gegeven naam en de gegeven dimensies terug uit de database. Werpt een RuntimeException indien er een probleem is met de database.
	 * @param spelbordNaam De naam van het op te halen spelbord.
	 * @param x Het aantal rijen van het spelbord.
	 * @param y Het aantal kolommen van het spelbord.
	 * @return het spelbord met de gegeven spelbordnaam en dimensies.
	 * @throws RuntimeException
	 */		
	public Spelbord geefBordMetVelden(String spelbordNaam, int aantalRijen, int aantalKolommen) throws RuntimeException {
		Veld[][] velden = new Veld[aantalRijen][aantalKolommen];
		
		Spelbord bord = null;
		List<Moveable> kisten = new ArrayList<>();
		Moveable mannetje = null;

		try {
		Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
		PreparedStatement query = conn.prepareStatement(GET_SPELBORD);
		query.setString(1, spelbordNaam);

		ResultSet rs = query.executeQuery();

		while (rs.next()) {
			int volgorde = rs.getInt("volgorde");
			VeldType type = VeldType.MUUR;
			// Indien een veld, override
			if (rs.getBoolean("veldType")) {
				type = VeldType.VELD;
			}
			int x = rs.getInt("x");
			int y = rs.getInt("y");
			Veld veld = new Veld(type, rs.getBoolean("doel"), x, y);
			
			// Indien er een moveable is, create die en maak wederzijdse link
			Moveable moveable = null;
			int moveableType = rs.getInt("moveable");
			if (moveableType != 0) {
				if(moveableType == 1) {
					moveable = new Moveable(veld, MoveableType.KIST);
					kisten.add(moveable);
				} else if (moveableType == 2) {
					moveable = new Moveable(veld, MoveableType.MANNETJE);
					mannetje = moveable;
				}
			}
			veld.setMoveable(moveable);
			
			//Inserteer veld op juiste plaats in de array
			velden[x][y] = veld;
			
			bord = new Spelbord(spelbordNaam, volgorde, mannetje, kisten, velden);
		}
		} catch (SQLException | IllegalArgumentException e) {
			throw new RuntimeException(e);
		}
		
		
		
		return bord;
	}
	/**
	 * Geeft een lijst van de spelborden van het spel met gegeven spelnaam terug uit de database. 
	 * Werpt een RuntimeException indien er een probleem is met de database.
	 * @param spelNaam De naam van het spel waarvan de spelborden worden opgehaald.
	 * @return een lijst van spelborden van het spel met de gegeven spelnaam.
	 */		
	public List<Spelbord> geefBorden(String spelNaam) throws RuntimeException {
		List<Spelbord> borden = new ArrayList<>();
		try {
		Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
		PreparedStatement query = conn.prepareStatement(GET_SPELBORDEN);
		query.setString(1, spelNaam);

		ResultSet rs = query.executeQuery();

		while (rs.next()) {
			String spelbordNaam = rs.getString("spelbordNaam");
			int volgorde = rs.getInt("volgorde");
			Spelbord bord = new Spelbord(spelbordNaam, volgorde);
			borden.add(bord);
		}
		} catch (SQLException | IllegalArgumentException e) {
			throw new RuntimeException(e);
		}
		
		return borden;
	}
	/**
	 * Insert het gegeven spelbord van het gegeven spel in de database. Werpt een RuntimeException indien er een probleem is met de database.
	 * @param spelBord Het te inserten spelBord.
	 * @param spelNaam De naam van het spel van het te inserteren spelbord.
	 * @throws RuntimeException
	 */	
	public void insertBord(Spelbord spelbord, String spelNaam) throws RuntimeException {
		try {
			Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
			conn.setAutoCommit(false);

			//Bord zelf inserten
			PreparedStatement query = conn.prepareStatement(INSERT_SPELBORD);

			query.setString(1, spelbord.getSpelbordNaam());
			query.setInt(2, spelbord.getVolgorde());
			query.setString(3, spelNaam);
			query.executeUpdate();
			
			//Velden inserten
			query = conn.prepareStatement(INSERT_VELDEN);
			
			Veld[][] velden = spelbord.getVelden();
			for (Veld[] kolom : velden) {
				for (Veld rij : kolom) {
					if (!Objects.equals(rij, null)) {
						query.setInt(1, rij.getX());
						query.setInt(2, rij.getY());
						query.setBoolean(3, rij.isDoel());
						boolean type = false;
						if (rij.getVeldType().equals(VeldType.VELD)) {
							type = true;
						}
						query.setBoolean(4, type);
						query.setInt(5, convertMoveableToInt(rij.getMoveable()));
						query.setString(6, spelbord.getSpelbordNaam());
						query.executeUpdate();
					}
				}
			}
			
			conn.commit();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	private int convertMoveableToInt(Moveable moveable) {
		int type = 0;
		if(!Objects.equals(moveable, null)) {
			if(moveable.getType().equals(MoveableType.KIST)){
				type = 1;
			} else if (moveable.getType().equals(MoveableType.MANNETJE)) {
				type = 2;
			}
		}

		return type;
	}
	
}
