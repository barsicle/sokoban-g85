package domein;

public abstract class Moveable {
	protected Veld positie;
	
	public Moveable(Veld positie) {
		this.positie = positie;
	}
	
    public Veld getPositie() {
    	return positie;
    }

    public void setPositie(Veld positie) {
    	this.positie = positie;
    }
}
