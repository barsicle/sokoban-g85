package domein;

import java.util.Objects;
import persistentie.*;

/**
 * Stelt een repository van spelers voor.
 * @author g85
 */
public class SpelerRepository {
	
	// Properties
	private final SpelerMapper spelerMapper;
	
	// Constructors
	public SpelerRepository() {
		
		spelerMapper = new SpelerMapper();
		
	}
	
	// Methods
	// Return player from database after checking if the entered password matches the password in the database
	
	/**
	 * Geeft de speler met opgegeven gebruikersnaam terug. Werpt een RuntimeException indien
	 *  er een probleem is met de database.
	 * @param gebruikersnaam De gebruikersnaam waarmee de speler wordt opgezocht.
	 */ 
	public Speler geefSpeler(String gebruikersnaam) throws RuntimeException {
		
		Speler speler = spelerMapper.geefSpeler(gebruikersnaam);
        return speler;
        
	}
	
	// Add player to database
	/**
	 * Voegt de opgegeven speler toe aan de database. Werpt een IllegalArgumentException indien de opgegeven speler
	 * al in de database zit.
	 * @param speler De speler die wordt toegevoegd aan de database.
	 */ 
	public void voegSpelerToe(Speler speler) throws IllegalArgumentException {
		
	       if (bestaatSpeler(speler.getGebruikersnaam()))
	            throw new IllegalArgumentException("De gebruiker bestaat reeds.");
	       
	       spelerMapper.voegSpelerToe(speler);
	       
	}
	
	// Check if the player already exists in the database
	/**
	 * Geeft de speler met opgegeven gebruikersnaam terug. Werpt een RuntimeException indien
	 *  er een probleem is met de database.
	 * @param gebruikersnaam De gebruikersnaam waarmee de speler wordt opgezocht in de database.
	 */ 
    public boolean bestaatSpeler(String gebruikersnaam) throws RuntimeException {
    	
        return !Objects.equals(spelerMapper.geefSpeler(gebruikersnaam), null);
        
    }
	
	// Encrypt the user password for secure storage
	// Note: we will need a decryption algorithm as well for authenticating purposes
	/*private String encryptWachtwoord(String wachtwoord) {
			
		// TO DO
		String encrypted = wachtwoord;
		return encrypted;
			
	}*/
	
}
