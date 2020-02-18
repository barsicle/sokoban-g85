package domein;

public class Speler {
	private String gebruikersnaam;
	private String wachtwoord;
	private boolean adminrechten;
	
	public Speler(String gebruikersnaam, boolean adminrechten) {
		this.gebruikersnaam = gebruikersnaam;
		this.adminrechten = adminrechten;
	}

	public String getGebruikersnaam() {
		return gebruikersnaam;
	}

	public boolean isAdminrechten() {
		return adminrechten;
	}
	
	
}
