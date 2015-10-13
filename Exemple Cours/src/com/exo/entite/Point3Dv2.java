package com.exo.entite;

public class Point3Dv2 {

	private Point2D p;
	private int z;

	public Point3Dv2() {
		this(0, 0, 0);
	}

	public Point3Dv2(int unX, int unY, int unZ) {
		super();
		this.p = new Point2D(unX, unY);
		this.setZ(unZ);
	}

	/**
	 * @return
	 * @deprecated
	 * @see com.exo.entite.Point2D#getXx()
	 */
	@Deprecated
	public int getXx() {
		return this.p.getXx();
	}

	/**
	 * @param x
	 * @see com.exo.entite.Point2D#setXx(int)
	 */
	public void setXx(int x) {
		this.p.setXx(x);
	}

	/**
	 * @param y
	 * @see com.exo.entite.Point2D#setY(int)
	 */
	public void setY(int y) {
		this.p.setY(y);
	}

	/**
	 * @return the z
	 */
	public int getZ() {
		return this.z;
	}

	/**
	 * @param z
	 *            the z to set
	 */
	public void setZ(int z) {
		this.z = z;
	}

	/**
	 * @param dX
	 * @param dY
	 * @see com.exo.entite.Point2D#translater(int, int)
	 */
	public void translater(int dX, int dY, int dZ) {
		this.p.translater(dX, dY);
		this.setZ(this.getZ() + dZ);
	}

}
