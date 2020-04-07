package persistentie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domein.Speler;

//UC2
/**
 * Stelt de mapper voor die met de database communiceert.
 * @author g85
 */
public class SpelerMapper {

	// Use this string to insert a player into the database
	private static final String INSERT_SPELER = "INSERT INTO ID222177_g85.speler(naam, voornaam, gebruikersnaam, wachtwoord, adminrechten)"
			+ "VALUES (?, ?, ?, ?, ?)";
	private static final String GET_SPELER = "SELECT * FROM ID222177_g85.speler WHERE gebruikersnaam = ?";

	// Methods
	//UC2
	/**
	 * Voegt de meegegeven speler toe aan de database. 
	 * 
	 * @param speler De speler die toegevoegd wordt aan de database.
	 * @throws RuntimeException
	 */
	public void voegSpelerToe(Speler speler) throws RuntimeException {

		try {
			Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);

			PreparedStatement query = conn.prepareStatement(INSERT_SPELER);

			conn.setAutoCommit(false);
			query.setString(1, speler.getNaam());
			query.setString(2, speler.getVoornaam());
			query.setString(3, speler.getGebruikersnaam());
			query.setString(4, speler.getWachtwoord());
			query.setBoolean(5, speler.hasAdminrechten());
			query.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}


	}
	//UC2
	/**
	 * Geeft de speler met meegegeven gebruikersnaam terug uit de database.
	 * Werpt een RuntimeException indien er problemen zijn met de database.
	 * 
	 * @param gebruikersnaam De gebruikersnaam die wordt gebruikt om de speler op te zoeken.
	 * @return De speler met gegeven gebruikersnaam.
	 * @throws RuntimeException
	 */
	public Speler geefSpeler(String gebruikersnaam) throws RuntimeException {
		
		Speler speler = null;

		try {
		Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
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
