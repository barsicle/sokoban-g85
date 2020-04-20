package domein;

import java.util.List;
import java.util.Objects;

import vertalingen.Taal;

/**
 * Stelt de domeincontroller voor.
 * @author g85
 */
public class DomeinController {
	
	private final SpelerRepository spelerRepository;
	private final SpelRepository spelRepository;
	private final SpelbordRepository spelbordRepository;
	private Speler speler;
	private Spel gekozenSpel;
	private Spelbord huidigSpelbord;
	
	/**
	 * Creëert een instantie van de domeincontroller.
	 */
	public DomeinController() {
		this.spelerRepository = new SpelerRepository();
		this.spelRepository = new SpelRepository();
		this.spelbordRepository = new SpelbordRepository();
	}
	
	/**
	 * Registreert de gebruiker als speler. Werpt een IllegalArgumentException indien
	 *  het wachtwoord en de wachtwoordbevestiging niet overeenkomen.
	 *
	 * @param  naam  De naam van de gebruiker.
	 * @param  voornaam De voornaam van de gebruiker.
	 * @param  gebruikersnaam De gebruikersnaam van de gebruiker.
	 * @param  wachtwoord Het wachtwoord van de gebruiker.
	 * @param  wachtwoordBevestiging De bevestiging van het wachtwoord van de gebruiker. Moet overeenkomen met het wachtwoord.
	 * @throws IllegalArgumentException indien het wachtwoord en de wachtwoordbevestiging niet overeenkomen.
	 */
	public void registreer(String naam, String voornaam, String gebruikersnaam, String wachtwoord, String wachtwoordBevestiging) throws IllegalArgumentException, RuntimeException {
		if (!wachtwoord.equals(wachtwoordBevestiging)) {
            throw new IllegalArgumentException(Taal.vertaal("exception_passwords"));
        }
		
		// Instantiate new player and add him/her to repository
		Speler nieuweSpeler = new Speler(naam, voornaam, gebruikersnaam, wachtwoord, false);
		spelerRepository.voegSpelerToe(nieuweSpeler);
		
		nieuweSpeler.resetWachtwoord();
		setSpeler(nieuweSpeler);

	}
	/**
	 * Meldt de speler aan. Werpt een RuntimeException indien
	 *  de speler niet bestaat.
	 *
	 * @param  gebruikersnaam De gebruikersnaam van de speler.
	 * @param  wachtwoord Het wachtwoord van de speler.
	 * @throws RuntimeException indien de speler niet bestaat.
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

	/**
	 * Geeft de speler zijn gebruikersnaam terug.
	 * @return gebruikersnaam
	 */
	public String getGebruikersnaam() {
		return speler.getGebruikersnaam();
	}	
	
	/**
	 * Geeft de speler zijn adminrechten terug.
	 * @return adminrechten
	 */
	public boolean isAdmin() {
		return speler.hasAdminrechten();
	}
	
	private void setSpeler(Speler speler) {
		if (Objects.equals(speler, null))
			throw new IllegalArgumentException(Taal.vertaal("player") + Taal.vertaal("exception_not_null"));
        this.speler = speler;
    }
	
	/**
	 * Geeft een lijst van de namen van alle spelen terug.
	 * @return een lijst van de de namen van de spelen.
	 */
	public List<String> getSpelNamen() throws RuntimeException{
		return spelRepository.getSpelNamen();
	}
	
	/**
	 * Creëert een spel met opgegeven naam. Werpt een IllegalArgumentException indien de spelnaam spaties bevat of
	 * indien het spel met de opgegeven naam al bestaat.
	 * @param spelNaam De naam van het spel.
	 * @throws IllegalArgumentException indien de spelnaam spaties bevat of
	 * indien het spel met de opgegeven naam al bestaat.
	 */
	public void creeerSpel(String spelNaam) throws IllegalArgumentException, RuntimeException {
		Spel spel = new Spel(spelNaam);
		
		//Check of spel al bestaat
		if (spelRepository.geefSpel(spelNaam) != null) {
			throw new IllegalArgumentException(Taal.vertaal("exception_game_exists"));
		}
		
		spel.setAanmaker(speler);
		
		gekozenSpel = spel;
	}
	
	/**
	 * Creëert een spelbord met opgegeven naam. Werpt een IllegalArgumentException indien de spelbordnaam null is of een lege string of
	 * indien het spelbord met de opgegeven naam al bestaat. Het gemaakte spelbord wordt het huidige spelbord.
	 * @param spelbordNaam De naam van het spelbord.
	 * @throws IllegalArgumentException indien de spelbordnaam null is of een lege string of
	 * indien het spelbord met de opgegeven naam al bestaat.
	 */
	public void creeerSpelbord(String spelbordNaam) throws IllegalArgumentException, RuntimeException {
		int volgorde = gekozenSpel.getBordenTotaal();
		huidigSpelbord = new Spelbord(spelbordNaam, volgorde);
		
		if(gekozenSpel.containsSpelbord(spelbordNaam)|| spelbordRepository.bordExists(spelbordNaam)) {
			throw new IllegalArgumentException(Taal.vertaal("exception_board_exists"));
		}
	}

	/**
	 * Voegt het huidige spelbord toe aan het gekozen spel.
	 * @throws RuntimeException indien het aantal kisten niet gelijk is
	 * aan het aantal doelen, indien er niet genoeg doelen of kisten op het bord staan of indien er geen mannetje op het bord staat.
	 */
	public void voegSpelbordToe() throws RuntimeException {
		gekozenSpel.voegNieuwSpelbordToe(huidigSpelbord);

	}
	
