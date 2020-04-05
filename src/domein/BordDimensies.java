package domein;

public class BordDimensies {
	
	private static int aantalRijen = 10;
	private static int aantalKolommen = 10;
	
	
	public BordDimensies() {
		setAantalRijen(10);
		setAantalKolommen(10);
	}
	
	
	public static int getAantalRijen() {
		return aantalRijen;
	}
	public static void setAantalRijen(int aantalRijen) {
		if(aantalRijen <= 0 || aantalRijen > 10) {
			throw new IllegalArgumentException("Min 1 max 10");
		}
		BordDimensies.aantalRijen = aantalRijen;
	}
	public static int getAantalKolommen() {
		return aantalKolommen;
	}
	public static void setAantalKolommen(int aantalKolommen) {
		if(aantalKolommen <= 0 || aantalKolommen > 10) {
			throw new IllegalArgumentException("Min 1 max 10");
		}
		BordDimensies.aantalKolommen = aantalKolommen;
	}
	
	

}
