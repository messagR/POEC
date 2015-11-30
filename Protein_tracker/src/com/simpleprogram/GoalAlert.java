/**
 * test
 */
package com.simpleprogram;

/**
 * @author PC
 *
 */
public class GoalAlert {

	private int id;
	private String message;

	public GoalAlert() {
		this("");
	}

	public GoalAlert(String message) {
		this.setMessage(message);
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.delete(builder.length() - 1, builder.length());
		builder.append(" : id = ").append(this.getId()).append(", ");
		builder.append(" : message = ").append(this.getMessage());
		builder.append("]");
		return builder.toString();
	}

}
