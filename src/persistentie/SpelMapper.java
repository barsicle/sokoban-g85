package persistentie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domein.Spel;
import domein.Spelbord;
/**
 * Stelt de mapper voor die met de database communiceert.
 * @author g85
 */
public class SpelMapper {

	private static final String GET_SPEL = "SELECT * FROM ID222177_g85.spel WHERE spel.spelNaam = ?";
	private static final String GET_SPELLEN = "SELECT * FROM ID222177_g85.spel";
	private static final String INSERT_SPEL = "INSERT INTO ID222177_g85.spel(spelNaam, speler) VALUES (?, ?)";
	/**
	 * Geeft het spel met de gegeven naam terug uit de database. Werpt een RuntimeException indien er een probleem is met de database.
	 * @param spelNaam De naam van het op te halen spel.
	 * @return het spel met de gegeven spelnaam.
	 * @throws RuntimeException indien er een probleem is met de database.
	 */		
	public Spel geefSpel(String spelNaam) throws RuntimeException {
		Spel spel = null;

		try {
		Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
		PreparedStatement query = conn.prepareStatement(GET_SPEL);
		query.setString(1, spelNaam);

		ResultSet rs = query.executeQuery();
		
		if (rs.next()) {
			spel = new Spel(spelNaam, new ArrayList<Spelbord>());
		}
		} catch (SQLException | IllegalArgumentException e) {
			throw new RuntimeException(e);
		}
		
		
		
		return spel;
	}
	/**
	 * Geeft een lijst van de namen van de spelen terug uit de database. 
	 * @return het spel met de gegeven spelnaam.
	 */		
	public List<String> getSpelNamen() {
		List<String> spellen = new ArrayList<>();

		try {
		Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
		PreparedStatement query = conn.prepareStatement(GET_SPELLEN);

		ResultSet rs = query.executeQuery();

		while (rs.next()) {
			String spelNaam = rs.getString("spelNaam");
			spellen.add(spelNaam);
		}
		} catch (SQLException | IllegalArgumentException e) {
			throw new RuntimeException(e);
		}
		
		return spellen;
	}
	/**
	 * Insert het gegeven spel in de database. Werpt een RuntimeException indien er een probleem is met de database.
	 * @param spel Het te inserten spel.
	 * @throws RuntimeException indien er een probleem is met de database.
	 */		
	public void insertSpel(Spel spel) throws RuntimeException {
		try {
		Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
		PreparedStatement query = conn.prepareStatement(INSERT_SPEL);

		conn.setAutoCommit(false);
		query.setString(1, spel.getSpelNaam());
		query.setString(2, spel.getAanmaker().getGebruikersnaam());
		query.executeUpdate();
		conn.commit();
		} catch (SQLException | IllegalArgumentException e) {
			throw new RuntimeException(e);
		}
	}

}
