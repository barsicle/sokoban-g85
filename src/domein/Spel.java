package domein;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Stelt het spel voor.
 * @author g85
 */
public class Spel {
	private String spelNaam;
	private List<Spelbord> spelborden;
	private Spelbord huidigSpelbord;
	private boolean spelVoltooid = false;
	private final SpelbordRepository spelbordRepository = new SpelbordRepository();
	
	public Spel(String spelNaam) {
		this.spelNaam = spelNaam;
	}
	public Spel(String spelNaam, List<Spelbord> borden) {
		this.spelNaam = spelNaam;
		this.spelborden = borden;
		//Sorteer ze
		this.spelborden = spelborden.stream().sorted(Comparator.comparingInt(Spelbord::getVolgorde)).collect(Collectors.toList());
		//Zet het huidige spelbord op eerste
		String naamEersteBord = spelborden.get(0).getSpelbordNaam();
		huidigSpelbord = spelbordRepository.geefSpelbordMetVelden(naamEersteBord);
	}
	public Veld[][] geefVelden() {
		return huidigSpelbord.getVelden();
	}

	public String getSpelNaam() {
		return spelNaam;
	}
	public void setSpelNaam(String spelNaam) {
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
				huidigSpelbord = spelbordRepository.geefSpelbordMetVelden(spelborden.get(vorigBordIndex+1).getSpelbordNaam());
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
		huidigSpelbord = spelbordRepository.geefSpelbordMetVelden(huidigSpelbord.getSpelbordNaam());
	}
	public int getAantalBewegingen() {
		return huidigSpelbord.getAantalBewegingen();
	}
}