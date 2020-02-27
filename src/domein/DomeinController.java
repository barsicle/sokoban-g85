package domein;

public class DomeinController {
	
	// Properties
	private final SpelerRepository spelerRepository;
	private Speler speler;
	
	public DomeinController() {
		spelerRepository = new SpelerRepository();
	}
	
	// Methods
	// To register, the properties must meet certain conditions and the password must be confirmed.
	
	/**
	 * Registreert de gebruiker als speler. Werpt een IllegalArgumentException indien
	 *  het wachtwoord en de wachtwoordbevestiging niet overeenkomen.
	 *
	 * @param  naam  de naam van de gebruiker
	 * @param  voornaam de voornaam van de gebruiker
	 * @param  gebruikersnaam de gebruikersnaam van de gebruiker
	 * @param  wachtwoord het wachtwoord van de gebruiker
	 * @param  wachtwoordbevestiging de bevestiging van het wachtwoord van de gebruiker. Moet overeenkomen met het wachtwoord.
	 */
	
	public void registreer(String naam, String voornaam, String gebruikersnaam, String wachtwoord, String wachtwoordBevestiging) throws IllegalArgumentException {
		if (!wachtwoord.equals(wachtwoordBevestiging)) {
            throw new IllegalArgumentException("Wachtwoorden komen niet overeen.");
        }
		
		// Instantiate new player and add him/her to repository
		Speler nieuweSpeler = new Speler(naam, voornaam, gebruikersnaam, wachtwoord);
		spelerRepository.voegSpelerToe(nieuweSpeler);
		
		setSpeler(nieuweSpeler);
				
	}
	
	/**
	 * Meldt de speler aan. Werpt een RuntimeException indien
	 *  de speler niet bestaat.
	 *
	 * @param  gebruikersnaam de gebruikersnaam van de speler
	 * @param  wachtwoord het wachtwoord van de speler
	 */
	public void meldAan(String gebruikersnaam, String wachtwoord) throws RuntimeException {
		if(!spelerRepository.bestaatSpeler(gebruikersnaam)) {
			throw new RuntimeException("Speler niet gevonden");
		}
		
		Speler aangemeldeSpeler = spelerRepository.geefSpeler(gebruikersnaam);
		if (!aangemeldeSpeler.getWachtwoord().equals(wachtwoord)) {
			throw new RuntimeException("Gebruikersnaam en wachtwoord komen niet overeen");
		}
		
		aangemeldeSpeler.resetWachtwoord();
        setSpeler(aangemeldeSpeler);
		
	}
	
	/**
	 * Controleert of de speler bestaat in de repository. Werpt een RuntimeException als deze niet bestaat.
	 * 
	 * @param  gebruikersnaam de gebruikersnaam van de speler
	 */
    public boolean bestaatSpeler(String gebruikersnaam) throws RuntimeException {
    	return spelerRepository.bestaatSpeler(gebruikersnaam);
    }
    
	/**
	 * Sluit de applicatie af.
	 */
	public void afsluiten() {
		System.exit(0);
	}

	// Only return the user name and no other properties (e.g. password)
	/**
	 * Retourneert de speler.
	 */
	public Speler getSpeler() {
		return speler;
	}
	
	// Set the current player
	private void setSpeler(Speler speler) {
        this.speler = speler;
    }
	
	
	
}