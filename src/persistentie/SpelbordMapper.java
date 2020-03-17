package persistentie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domein.Kist;
import domein.Mannetje;
import domein.Moveable;
import domein.Spelbord;
import domein.Veld;
import domein.VeldType;

public class SpelbordMapper {
	private static final String GET_SPELBORDEN = "SELECT * FROM ID222177_g85.spelbord WHERE spelNaam = ?";
	private static final String GET_SPELBORD = "SELECT * FROM ID222177_g85.spelbord INNER JOIN ID222177_g85.veld ON spelbord.spelbordNaam = veld.spelbordNaam WHERE spelbord.spelbordNaam = ?";
	
	public Spelbord geefBordMetVelden(String spelbordNaam) throws RuntimeException {
		Veld[][] velden = new Veld[10][10];
		
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
}
