package com.exo2;

public class Point2D {

	private int x;
	private int y;

	private static int nbObjet;

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
	 * @param x
	 * 			@param y
	 */
	public Point2D(int vX, int vY) {
		this.setX(vX);
		this.setY(vY);
		Point2D.nbObjet++;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}


	/**
	 * @return the y
	 */
	public int getY() {
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
	public static int getNbObjet() {
		return Point2D.nbObjet;
	}

	/**
	 *
	 */
	public void afficher() {
		System.out.println(this.toString());
	}

	/**
	 * @param dX
	 * 			@param dY
	 */
	public void translater(int dX, int dY) {
		this.setX(this.getX() + dX);
		this.setY(this.getY() + dY);
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
		builder.append(" [x=").append(this.getX());
		builder.append(", y=").append(this.getY());
		builder.append("]");
		return builder.toString();
	}
}
