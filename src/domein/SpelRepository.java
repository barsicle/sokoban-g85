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
	public List<String> getSpelNamen(){
		return spelMapper.getSpelNamen();
	}
	/**
	 * Haalt het spel met opgegeven spelnaam op van de database.
	 * @return het spel met de opgegeven naam.
	 */
	public Spel geefSpel(String spelNaam) {
		return spelMapper.geefSpel(spelNaam);
	}
	/**
	 * Insert het gegeven spel in de database.
	 * @param het spel om in database te inserten.
	 */
	public void insertSpel(Spel spel) {
		spelMapper.insertSpel(spel);
	}

}
