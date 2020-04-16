package domein;

import java.util.Objects;

import vertalingen.Taal;
/**
 * Stelt een veld op het spelbord voor.
 * @author g85
 */
public class Veld implements VeldInterface {

	private VeldType veldType;
	private boolean doel;
	private int x;
	private int y;
	private Moveable moveable;
	/**
	 * Creëert een veld met opgegeven veldtype, al dan niet een doel op de gegeven coördinaten.
	 * @param veldType Het type veld.
	 * @param doel Of het veld een doel is.
	 * @param x De rij van het veld.
	 * @param y De kolom van het veld.
	 */
	public Veld(VeldType veldType, boolean doel, int x, int y) {
		setVeldType(veldType);
		this.doel = doel;
		this.x = x;
		this.y = y;
	}
	/**
	 * Geeft het type van het veld terug.
	 * @return Het veldtype.
	 */
	public VeldType getVeldType() {
		return veldType;
	}
	/**
	 * Geeft terug of het veld een doel is.
	 * @return true indien het vel een doel is, anders false.
	 */
	public boolean isDoel() {
		return doel;
	}
	/**
	 * Geeft de rij van het veld terug.
	 * @return de rij van het veld.
	 */
	public int getX() {
		return x;
	}
	/**
	 * Geeft de kolom van het veld terug.
	 * @return de kolom van het veld.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Stelt de rij van het veld in.
	 * @param x de rij van het veld.
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * Stelt de kolom van het veld in.
	 * @param y de kolom van het veld.
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * Stelt het veldtype van het veld in. Werpt een IllegalArgumentException indien het veldtype null is.
	 * @param value het gewenste veldtype.
	 * @throws IllegalArgumentException indien het veldtype null is.
	 */
	public void setVeldType(VeldType value) throws IllegalArgumentException{
		if (Objects.equals(value, null))
			throw new IllegalArgumentException(Taal.vertaal("fieldtype") + Taal.vertaal("exception_not_null"));
		this.veldType = value;
	}
	/**
	 * Geeft de moveable van het veld terug.
	 * @return de moveable van het veld.
	 */
	public Moveable getMoveable() {
		return moveable;
	}
	/**
	 * Stelt de moveable van het veld in.
	 * @param moveable de gewenste moveable van het veld.
	 */
	public void setMoveable(Moveable moveable) {
		this.moveable = moveable;
	}
}
