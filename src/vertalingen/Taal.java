package vertalingen;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class Taal {

	private static ResourceBundle bundle = null;
	
	public static void setTaal(Talen taal){
		Locale.setDefault(new Locale(taal.toString(), "BE"));
		bundle = ResourceBundle.getBundle("vertalingen/Vertaling");
	}
	
	public static boolean taalIngesteld() {
		return !Objects.equals(bundle, null);
	}
	
	public static String vertaal(String woord) {
		return bundle.getString(woord);
	}
}