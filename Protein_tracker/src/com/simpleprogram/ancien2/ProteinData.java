/**
 * test
 */
package com.simpleprogram.ancien2;

/**
 * @author PC
 *
 */
public class ProteinData {

	private int total; // conso total de protéine
	private int goal; // conso visée de protéine

	public ProteinData() {
		this(0, 100);
	}

	public ProteinData(int total, int goal) {
		this.setTotal(total);
		this.setGoal(goal);
	}

	public int getTotal() {
		return this.total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getGoal() {
		return this.goal;
	}

	public void setGoal(int goal) {
		this.goal = goal;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.delete(builder.length() - 1, builder.length());
		builder.append(" : total = ").append(this.getTotal()).append(", ");
		builder.append("goal = ").append(this.getGoal());
		builder.append("]");
		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof ProteinData) {
			ProteinData proteinData = (ProteinData) obj;
			return ((this.getTotal() == proteinData.getTotal()) && (this.getGoal() == proteinData.getGoal()));
		}
		return false;
	}

}
