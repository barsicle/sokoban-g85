package persistentie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import domein.Kist;
import domein.Mannetje;
import domein.Moveable;
import domein.Spelbord;
import domein.Veld;
import domein.VeldType;

public class SpelbordMapper {
	private static final String GET_SPELBORDEN = "SELECT * FROM ID222177_g85.spelbord WHERE spelNaam = ?";
	private static final String GET_SPELBORD = "SELECT * FROM ID222177_g85.spelbord INNER JOIN ID222177_g85.veld ON spelbord.spelbordNaam = veld.spelbordNaam WHERE spelbord.spelbordNaam = ?";
	private static final String INSERT_SPELBORD = "INSERT INTO ID222177_g85.spelbord(spelbordNaam,volgorde,spelNaam) VALUES(?, ?, ?)";
	private static final String INSERT_VELDEN = "INSERT INTO ID222177_g85.veld(x,y,doel,veldType,moveable,spelbordNaam) VALUES(?, ?, ?, ?, ?, ?)";
	
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
					moveable = new Kist(veld);
					kisten.add(moveable);
				} else if (moveableType == 2) {
					moveable = new Mannetje(veld);
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
	
	public void insertBord(Spelbord spelbord) throws RuntimeException {
		try {
			Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
			conn.setAutoCommit(false);

			//Bord zelf inserten
			PreparedStatement query = conn.prepareStatement(INSERT_SPELBORD);

			query.setString(1, spelbord.getSpelbordNaam());
			query.setInt(2, spelbord.getVolgorde());
			query.setString(3, spelbord.getSpel().getSpelNaam());
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
			if(moveable instanceof Kist){
				type = 1;
			} else if (moveable instanceof Mannetje) {
				type = 2;
			}
		}

		return type;
	}
	
}
