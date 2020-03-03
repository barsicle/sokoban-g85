package vertalingen;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

//UC2
/**
 * Stelt de gebruikte taal van de applicatie voor.
 * 
 * @author g85 
 */
public class Taal {

	private static ResourceBundle bundle = null;
	
	/**
	 * Stelt de taal van de applicatie in.
	 * 
	 * @param taal De gekozen taal van de applicatie.
	 */
	public static void setTaal(Talen taal){
		Locale.setDefault(new Locale(taal.toString(), "BE"));
		bundle = ResourceBundle.getBundle("vertalingen/Vertaling");
	}
	/**
	 * Controleert of de taal van de applicatie is ingesteld.
	 * @return true terug indien de taal is ingesteld, false indien de taal niet is ingesteld.
	 */
	public static boolean taalIngesteld() {
		return !Objects.equals(bundle, null);
	}
	/**
	 * Vertaalt een meegegeven String uit de ResourceBundle naar de gekozen taal.
	 * 
	 * @param woord De te vertalen String uit de ResourceBundle.
	 * @return de vertaalde String.
	 */
	public static String vertaal(String woord) {
		return bundle.getString(woord);
	}
}