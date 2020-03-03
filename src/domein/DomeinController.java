package domein;

/**
 * Stelt de domeincontroller voor.
 * @author g85
 */
public class DomeinController {
	
	// Properties
	private final SpelerRepository spelerRepository;
	private Speler speler;
	
	//UC1
	/**
	 * Creëert een instantie van de domeincontroller.
	 */
	public DomeinController() {
		spelerRepository = new SpelerRepository();
	}
	
	// Methods
	// To register, the properties must meet certain conditions and the password must be confirmed.
	
	//UC2
	/**
	 * Registreert de gebruiker als speler. Werpt een IllegalArgumentException indien
	 *  het wachtwoord en de wachtwoordbevestiging niet overeenkomen.
	 *
	 * @param  naam  De naam van de gebruiker.
	 * @param  voornaam De voornaam van de gebruiker.
	 * @param  gebruikersnaam De gebruikersnaam van de gebruiker.
	 * @param  wachtwoord Het wachtwoord van de gebruiker.
	 * @param  wachtwoordBevestiging De bevestiging van het wachtwoord van de gebruiker. Moet overeenkomen met het wachtwoord.
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
	//UC1
	/**
	 * Meldt de speler aan. Werpt een RuntimeException indien
	 *  de speler niet bestaat.
	 *
	 * @param  gebruikersnaam De gebruikersnaam van de speler.
	 * @param  wachtwoord Het wachtwoord van de speler.
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
	//UC2
	/**
	 * Controleert of de speler bestaat in de database. Werpt een RuntimeException als deze niet bestaat.
	 * 
	 * @param gebruikersnaam De gebruikersnaam van de speler.
	 * @return true indien de speler bestaat, false indien de speler niet bestaat.
	 */
    public boolean bestaatSpeler(String gebruikersnaam) throws RuntimeException {
    	return spelerRepository.bestaatSpeler(gebruikersnaam);
    }
    //UC3
	/**
	 * Sluit de applicatie af.
	 */
	public void afsluiten() {
		System.exit(0);
	}

	// Only return the user name and no other properties (e.g. password)
	//UC1
	/**
	 * Geeft de speler terug.
	 * @return De speler.
	 */
	public Speler getSpeler() {
		return speler;
	}
	
	//UC1
	private void setSpeler(Speler speler) {
        this.speler = speler;
    }
	
}