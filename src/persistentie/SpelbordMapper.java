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
	private static final String GET_SPELBORD = "SELECT * FROM ID222177_g85.spelbord INNER JOIN ID222177_g85.veld ON spelbord.spelbordId = veld.spelbordId WHERE spelbord.spelbordNaam = ?";
	
	public Spelbord geefBordMetVelden(String spelbordNaam) throws RuntimeException {
		Veld[][] velden = new Veld[10][10];
		int volgorde = 0;
		Spelbord bord = null;
		List<Moveable> kisten = new ArrayList<>();
		Moveable mannetje = null;

		try {
		Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
		PreparedStatement query = conn.prepareStatement(GET_SPELBORD);
		query.setString(1, spelbordNaam);

		ResultSet rs = query.executeQuery();

		while (rs.next()) {
			if(volgorde != 0) {
				volgorde = rs.getInt("volgorde");
			}
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
		}
		} catch (SQLException | IllegalArgumentException e) {
			throw new RuntimeException(e);
		}
		
		bord = new Spelbord(spelbordNaam, volgorde, mannetje, kisten, velden);
		
		return bord;
	}
}
