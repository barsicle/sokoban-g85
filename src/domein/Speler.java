package domein;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Speler {

	// Properties
	private String naam;
	private String voornaam;
	private String gebruikersnaam;
	private String wachtwoord;
	private boolean adminrechten;

	// Constructors
	public Speler(String naam, String voornaam, String gebruikersnaam, String wachtwoord, boolean adminrechten)
			throws IllegalArgumentException {

		setNaam(naam);
		setVoornaam(voornaam);
		setGebruikersnaam(gebruikersnaam);
		setWachtwoord(wachtwoord);
		setAdminrechten(adminrechten);

	}

	public Speler(String naam, String voornaam, String gebruikersnaam, String wachtwoord)
			throws IllegalArgumentException {

		this(naam, voornaam, gebruikersnaam, wachtwoord, false);

	}

	// Methods
	public String getNaam() {
		return this.naam;
	}

	private void setNaam(String naam) {

		this.naam = naam;

	}

	public String getVoornaam() {

		return this.voornaam;

	}

	private void setVoornaam(String voornaam) {

		this.voornaam = voornaam;

	}

	public String getGebruikersnaam() {

		return this.gebruikersnaam;

	}

	public void setGebruikersnaam(String gebruikersnaam) throws IllegalArgumentException {

		if (gebruikersnaam == null || gebruikersnaam.length() == 0) {

			throw new IllegalArgumentException("Elke speler heeft verplicht een gebruikersnaam");

		}
		if (gebruikersnaam.length() < 8) {
			throw new IllegalArgumentException("Gebruikersnaam bestaat minstens uit 8 karakters");
		}

		this.gebruikersnaam = gebruikersnaam.toLowerCase();

	}

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

	public boolean isAdminrechten() {

		return this.adminrechten;

	}

	private void setAdminrechten(boolean adminrechten) {

		this.adminrechten = adminrechten;

	}

	public void resetWachtwoord() {
		wachtwoord = null;
	}

}
