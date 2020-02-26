package domein;

import java.util.Objects;

import persistentie.*;

public class SpelerRepository {
	
	// Properties
	private final SpelerMapper spelerMapper;
	
	// Constructors
	public SpelerRepository() {
		
		spelerMapper = new SpelerMapper();
		
	}
	
	// Methods
	// Return player from database after checking if the entered password matches the password in the database
	public Speler geefSpeler(String gebruikersnaam) throws RuntimeException {
		
		Speler speler = spelerMapper.geefSpeler(gebruikersnaam);
        return speler;
        
	}
	
	// Add player to database
	public void voegSpelerToe(Speler speler) throws IllegalArgumentException {
		
	       if (bestaatSpeler(speler.getGebruikersnaam()))
	            throw new IllegalArgumentException("De gebruiker bestaat reeds.");
	       
	       spelerMapper.voegSpelerToe(speler);
	       
	}
	
	// Check if the player already exists in the database
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
