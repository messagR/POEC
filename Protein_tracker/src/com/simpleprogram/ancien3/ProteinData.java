/**
 * test
 */
package com.simpleprogram.ancien3;


/**
 * @author PC
 *
 */
public class ProteinData {

	private int id;
	private int total; // conso total de protéine
	private int goal; // conso visée de protéine
	private User user;

	public ProteinData() {
		this(0, 100);
	}

	public ProteinData(int total, int goal) {
		this.setTotal(total);
		this.setGoal(goal);
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.delete(builder.length() - 1, builder.length());
		builder.append(" : id = ").append(this.getId()).append(", ");
		builder.append(" : total = ").append(this.getTotal()).append(", ");
		builder.append("goal = ").append(this.getGoal());
		builder.append("]");
		return builder.toString();
	}

}
