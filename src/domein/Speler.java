package domein;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Stelt een speler voor.
 * @author g85
 */
public class Speler {

	// Properties
	private String naam;
	private String voornaam;
	private String gebruikersnaam;
	private String wachtwoord;
	private boolean adminrechten;

	// Constructors
	
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
	
	/**
	 * Geeft de naam van de speler terug.
	 * */
	public String getNaam() {
		return this.naam;
	}

	private void setNaam(String naam) {

		this.naam = naam;

	}
	
	/**
	 * Geeft de voornaam van de speler terug.
	 * */
	public String getVoornaam() {

		return this.voornaam;

	}

	private void setVoornaam(String voornaam) {

		this.voornaam = voornaam;

	}
	
	/**
	 * Geeft de gebruikersnaam van de speler terug.
	 * */
	public String getGebruikersnaam() {

		return this.gebruikersnaam;

	}
	
	/**
	 * Stelt de gebruikersnaam van de speler in. Werpt een IllegalArgumentException indien
	 * de gebruikersnaam leeg is of kleiner dan 8 karakters.
	 * 
	 * @param gebruikersnaam De gewenste gebruikersnaam van de speler.
	 * */
	public void setGebruikersnaam(String gebruikersnaam) throws IllegalArgumentException {

		if (gebruikersnaam == null || gebruikersnaam.length() == 0) {

			throw new IllegalArgumentException("Elke speler heeft verplicht een gebruikersnaam");

		}
		if (gebruikersnaam.length() < 8) {
			throw new IllegalArgumentException("Gebruikersnaam bestaat minstens uit 8 karakters");
		}

		this.gebruikersnaam = gebruikersnaam.toLowerCase();

	}
	
	/**
	 * Geeft het wachtwoord van de speler terug.
	 * */
	public String getWachtwoord() {

		return this.wachtwoord;

	}

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
	
	/**
	 * Geeft terug of de speler adminrechten heeft of niet.
	 * */
	public boolean isAdminrechten() {

		return this.adminrechten;

	}

	private void setAdminrechten(boolean adminrechten) {

		this.adminrechten = adminrechten;

	}
	
	/**
	 * Stelt het wachtwoord van de speler terug in op null.
	 * */
	public void resetWachtwoord() {
		wachtwoord = null;
	}

}
