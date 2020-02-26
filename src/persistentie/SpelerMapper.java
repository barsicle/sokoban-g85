package persistentie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domein.Speler;

public class SpelerMapper {

	// Properties
	// Establish connection with database
	private static final String JDBC_URL = "jdbc:mysql://ID222177_g85.db.webhosting.be?serverTimezone=UTC&useLegacyDatetimeCode=false&user=ID222177_g85&password=fi4pinVo";
	// Use this string to insert a player into the database
	private static final String INSERT_SPELER = "INSERT INTO ID222177_g85.speler(naam, voornaam, gebruikersnaam, wachtwoord, adminrechten)"
			+ "VALUES (?, ?, ?, ?, ?)";
	private static final String GET_SPELER = "SELECT * FROM ID222177_g85.speler WHERE gebruikersnaam = ?";

	// Methods
	public void voegSpelerToe(Speler speler) throws RuntimeException {

		try {
			Connection conn = DriverManager.getConnection(JDBC_URL);

			PreparedStatement query = conn.prepareStatement(INSERT_SPELER);

			conn.setAutoCommit(false);
			query.setString(1, speler.getNaam());
			query.setString(2, speler.getVoornaam());
			query.setString(3, speler.getGebruikersnaam());
			query.setString(4, speler.getWachtwoord());
			query.setBoolean(5, speler.isAdminrechten());
			query.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}


	}

	public Speler geefSpeler(String gebruikersnaam) throws RuntimeException {
		
		Speler speler = null;

		try {
		Connection conn = DriverManager.getConnection(JDBC_URL);
		PreparedStatement query = conn.prepareStatement(GET_SPELER);

		query.setString(1, gebruikersnaam.toLowerCase());

		ResultSet rs = query.executeQuery();

		if (rs.next()) {

			String naam = rs.getString("naam");
			String voornaam = rs.getString("voornaam");
			String wachtwoord = rs.getString("wachtwoord");
			boolean adminrechten = rs.getBoolean("adminrechten");

			speler = new Speler(naam, voornaam, gebruikersnaam, wachtwoord, adminrechten);
			return speler;
		}
		} catch (SQLException | IllegalArgumentException e) {
			throw new RuntimeException(e);
		}
		
		return speler;
	}

}
