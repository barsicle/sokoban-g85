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
	public void registreer(String naam, String voornaam, String gebruikersnaam, String wachtwoord, String wachtwoordBevestiging) throws IllegalArgumentException {
		if (!wachtwoord.equals(wachtwoordBevestiging)) {
            throw new IllegalArgumentException("Wachtwoorden komen niet overeen.");
        }
		
		// Instantiate new player and add him/her to repository
		Speler nieuweSpeler = new Speler(naam, voornaam, gebruikersnaam, wachtwoord);
		spelerRepository.voegSpelerToe(nieuweSpeler);
		
		setSpeler(nieuweSpeler);
				
	}
	
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
	
    public boolean bestaatSpeler(String gebruikersnaam) throws RuntimeException {
    	return spelerRepository.bestaatSpeler(gebruikersnaam);
    }
	
	public void afsluiten() {
		System.exit(0);
	}

	// Only return the user name and no other properties (e.g. password)
	public Speler getSpeler() {
		return speler;
	}
	
	// Set the current player
	private void setSpeler(Speler speler) {
        this.speler = speler;
    }
	
	
	
}