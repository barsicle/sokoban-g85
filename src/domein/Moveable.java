package domein;

import java.util.Objects;

import vertalingen.Taal;

public abstract class Moveable {
	protected Veld positie;
	
	public Moveable(Veld positie) {
			setPositie(positie);
	}
	
    public Veld getPositie() {
    	return positie;
    }

    public void setPositie(Veld positie) throws IllegalArgumentException {
		if (Objects.equals(positie, null))
			throw new IllegalArgumentException(Taal.vertaal("field") + Taal.vertaal("exception_not_null"));
    	this.positie = positie;
    }
}
