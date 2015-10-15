package com.exo.classe;

public class Point2D {

	private int xx;
	private int y;

	private static int nbObjetM;

	// static{
	// Point2D.nbObjet++;
	// }

	/**
	 *
	 */
	public Point2D() {
		this(0, 0);
	}

	/**
	 * @param xx
	 * 			@param y
	 */
	public Point2D(int vX, int vY) {
		this.setXx(vX);
		this.setY(vY);
		Point2D.nbObjetM++;
	}

	/**
	 * @return the x
	 */
	@Deprecated
	public int getXx() {
		return this.xx;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setXx(int x) {
		this.xx = x;
	}


	/**
	 * @return the y
	 */
	public int changerY() {
		return this.y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the nbObjet
	 */
	public static int getNbObjetM() {
		return Point2D.nbObjetM;
	}

	/**
	 *
	 */
	public void afficher() {
		System.out.println("X = " + this.getXx() + ", Y = " + this.changerY());
	}

	/**
	 * @param dX
	 * 			@param dY
	 */
	public void translater(int dX, int dY) {
		this.setXx(this.getXx() + dX);
		this.setY(this.changerY() + dY);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getName());
		builder.append(" [x=").append(this.getXx());
		builder.append(", y=").append(this.changerY());
		builder.append("]");
		return builder.toString();
	}
}
