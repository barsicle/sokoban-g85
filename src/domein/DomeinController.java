package domein;

public class DomeinController {

	private SpelerRepository spelerRepository;
	private Speler speler;
	
	public DomeinController() {
		spelerRepository = new SpelerRepository();
		speler = null;
	}

	public void meldAan(String gebruikersnaam, String wachtwoord) {
		speler = spelerRepository.geefSpeler(gebruikersnaam, wachtwoord);
	}
}