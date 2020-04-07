package domein;

import java.util.Objects;

import vertalingen.Taal;
/**
 * Stelt de moveable op het veld voor.
 * @author g85
 */
public class Moveable {
	protected Veld positie;
	private MoveableType type;
	/**
	 * Creëert een moveable van het gegeven type het gegeven veld.
	 * @param positie Het veld waarop de moveable komt.
	 * @param type Het soort moveable.
	 */
	public Moveable(Veld positie, MoveableType type) {
			setPositie(positie);
			this.type = type;
	}
	/**
	 * Geeft het veld terug waarop de moveable zich bevindt.
	 * @return het veld waarop de moveable zich bevindt.
	 */
    public Veld getPositie() {
    	return positie;
    }

    protected void setPositie(Veld positie) throws IllegalArgumentException {
		if (Objects.equals(positie, null))
			throw new IllegalArgumentException(Taal.vertaal("field") + Taal.vertaal("exception_not_null"));
    	this.positie = positie;
    }
	/**
	 * Geeft het type van de moveable terug.
	 * @return het type van de moveable.
	 */
	public MoveableType getType() {
		return type;
	}
    
    
}
