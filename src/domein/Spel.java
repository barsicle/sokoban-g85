package domein;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import vertalingen.Taal;

/**
 * Stelt een spel voor.
 * 
 * @author g85
 */
public class Spel implements SpelInterface {
	private String spelNaam;
	private List<Spelbord> spelborden;
	private Speler aanmaker;
	private int huidigBordIndex;

	/**
	 * Creëert een spel met opgegeven naam en lijst van spelborden.
	 * 
	 * @param spelNaam           De naam van het spel.
	 * @param spelborden         Een lijst met spelborden waaruit het spel is
	 *                           samengesteld.
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
	
	/**
	 * Creëert een spel met opgegeven naam zonder spelborden.
	 * 
	 * @param spelNaam           De naam van het spel.
	 */
	
	public Spel(String spelNaam) {
		this(spelNaam, new ArrayList<Spelbord>());
	}
	
	/**
	 * Geeft de naam van het spel terug.
	 * @return de naam van het spel.
	 */
	public String getSpelNaam() {
		return spelNaam;
	}
	/**
	 * Stelt de naam van het spel in. Werpt een IllegalArgumentException indien er een lege string of null
	 * wordt meegegeven.
	 * @param spelNaam De naam van het spel.
	 * @throws IllegalArgumentException indien er een lege string of null
	 * wordt meegegeven.
	 */
	public void setSpelNaam(String spelNaam) throws IllegalArgumentException {
		if (Objects.equals(spelNaam, null) || spelNaam.isEmpty())
			throw new IllegalArgumentException(Taal.vertaal("game_name") + Taal.vertaal("exception_not_null"));
		this.spelNaam = spelNaam;
	}
	
	/**
	 * Controleert of het spel voltooid is.
	 * @return true indien het spel voltooid is, false indien het spel nog niet voltooid is.
	 */
	public boolean checkSpelvoltooid() {
		return getBordenVoltooid() == getBordenTotaal();
	}

	/**
	 * Geeft het aantal voltooide spelborden van het spel terug.
	 * @return het aantal voltooide spelborden.
	 */
	public int getBordenVoltooid() {
		return huidigBordIndex;
	}

	/**
	 * Geeft het totaal aantal spelborden van het spel terug.
	 * @return het aantal spelborden.
	 */
	public int getBordenTotaal() {
		return spelborden.size();
	}

	/**
	 * Geeft de maker van het spel terug.
	 * @return de speler die het spel heeft gemaakt.
	 */
	public Speler getAanmaker() {
		return aanmaker;
	}

	protected void setAanmaker(Speler aanmaker) {
		this.aanmaker = aanmaker;
	}

	protected void voegNieuwSpelbordToe(Spelbord spelbord) {
		spelborden.add(spelbord);
	}

	/**
	 * Geeft de namen van de spelborden van het spel terug.
	 * @return een lijst van de namen van de spelborden van het spel.
	 */
	public List<String> getBordnamen() {
		return spelborden.stream().map(b -> b.getSpelbordNaam()).collect(Collectors.toList());
	}

	protected void volgendBord() {
		//Haal bord op, zet de index klaar voor de volgende op te halen. Begint op 0 in constructor
		huidigBordIndex++;
	}
	/**
	 * Geeft de naam van het huidige spelbord terug.
	 * @return de naam van het huidige spelbord.
	 */
	
	public String getBordNaam() {
		return spelborden.get(huidigBordIndex).getSpelbordNaam();
	}

	/**
	 * Geeft een lijst van de spelborden terug.
	 * @return een lijst van de spelborden van het spel.
	 */
	public List<Spelbord> getSpelborden() {
		return spelborden;
	}

	/**
	 * Stelt de spelborden van het spel in aan de hand van de meegegeven lijst.
	 * @param spelborden de lijst van de spelborden.
	 */
	public void setSpelborden(List<Spelbord> spelborden) {
		this.spelborden = spelborden;
	}
	
	
	

}