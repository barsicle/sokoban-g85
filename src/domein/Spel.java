package domein;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import vertalingen.Taal;

/**
 * Stelt het spel voor.
 * 
 * @author g85
 */
public class Spel implements SpelInterface {
	private String spelNaam;
	private List<Spelbord> spelborden;
	private Speler aanmaker;
	private int huidigBordIndex;

	/**
	 * Creëert een spel met opgegeven naam.
	 * 
	 * @param spelNaam           De naam van het spel.
	 * @param spelborden         Een lijst met spelborden waaruit het spel is
	 *                           samengesteld.
	 * @param spelbordRepository Repository om spelborden op te halen.
	 */
	public Spel(String spelNaam, List<Spelbord> spelborden) {
		setSpelNaam(spelNaam);
		this.spelborden = spelborden;
		huidigBordIndex = 0;
		
		// Check of er borden zijn, en indien ja sorteren
		if (spelborden.size() > 0) {
			// Sorteer ze
			spelborden = spelborden.stream().sorted(Comparator.comparingInt(Spelbord::getVolgorde))
					.collect(Collectors.toList());
		}

	}

	public String getSpelNaam() {
		return spelNaam;
	}

	public void setSpelNaam(String spelNaam) throws IllegalArgumentException {
		if (Objects.equals(spelNaam, null) || spelNaam.isEmpty())
			throw new IllegalArgumentException(Taal.vertaal("game_name") + Taal.vertaal("exception_not_null"));
		this.spelNaam = spelNaam;
	}

	public boolean checkSpelvoltooid() {
		return getBordenVoltooid() == getBordenTotaal();
	}

	public int getBordenVoltooid() {
		return huidigBordIndex;
	}

	public int getBordenTotaal() {
		return spelborden.size();
	}

	public Speler getAanmaker() {
		return aanmaker;
	}

	protected void setAanmaker(Speler aanmaker) {
		this.aanmaker = aanmaker;
	}

	protected void voegNieuwSpelbordToe(Spelbord spelbord) {
		spelborden.add(spelbord);
	}

	public List<String> getBordnamen() {
		return spelborden.stream().map(b -> b.getSpelbordNaam()).collect(Collectors.toList());
	}

	protected void volgendBord() {
		//Haal bord op, zet de index klaar voor de volgende op te halen. Begint op 0 in constructor
		huidigBordIndex++;
	}
	
	public String getBordNaam() {
		return spelborden.get(huidigBordIndex).getSpelbordNaam();
	}

	public List<Spelbord> getSpelborden() {
		return spelborden;
	}

	public void setSpelborden(List<Spelbord> spelborden) {
		this.spelborden = spelborden;
	}
	
	
	

}