package domein;

import java.util.List;

import vertalingen.Taal;

/**
 * Stelt de domeincontroller voor.
 * @author g85
 */
public class DomeinController {
	
	// Properties
	private final SpelerRepository spelerRepository;
	private final SpelRepository spelRepository;
	private SpelerInfo speler;
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
            throw new IllegalArgumentException(Taal.vertaal("exception_passwords"));
        }
		
		// Instantiate new player and add him/her to repository
		Speler nieuweSpeler = new Speler(naam, voornaam, gebruikersnaam, wachtwoord);
		spelerRepository.voegSpelerToe(nieuweSpeler);
		
		nieuweSpeler.resetWachtwoord();
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
			throw new RuntimeException(Taal.vertaal("player_not_found"));
		}
		
		Speler aangemeldeSpeler = spelerRepository.geefSpeler(gebruikersnaam);
		if (!aangemeldeSpeler.getWachtwoord().equals(wachtwoord)) {
			throw new RuntimeException(Taal.vertaal("exception_username_password"));
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
	public boolean isAdmin() {
		return speler.hasAdminrechten();
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