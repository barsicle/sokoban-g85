package domein;

import java.util.Objects;

import vertalingen.Taal;

public class Moveable {
	protected Veld positie;
	private MoveableType type;
	
	public Moveable(Veld positie, MoveableType type) {
			setPositie(positie);
			this.type = type;
	}
	
    public Veld getPositie() {
    	return positie;
    }

    protected void setPositie(Veld positie) throws IllegalArgumentException {
		if (Objects.equals(positie, null))
			throw new IllegalArgumentException(Taal.vertaal("field") + Taal.vertaal("exception_not_null"));
    	this.positie = positie;
    }

	public MoveableType getType() {
		return type;
	}
    
    
}
