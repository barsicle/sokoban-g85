package domein;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import vertalingen.Taal;

/**
 * Stelt de domeincontroller voor.
 * @author g85
 */
public class DomeinController {
	
	// Properties
	private final SpelerRepository spelerRepository;
	private final SpelRepository spelRepository;
	private final SpelbordRepository spelbordRepository;
	private Speler speler;
	private Spel gekozenSpel;
	private Spelbord huidigSpelbord;
	
	//UC1
	/**
	 * Cre�ert een instantie van de domeincontroller.
	 */
	public DomeinController() {
		this.spelerRepository = new SpelerRepository();
		this.spelRepository = new SpelRepository();
		this.spelbordRepository = new SpelbordRepository();
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
		Speler nieuweSpeler = new Speler(naam, voornaam, gebruikersnaam, wachtwoord, false);
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
		if (Objects.equals(speler, null))
			throw new IllegalArgumentException(Taal.vertaal("player") + Taal.vertaal("exception_not_null"));
        this.speler = speler;
    }
	
	public List<String> getSpelNamen(){
		return spelRepository.getSpelNamen();
	}
	
	public void geefOp() {
		
	}
	
	public void creeerSpel(String spelNaam) throws RuntimeException {
		//Check dat er geen spaties zijn
		if (spelNaam.contains(" ")) {
			throw new RuntimeException(Taal.vertaal("exception_name_no_spaces"));
		}
		//Check of spel al bestaat
		if (spelRepository.geefSpel(spelNaam) != null) {
			throw new RuntimeException(Taal.vertaal("exception_game_exists"));
		}
		
		Spel spel = new Spel(spelNaam, new ArrayList<Spelbord>());
		spel.setAanmaker(speler);
		
		gekozenSpel = spel;
	}
	
	public void creeerSpelbord(String spelbordNaam) {
		//Check dat het niet in de huidige selectie zit om toe te voegen of al in de DB
		if(gekozenSpel.getBordnamen().contains(spelbordNaam) || spelbordRepository.bordExists(spelbordNaam)) {
			throw new RuntimeException(Taal.vertaal("exception_board_exists"));
		}
		
		int volgorde = gekozenSpel.getBordenTotaal();
		huidigSpelbord = new Spelbord(spelbordNaam, volgorde);
	}
	
	public void voegSpelbordToe(Spelbord bord) {
		gekozenSpel.voegNieuwSpelbordToe(bord);
	}
	
	//TO DO : misschien dit void maken en een get zetten op SpelInterface? Dan kunnen er een shitload methods weg uit de DC zoals bordenvoltooid, totaal, ...
	public SpelInterface registreerSpel() {
		spelRepository.insertSpel(gekozenSpel);
		
		gekozenSpel.getSpelborden().stream().forEach(b -> spelbordRepository.insertBord(b, gekozenSpel.getSpelNaam()));
		//resetGekozenSpel();
		
		return gekozenSpel;
	}
	
	public SpelInterface getSpel() {
		return this.gekozenSpel;
	}
	
	/*
	public void resetGekozenSpel() {
		gekozenSpel = null;
	}
	*/
	
	//Haal spel op uit de repo en zet zijn borden erna
	public void kiesSpel(String spelNaam) {
		gekozenSpel = spelRepository.geefSpel(spelNaam);
		List<Spelbord> borden = spelbordRepository.geefSpelborden(spelNaam);
		gekozenSpel.setSpelborden(borden);
		huidigSpelbord = spelbordRepository.geefSpelbordMetVelden(gekozenSpel.getBordNaam(), BordDimensies.getAantalRijen(), BordDimensies.getAantalKolommen());
		
	}

	public VeldInterface[][] geefVelden() {
		return huidigSpelbord.getVelden();
	}

	public void beweeg(BeweegRichting richting) throws RuntimeException {
		huidigSpelbord.beweeg(richting);
	}


    public Moveable getMannetje() {
		return huidigSpelbord.getMannetje();
    }

	public List<Moveable> getKisten() {
		return huidigSpelbord.getKisten();
	}

	public boolean checkBordVoltooid() {
		boolean voltooid = huidigSpelbord.isVoltooid();
		if (voltooid && !(checkSpelVoltooid())) {
			gekozenSpel.volgendBord();
			if (!gekozenSpel.checkSpelvoltooid()) {
				huidigSpelbord = spelbordRepository.geefSpelbordMetVelden(gekozenSpel.getBordNaam(), BordDimensies.getAantalRijen(), BordDimensies.getAantalKolommen());
			}
		}
		return voltooid;
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
	
	public void resetBord() {
		huidigSpelbord = spelbordRepository.geefSpelbordMetVelden(huidigSpelbord.getSpelbordNaam(), BordDimensies.getAantalRijen(), BordDimensies.getAantalKolommen());
	}
	
	public int getAantalBewegingen() {
		return huidigSpelbord.getAantalBewegingen();
	}
	
	public void creeerVeld(Actie actie, int x, int y) {
		huidigSpelbord.creeerVeld(actie, x, y);
	}
	
	public Spelbord getHuidigSpelbord() {
		return this.huidigSpelbord;
	}
	
	public VeldInterface getVeld(int x, int y) {
		return this.huidigSpelbord.getVeld(x, y);
	}
}