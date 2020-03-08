package domein;

import java.util.List;

/**
 * Stelt de domeincontroller voor.
 * @author g85
 */
public class DomeinController {
	
	// Properties
	private final SpelerRepository spelerRepository;
	private final SpelRepository spelRepository;
	private Speler speler;
	private Spel gekozenSpel;
	
	//UC1
	/**
	 * Cre�ert een instantie van de domeincontroller.
	 */
	public DomeinController() {
		spelerRepository = new SpelerRepository();
		spelRepository = new SpelRepository();
	}
	
	
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

	//UC1
	/**
	 * Geeft de speler zijn gebruikersnaam terug.
	 * @return gebruikersnaam
	 */
	public String getGebruikersnaam() {
		return speler.getGebruikersnaam();
	}	
	
	//UC1
	/**
	 * Geeft de speler zijn adminrechten terug.
	 * @return adminrechten
	 */
	public boolean isAdminrechten() {
		return speler.isAdminrechten();
	}
	
	//UC1
	private void setSpeler(Speler speler) {
        this.speler = speler;
    }
	
	public List<String> getSpelNamen(){
		return spelRepository.getSpelNamen();
	}
	
	public void geefOp() {
		
	}
	
	public void kiesSpel(String spelNaam) {
		gekozenSpel = spelRepository.geefSpel(spelNaam);
		
	}

	public Veld[][] geefVelden() {
		return gekozenSpel.geefVelden();
	}

	public boolean beweeg(BeweegRichting richting) {
		return gekozenSpel.beweeg(richting);
	}


    public Moveable getMannetje() {
		return gekozenSpel.geefMannetje();
    }

	public List<Moveable> getKisten() {
		return gekozenSpel.geefKisten();
	}

	public boolean checkBordVoltooid() {
		return gekozenSpel.checkBordVoltooid();
	}
	
	public boolean checkSpelVoltooid() {
		return gekozenSpel.checkSpelvoltooid();
	}
	
	public int getBordenVoltooid() {
		return gekozenSpel.getBordenVoltooid();
	}
	
	public int getBordenTotaal() {
		return gekozenSpel.getBordenTotaal();
	}

}