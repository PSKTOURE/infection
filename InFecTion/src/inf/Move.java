package inf;

/**
 * Classe representant un Move
 * @param xi, yi : representant les cordonnées initiales
 * 		  xf, yf les coordonnées finales
 * @param isClone : indiquant si le pions s'est cloné ou pas
 * @author toure215
 *
 */

public class Move {

	private int xi;
	private int yi;
	private int xf;
	private int yf;
	private boolean isClone;

	public Move(int xi, int yi, int xf, int yf, boolean isClone) {
		this.setXi(xi);
		this.setYi(yi);
		this.setXf(xf);
		this.setYf(yf);
		this.setClone(isClone);
	}

	public Move() {

	}

	public boolean isClone() {
		return isClone;
	}

	public void setClone(boolean isClone) {
		this.isClone = isClone;
	}

	public int getYf() {
		return yf;
	}

	public void setYf(int yf) {
		this.yf = yf;
	}

	public int getXf() {
		return xf;
	}

	public void setXf(int xf) {
		this.xf = xf;
	}

	public int getYi() {
		return yi;
	}

	public void setYi(int yi) {
		this.yi = yi;
	}

	public int getXi() {
		return xi;
	}

	public void setXi(int xi) {
		this.xi = xi;
	}

	@Override
	public String toString() {
		return "(" + xi + "," + yi + ") --> (" + xf + "," + yf + ") , " + isClone;
	}

}
