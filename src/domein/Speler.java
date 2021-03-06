package domein;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vertalingen.Taal;

/**
 * Stelt een speler voor.
 * @author g85
 */
public class Speler{

	// Properties
	private String naam;
	private String voornaam;
	private String gebruikersnaam;
	private String wachtwoord;
	private boolean adminrechten;

	// Constructors
	/**
	 * Cre�ert een speler met opgegeven naam, voornaam, gebruikersnaam, wachtwoord en
	 * adminrechten.
	 * 
	 * @param naam De naam van de speler.
	 * @param voornaam De voornaam van de speler.
	 * @param gebruikersnaam De gebruikersnaam van de speler.
	 * @param wachtwoord Het wachtwoord van de speler.
	 * @param adminrechten Of de speler adminrechten bezit.
	 * @throws IllegalArgumentException indien de gegevens incorrect zijn ingevuld.
	 */
	public Speler(String naam, String voornaam, String gebruikersnaam, String wachtwoord, boolean adminrechten)
			throws IllegalArgumentException {

		setNaam(naam);
		setVoornaam(voornaam);
		setGebruikersnaam(gebruikersnaam);
		setWachtwoord(wachtwoord);
		setAdminrechten(adminrechten);

	}

	// Methods
	/**
	 * Geeft de naam van de speler terug.
	 * @return de naam van de speler.
	 */
	public String getNaam() {
		return this.naam;
	}
	
	private void setNaam(String naam) {
		this.naam = naam;
	}
	
	/**
	 * Geeft de voornaam van de speler terug.
	 * @return de voornaam van de speler.
	 */
	public String getVoornaam() {
		return this.voornaam;
	}

	private void setVoornaam(String voornaam){
		this.voornaam = voornaam;
	}
	
	/**
	 * Geeft de gebruikersnaam van de speler terug.
	 * @return de gebruikersnaam van de speler.
	 */
	public String getGebruikersnaam() {
		return this.gebruikersnaam;
	}

	/**
	 * Stelt de gebruikersnaam van de speler in. Werpt een IllegalArgumentException indien
	 * de gebruikersnaam leeg is of kleiner dan 8 karakters.
	 * 
	 * @param gebruikersnaam De gewenste gebruikersnaam van de speler.
	 * @throws IllegalArgumentException indien de gebruikersnaam leeg is of kleiner dan 8 karakters.
	 */
	public void setGebruikersnaam(String gebruikersnaam) throws IllegalArgumentException {
		if (gebruikersnaam == null || gebruikersnaam.length() == 0) {
			throw new IllegalArgumentException(Taal.vertaal("exception_username_required"));
		}
		if (gebruikersnaam.length() < 8) {
			throw new IllegalArgumentException(Taal.vertaal("exception_username_minimum"));
		}
		this.gebruikersnaam = gebruikersnaam.toLowerCase();

	}

	/**
	 * Geeft het wachtwoord van de speler terug.
	 * @return het wachtwoord van de speler.
	 */
	public String getWachtwoord() {

		return this.wachtwoord;

	}
	
	private void setWachtwoord(String wachtwoord) throws IllegalArgumentException {
		if (wachtwoord == null || wachtwoord.length() == 0) {
			throw new IllegalArgumentException(Taal.vertaal("exception_password_required"));
		}
		if (wachtwoord.length() < 8) {
			throw new IllegalArgumentException(Taal.vertaal("exception_password_minimum"));
		}
		Pattern p = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}");
		Matcher m = p.matcher(wachtwoord);
		if (!m.matches()) {
			throw new IllegalArgumentException(Taal.vertaal("exception_password_make"));
		}

		this.wachtwoord = wachtwoord;

	}
	/**
	 * Geeft terug of de speler adminrechten heeft of niet.
	 * 
	 * @return true indien de speler adminrechten heeft, false indien de speler geen adminrechten heeft.
	 */
	public boolean hasAdminrechten() {

		return this.adminrechten;

	}

	private void setAdminrechten(boolean adminrechten) throws IllegalArgumentException{
		if (Objects.equals(adminrechten, null))
			throw new IllegalArgumentException(Taal.vertaal("exception_not_null"));
		this.adminrechten = adminrechten;

	}

	/**
	 * Stelt het wachtwoord van de speler terug in op null.
	 */
	public void resetWachtwoord() {
		wachtwoord = null;
	}

}
