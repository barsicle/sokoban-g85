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
import domein.SpelbordRepository;
import domein.Speler;

public class SpelMapper {

	private static final String GET_SPEL = "SELECT * FROM ID222177_g85.spel INNER JOIN ID222177_g85.spelbord ON spel.spelId = spelbord.spelId WHERE spel.spelNaam = ?";
	private static final String GET_SPELLEN = "SELECT * FROM ID222177_g85.spel";

	public Spel geefSpel(String spelNaam) throws RuntimeException {

		List<Spelbord> spelBorden = new ArrayList<>();

		try {
		Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
		PreparedStatement query = conn.prepareStatement(GET_SPEL);
		query.setString(1, spelNaam);

		ResultSet rs = query.executeQuery();

		while (rs.next()) {
			Spelbord bord = new Spelbord(rs.getString("spelbordNaam"), rs.getInt("volgorde"));
			spelBorden.add(bord);
		}
		} catch (SQLException | IllegalArgumentException e) {
			throw new RuntimeException(e);
		}
		
		Spel spel = new Spel(spelNaam, spelBorden, new SpelbordRepository());
		
		return spel;
	}

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

}