	/**
	 * Registreert het gekozen spel en zijn spelborden in de repositories.
	 */
	public void registreerSpel() throws RuntimeException {
		spelRepository.insertSpel(gekozenSpel);
		gekozenSpel.getSpelborden().stream().forEach(b -> spelbordRepository.insertBord(b, gekozenSpel.getSpelNaam()));
	}
	
	/**
	 * Geeft het huidige spel terug.
	 * @return gebruikersnaam
	 */
	public SpelInterface getSpel() {
		return this.gekozenSpel;
	}
	
	/**
	 * Stelt het gekozen spel in op null.
	 */
	public void resetGekozenSpel() {
		gekozenSpel = null;
	}
	
	/**
	 * Haalt het spel met opgegeven naam op uit de repository, haalt zijn overeenkomstige spelborden
	 * op en stelt het spel in als gekozen spel. 
	 * @param spelNaam De opgegeven naam waarmee het spel opgehaald wordt.
	 */
	public void kiesSpel(String spelNaam) throws RuntimeException {
		gekozenSpel = spelRepository.geefSpel(spelNaam);
		List<Spelbord> borden = spelbordRepository.geefSpelborden(spelNaam);
		gekozenSpel.setSpelborden(borden);
		huidigSpelbord = spelbordRepository.geefSpelbordMetVelden(gekozenSpel.getBordNaam(), BordDimensies.getAantalRijen(), BordDimensies.getAantalKolommen());
		
	}
	/**
	 * Geeft de velden van het huidige spelbord terug.
	 * @return de velden van het spelbord.
	 */
	public VeldInterface[][] geefVelden() {
		return huidigSpelbord.getVelden();
	}

	/**
	 * Beweegt het mannetje in de gekozen richting.
	 * @param richting de gekozen richting.
	 * @throws RuntimeException indien het mannetje een illegale beweging doet.
	 */
	public void beweeg(BeweegRichting richting) throws RuntimeException {
		huidigSpelbord.beweeg(richting);
	}

	/**
	 * Geeft het mannetje van het hudige spelbord terug.
	 * @return het mannetje van het huidige spelbord.
	 */
    public Moveable getMannetje() {
		return huidigSpelbord.getMannetje();
    }

	/**
	 * Geeft een lijst van de kisten van het huidige spelbord terug. 
	 * @return een lijst van de kisten.
	 */
	public List<Moveable> getKisten() {
		return huidigSpelbord.getKisten();
	}

	/**
	 * Controleert of het spelbord voltooid is. Als het spelbord voltooid is maar het spel nog niet
	 * zet de methode het volgende bord klaar.
	 * 
	 * @return true indien het spelbord voltooid is, anders false.
	 */
	public boolean checkBordVoltooid() throws RuntimeException {
		boolean voltooid = huidigSpelbord.isVoltooid();
		if (voltooid && !(checkSpelVoltooid())) {
			gekozenSpel.volgendBord();
			if (!gekozenSpel.checkSpelvoltooid()) {
				huidigSpelbord = spelbordRepository.geefSpelbordMetVelden(gekozenSpel.getBordNaam(), BordDimensies.getAantalRijen(), BordDimensies.getAantalKolommen());
			}
		}
		return voltooid;
	}
	/**
	 * Controleert of het spel voltooid is.
	 * @return true als het spel voltooid is, anders false.
	 */
	public boolean checkSpelVoltooid() {
		return gekozenSpel.checkSpelvoltooid();
	}
	/**
	 * Geeft het aantal voltooide spelborden van het spel terug.
	 * @return het aantal voltooide spelborden.
	 */
	public int getBordenVoltooid() {
		return gekozenSpel.getBordenVoltooid();
	}
	/**
	 * Geeft het totaal aantal spelborden van het spel terug.
	 * @return het totaal aantal spelborden van het spel.
	 */
	public int getBordenTotaal() {
		return gekozenSpel.getBordenTotaal();
	}
	/**
	 * Reset het spelbord op het moment van spelen.
	 */
	public void resetBord() throws RuntimeException {
		huidigSpelbord = spelbordRepository.geefSpelbordMetVelden(huidigSpelbord.getSpelbordNaam(), BordDimensies.getAantalRijen(), BordDimensies.getAantalKolommen());
	}
	/**
	 * Reset het spelbord op het moment van creatie.
	 */
	public void resetBordCreatie() {
		creeerSpelbord(huidigSpelbord.getSpelbordNaam());
	}
	/**
	 * Geeft het aantal bewegingen van het mannetje op het spelbord terug.
	 * @return het aantal bewegingen van het mannetje op het spelbord.
	 */
	public int getAantalBewegingen() {
		return huidigSpelbord.getAantalBewegingen();
	}
	/**
	 * Maakt een gegeven veld aan op de gespecifieerde locatie.
	 * @param actie Het soort veld dat gecreëerd wordt of een clear van het veld.
	 * @param x De rij waarop het veld komt.
	 * @param y De kolom waarop het veld komt.
	 */
	public void creeerVeld(Actie actie, int x, int y) {
		huidigSpelbord.creeerVeld(actie, x, y);
	}
	/**
	 * Geeft het veld op de gegeven locatie terug.
	 * @param x De gegeven rij.
	 * @param y De gegeven kolom.
	 * @return het veld met de gegeven coördinaten;
	 */
	public VeldInterface getVeld(int x, int y) {
		return this.huidigSpelbord.getVeld(x, y);
	}
}