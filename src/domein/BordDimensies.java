package domein;
/**
 * De dimensies van een spelbord.
 * @author g85
 */
public class BordDimensies {
	
	private static int aantalRijen = 10;
	private static int aantalKolommen = 10;
	
	/**
	 * Creëert een instantie van de dimensies van het spelbord.
	 */
	public BordDimensies() {
		setAantalRijen(10);
		setAantalKolommen(10);
	}
	
	/**
	 * Geeft het aantal rijen van het spelbord terug.
	 * @return het aantal rijen van het spelbord.
	 */
	public static int getAantalRijen() {
		return aantalRijen;
	}
	/**
	 * Stelt het aantal rijen van een spelbord in.
	 * @param aantalRijen Het aantal rijen.
	 */
	public static void setAantalRijen(int aantalRijen) {
		if(aantalRijen <= 0 || aantalRijen > 10) {
			throw new IllegalArgumentException("Min 1 max 10");
		}
		BordDimensies.aantalRijen = aantalRijen;
	}
	/**
	 * Geeft het aantal kolommen van het spelbord terug.
	 * @return het aantal kolommen van het spelbord.
	 */
	public static int getAantalKolommen() {
		return aantalKolommen;
	}
	/**
	 * Stelt het aantal kolommen van een spelbord in.
	 * @param aantalKolommen Het aantal kolommen.
	 */
	public static void setAantalKolommen(int aantalKolommen) {
		if(aantalKolommen <= 0 || aantalKolommen > 10) {
			throw new IllegalArgumentException("Min 1 max 10");
		}
		BordDimensies.aantalKolommen = aantalKolommen;
	}
	
	

}
