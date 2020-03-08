package domein;

public class Veld {

	private VeldType veldType;
	private boolean doel;
	private int x;
	private int y;
	private Moveable moveable;
	
	public Veld(VeldType veldType, boolean doel, int x, int y) {
		this.veldType = veldType;
		this.doel = doel;
		this.x = x;
		this.y = y;
	}
	
	public VeldType getVeldType() {
		return veldType;
	}
	public boolean isDoel() {
		return doel;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setVeldType(VeldType value) {
		this.veldType = value;
	}

	public Moveable getMoveable() {
		return moveable;
	}

	public void setMoveable(Moveable moveable) {
		this.moveable = moveable;
	}
}
