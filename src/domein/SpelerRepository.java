package domein;

import java.util.Objects;
import persistentie.*;
import vertalingen.Taal;

/**
 * Stelt een repository van spelers voor.
 * @author g85
 */
public class SpelerRepository {
	
	private final SpelerMapper spelerMapper;
	
	/**
	 * Creëert een instantie van de repository van spelers. 
	 * Maakt een instantie van de SpelerMapper-klasse aan bij initialisatie.
	 */
	public SpelerRepository() {
		
		spelerMapper = new SpelerMapper();
		
	}
	
	/**
	 * Geeft de speler met opgegeven gebruikersnaam terug. Werpt een RuntimeException indien
	 *  er een probleem is met de database.
	 * @param gebruikersnaam De gebruikersnaam waarmee de speler wordt opgezocht.
	 * @return de speler met gegeven gebruikersnaam.
	 */ 
	protected Speler geefSpeler(String gebruikersnaam) throws RuntimeException {
		
		Speler speler = spelerMapper.geefSpeler(gebruikersnaam);
        return speler;
        
	}
	
	/**
	 * Voegt de opgegeven speler toe aan de database. Werpt een IllegalArgumentException indien de opgegeven speler
	 * al in de database zit.
	 * @param speler De speler die wordt toegevoegd aan de database.
	 */ 
	protected void voegSpelerToe(Speler speler) throws IllegalArgumentException, RuntimeException {
		
	       if (bestaatSpeler(speler.getGebruikersnaam()))
	            throw new IllegalArgumentException(Taal.vertaal("exception_user_exists"));
	       
	       spelerMapper.voegSpelerToe(speler);
	       
	}
	
	/**
	 * Controleert of de speler met opgegeven gebruikersnaam al bestaat. Werpt een RuntimeException indien
	 * dit zo is.
	 * @param gebruikersnaam De gebruikersnaam waarmee de speler wordt opgezocht in de database.
	 * @return true indien de speler al bestaat, false indien de speler nog niet bestaat.
	 */ 
    protected boolean bestaatSpeler(String gebruikersnaam) throws RuntimeException {
        return !Objects.equals(spelerMapper.geefSpeler(gebruikersnaam), null);
    }
	
}
