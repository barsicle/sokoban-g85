package domein;

import java.util.*;
import java.util.stream.Collectors;

import vertalingen.Taal;

/**
 * Stelt het spel voor.
 * @author g85
 */
public class Spel implements SpelInterface{
	private String spelNaam;
	private List<Spelbord> spelborden;
	private Spelbord huidigSpelbord;
	private boolean spelVoltooid = false;
	private final SpelbordRepository spelbordRepository;
	private Speler aanmaker;
	private int aantalRijen, aantalKolommen;

	
	/**
	 * Creëert een spel met opgegeven naam.
	 * 
	 * @param spelNaam De naam van het spel.
	 * @param spelborden Een lijst met spelborden waaruit het spel is samengesteld.
	 * @param spelbordRepository Repository om spelborden op te halen.
	 */		
	public Spel(String spelNaam, SpelbordRepository spelbordRepository) {
			setSpelNaam(spelNaam);
			setAantalRijen(10);
			setAantalKolommen(10);
		this.spelbordRepository = spelbordRepository;
		
		spelborden = spelbordRepository.geefSpelborden(spelNaam);
		//Check of er borden zijn, zoja is het geen nieuw spel en willen we starten
		if (spelborden.size() > 0) {
			//Sorteer ze
			spelborden = spelborden.stream().sorted(Comparator.comparingInt(Spelbord::getVolgorde)).collect(Collectors.toList());
			//Zet het huidige spelbord op eerste en haal de velden en details op
			String naamEersteBord = spelborden.get(0).getSpelbordNaam();
			huidigSpelbord = spelbordRepository.geefSpelbordMetVelden(naamEersteBord, getAantalRijen(), getAantalKolommen());
		}


	}
	
	public Veld[][] geefVelden() {
		return huidigSpelbord.getVelden();
	}

	public String getSpelNaam() {
		return spelNaam;
	}
	public void setSpelNaam(String spelNaam) throws IllegalArgumentException {
		if (Objects.equals(spelNaam, null) || spelNaam.isEmpty())
			throw new IllegalArgumentException(Taal.vertaal("game_name") + Taal.vertaal("exception_not_null"));
		this.spelNaam = spelNaam;
	}

	public void beweeg(BeweegRichting richting) throws RuntimeException {
		huidigSpelbord.beweeg(richting);
	}

    public Moveable geefMannetje() {
		return huidigSpelbord.getMannetje();
    }

	public List<Moveable> geefKisten() {
		return huidigSpelbord.getKisten();
	}

	public boolean checkBordVoltooid() {
		//TO DO: REFACTOR! Dit is een dirty hack
		boolean voltooid = huidigSpelbord.isVoltooid();
		if(voltooid){
			List<Spelbord> vorigBord = spelborden.stream().filter(b -> b.getSpelbordNaam().equals(huidigSpelbord.getSpelbordNaam())).collect(Collectors.toList());
			int vorigBordIndex = spelborden.indexOf(vorigBord.get(0));
			spelborden.set(vorigBordIndex, huidigSpelbord);
			if (vorigBordIndex != spelborden.size()-1) {
				huidigSpelbord = spelbordRepository.geefSpelbordMetVelden(spelborden.get(vorigBordIndex+1).getSpelbordNaam(), getAantalRijen(), getAantalKolommen());
			} else {
				spelVoltooid = true;
			}
			
		}
		return voltooid;
	}
	
	public boolean checkSpelvoltooid() {
		return spelVoltooid;
	}
	
	public int getBordenVoltooid() {
		return (int) spelborden.stream().filter(b -> b.isVoltooid()).count();
	}
	public int getBordenTotaal() {
		return spelborden.size();
	}
	public void resetBord() {
		huidigSpelbord = spelbordRepository.geefSpelbordMetVelden(huidigSpelbord.getSpelbordNaam(), getAantalRijen(), getAantalKolommen());
	}
	public int getAantalBewegingen() {
		return huidigSpelbord.getAantalBewegingen();
	}
	public Speler getAanmaker() {
		return aanmaker;
	}
	protected void setAanmaker(Speler aanmaker) {
		this.aanmaker = aanmaker;
	}
	public int getAantalRijen() {
		return this.aantalRijen;
	}
	private void setAantalRijen(int aantalRijen) {
		if(Objects.equals(aantalRijen, null))
				throw new IllegalArgumentException("Het aantal rijen moet bepaald zijn.");
		this.aantalRijen = aantalRijen;
	}
	public int getAantalKolommen() {
		return this.aantalKolommen;
	}
	private void setAantalKolommen(int aantalKolommen) {
		if(Objects.equals(aantalKolommen, null))
				throw new IllegalArgumentException("Het aantal kolommen moet bepaald zijn.");
		this.aantalKolommen = aantalKolommen;
	}
	protected void voegNieuwSpelbordToe(String spelbordNaam) {
		int volgorde = spelborden.size()+1;
		
		//TO DO DELETE NU DUMMY IMPLEMENTATIE
		//Maak kopie van de velden van eerste bord
		Spelbord bordClone = spelbordRepository.geefSpelbordMetVelden("Eerste bord", getAantalRijen(), getAantalKolommen());
	
		Spelbord bord = new Spelbord(spelbordNaam, volgorde, bordClone.getMannetje(), bordClone.getKisten(), bordClone.getVelden());
		bord.setSpel(this);
		spelborden.add(bord);
	}
	protected void registreerBorden() {
		spelborden.stream().forEach(b -> spelbordRepository.insertBord(b));
	}
	public List<String> getBordnamen(){
		return spelborden.stream().map(b -> b.getSpelbordNaam()).collect(Collectors.toList());
	}
	
}