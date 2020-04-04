package domein;
/**
 * Interface voor het halen van de gegevens van Veld om weer te geven in de GUI, aangemaakt ter afscherming van de setters van Veld.
 * @author g85
 */
public interface VeldInterface {
	
	public int getX();
	
	public int getY();
	
	public VeldType getVeldType();
	
	public boolean isDoel();
	
	public Moveable getMoveable();
	
}
