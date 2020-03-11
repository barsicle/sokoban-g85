package domein;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//UC1
/**
 * Stelt een speler voor.
 * @author g85
 */
public class Speler implements SpelerInfo{

	// Properties
	private String naam;
	private String voornaam;
	private String gebruikersnaam;
	private String wachtwoord;
	private boolean adminrechten;

	// Constructors
	
	//UC2
	/**
	 * Creëert een een speler met opgegeven naam, voornaam, gebruikersnaam, wachtwoord en
	 * adminrechten.
	 * 
	 * @param naam De naam van de speler.
	 * @param voornaam De voornaam van de speler.
	 * @param gebruikersnaam De gebruikersnaam van de speler.
	 * @param wachtwoord Het wachtwoord van de speler.
	 * @param adminrechten Of de speler adminrechten bezit.
	 */
	public Speler(String naam, String voornaam, String gebruikersnaam, String wachtwoord, boolean adminrechten)
			throws IllegalArgumentException {

		setNaam(naam);
		setVoornaam(voornaam);
		setGebruikersnaam(gebruikersnaam);
		setWachtwoord(wachtwoord);
		setAdminrechten(adminrechten);

	}
	//UC2
	/**
	 * Creëert een een speler met opgegeven naam, voornaam, gebruikersnaam en wachtwoord. Heeft geen adminrechten.
	 * 
	 * @param naam De naam van de speler.
	 * @param voornaam De voornaam van de speler.
	 * @param gebruikersnaam De gebruikersnaam van de speler.
	 * @param wachtwoord Het wachtwoord van de speler.
	 */
	public Speler(String naam, String voornaam, String gebruikersnaam, String wachtwoord)
			throws IllegalArgumentException {

		this(naam, voornaam, gebruikersnaam, wachtwoord, false);

	}

	// Methods
	
	//UC1
	/**
	 * Geeft de naam van de speler terug.
	 * @return de naam van de speler.
	 */
	public String getNaam() {
		return this.naam;
	}
	
	//UC1
	private void setNaam(String naam) {

		this.naam = naam;

	}
	
	//UC1
	/**
	 * Geeft de voornaam van de speler terug.
	 * @return de voornaam van de speler.
	 */
	public String getVoornaam() {

		return this.voornaam;

	}
	
	//UC1
	private void setVoornaam(String voornaam) {

		this.voornaam = voornaam;

	}
	
	//UC1
	/**
	 * Geeft de gebruikersnaam van de speler terug.
	 * @return de gebruikersnaam van de speler.
	 */
	public String getGebruikersnaam() {

		return this.gebruikersnaam;

	}
	//UC1
	/**
	 * Stelt de gebruikersnaam van de speler in. Werpt een IllegalArgumentException indien
	 * de gebruikersnaam leeg is of kleiner dan 8 karakters.
	 * 
	 * @param gebruikersnaam De gewenste gebruikersnaam van de speler.
	 */
	public void setGebruikersnaam(String gebruikersnaam) throws IllegalArgumentException {

		if (gebruikersnaam == null || gebruikersnaam.length() == 0) {

			throw new IllegalArgumentException("Elke speler heeft verplicht een gebruikersnaam");

		}
		if (gebruikersnaam.length() < 8) {
			throw new IllegalArgumentException("Gebruikersnaam bestaat minstens uit 8 karakters");
		}

		this.gebruikersnaam = gebruikersnaam.toLowerCase();

	}
	//UC1
	/**
	 * Geeft het wachtwoord van de speler terug.
	 * @return het wachtwoord van de speler.
	 */
	public String getWachtwoord() {

		return this.wachtwoord;

	}
	
	//UC1
	private void setWachtwoord(String wachtwoord) throws IllegalArgumentException {

		if (wachtwoord == null || wachtwoord.length() == 0) {
			throw new IllegalArgumentException("Elke speler heeft verplicht een wachtwoord.");
		}
		if (wachtwoord.length() < 8) {
			throw new IllegalArgumentException("Wachtwoord bestaat minstens uit 8 karakters");
		}
		Pattern p = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}");
		Matcher m = p.matcher(wachtwoord);
		if (!m.matches()) {
			throw new IllegalArgumentException(
					"Wachtwoord bestaat ten minste uit 1 hoofdletter, 1 kleine letter en 1 cijfer");
		}

		this.wachtwoord = wachtwoord;

	}
	//UC1
	/**
	 * Geeft terug of de speler adminrechten heeft of niet.
	 * 
	 * @return true indien de speler adminrechten heeft, false indien de speler geen adminrechten heeft.
	 */
	public boolean hasAdminrechten() {

		return this.adminrechten;

	}
	//UC1
	private void setAdminrechten(boolean adminrechten) {

		this.adminrechten = adminrechten;

	}
	//UC2
	/**
	 * Stelt het wachtwoord van de speler terug in op null.
	 */
	public void resetWachtwoord() {
		wachtwoord = null;
	}

}
