package persistentie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import domein.Speler;

public class SpelerMapper {
	
	private static final String JDBC_URL = "jdbc:mysql://ID222177_g85.db.webhosting.be?serverTimezone=UTC&useLegacyDatetimeCode=false&user=ID222177_g85&password=fi4pinVo";

	public Speler geefSpeler(String gebruikersnaam, String wachtwoord) {
        Speler speler = null;

        try (Connection conn = DriverManager.getConnection(JDBC_URL);
                PreparedStatement query = conn.prepareStatement("SELECT * FROM ID222177_g85.speler WHERE gebruikersnaam = ?")) {
            query.setString(1, gebruikersnaam.toLowerCase());
            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                	String dbWachtwoord = rs.getString("wachtwoord");
                	if (!dbWachtwoord.equals(wachtwoord)) {
                		throw new IllegalArgumentException("Verkeerd wachtwoord");
                	}
                    speler = new Speler(rs.getString("gebruikersnaam"), rs.getBoolean("adminrechten"));
                } else {
                	throw new IllegalArgumentException("Gebruiker onbekend");
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return speler;
	}
}
