package domein;

import java.util.List;

import persistentie.SpelMapper;
/**
 * Stelt een repository van de spelen voor.
 * @author g85
 */
public class SpelRepository {
	
	private final SpelMapper spelMapper;
	/**
	 * Creëert een instantie van de repository van de spelen. 
	 * Maakt een instantie van de SpelMapper-klasse aan bij initialisatie.
	 */
	public SpelRepository() {
		spelMapper = new SpelMapper();
	}
	/**
	 * Geeft een lijst van de namen de spelen terug.
	 * @return een lijst van de namen van de spelen.
	 */
	public List<String> getSpelNamen() throws RuntimeException{
		return spelMapper.getSpelNamen();
	}
	/**
	 * Haalt het spel met opgegeven spelnaam op van de database.
	 * @param spelNaam De naam van het gewenste spel.
	 * @return het spel met de opgegeven naam.
	 */
	public Spel geefSpel(String spelNaam) throws RuntimeException {
		return spelMapper.geefSpel(spelNaam);
	}
	/**
	 * Insert het gegeven spel in de database.
	 * @param spel het spel om in database te inserten.
	 */
	public void insertSpel(Spel spel)  throws RuntimeException {
		spelMapper.insertSpel(spel);
	}

}
