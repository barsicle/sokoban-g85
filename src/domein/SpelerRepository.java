package domein;

import persistentie.*;
import domein.*;

public class SpelerRepository {

	private SpelerMapper spelerMapper;
	
	public SpelerRepository() {
		spelerMapper = new SpelerMapper();
	}

	public Speler geefSpeler(String gebruikersnaam, String wachtwoord) {
		Speler speler = null;
		try {
			speler = spelerMapper.geefSpeler(gebruikersnaam, encryptWachtwoord(wachtwoord));
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return speler;
	}
	
	private String encryptWachtwoord(String wachtwoord) {
		// TO DO: encryption algoritme ipv plaintext
		String encrypted = wachtwoord;
		return encrypted;
	}
}
