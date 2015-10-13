package com.exo.entite;

public class Point3D extends Point2D {

	private int z;

	public Point3D() {
		super();
	}

	public Point3D(int vX, int vY, int vZ) {
		super(vX, vY);
		this.setZ(vZ);
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

	/*
	 * (non-Javadoc)
	 *
	 * @see com.exo2.Point2D#afficher()
	 */
	@Override
	public void afficher() {
		super.afficher();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.exo2.Point2D#translater(int, int)
	 */
	public void translater(int dX, int dY, int dZ) {
		super.translater(dX, dY);
		this.setZ(this.getZ() + dZ);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.delete(builder.length() - 1, builder.length());
		builder.append(", z=").append(this.getZ());
		builder.append("]");
		return builder.toString();
	}

}
