package domein;

import java.util.List;
import java.util.Objects;

import persistentie.SpelbordMapper;
/**
 * Stelt een repository van spelborden voor.
 * @author g85
 */
public class SpelbordRepository {
	private final SpelbordMapper spelbordMapper;
	/**
	 * Creëert een instantie van de repository van spelborden. 
	 * Maakt een instantie van de SpelbordMapper-klasse aan bij initialisatie.
	 */
	public SpelbordRepository() {
		this.spelbordMapper = new SpelbordMapper();
	}
	/**
	 * Haalt het spelbord met de gegeven naam en de gegeven dimensies uit de database.
	 * @param spelbordNaam De naam van het spelbord.
	 * @param aantalRijen Het aantal rijen van het spelbord.
	 * @param aantalKolommen Het aantal kolommen van het spelbord.
	 * @return het spelbord met de gegeven naam en de gegeven dimensies.
	 */
	protected Spelbord geefSpelbordMetVelden(String spelbordNaam, int aantalRijen, int aantalKolommen) throws RuntimeException {
		return spelbordMapper.geefBordMetVelden(spelbordNaam, aantalRijen, aantalKolommen);
	}
	/**
	 * Haalt een lijst van de spelborden met de gegeven spelnaam uit de database.
	 * @param spelNaam De naam van het spel.
	 * @return de lijst van de spelborden met de gegeven spelnaam.
	 */
	protected List<Spelbord> geefSpelborden(String spelNaam) throws RuntimeException {
		return spelbordMapper.geefBorden(spelNaam);
	}
	/**
	 * Insert het gegeven spelbord van het gegeven spel in de database.
	 * @param spelbord Het gegeven spelbord.
	 * @param spelNaam De gegeven spelnaam.
	 */
	protected void insertBord(Spelbord spelbord, String spelNaam) throws RuntimeException {
		spelbordMapper.insertBord(spelbord, spelNaam);
	}
	/**
	 * Geeft terug of het spelbord met de gegeven naam bestaat.
	 * @param spelbordNaam De naam van het spelbord.
	 * @return true indien het spelbord bestaat, anders false.
	 */
	protected boolean bordExists(String spelbordNaam) throws RuntimeException {
		return !Objects.equals(spelbordMapper.geefBordMetVelden(spelbordNaam, BordDimensies.getAantalRijen(), BordDimensies.getAantalKolommen()), null); 
	}
}
