/**
 * test
 */
package com.simpleprogram.ancien2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PC
 *
 */
public class User {

	private int id;
	private String name;
	private ProteinData proteinData;
	private List<UserHistory> history = new ArrayList<UserHistory>();

	User() {
		this("");
	}

	User(String name) {
		this(name, new ProteinData());
	}

	User(String name, ProteinData proteinData) {
		this.setName(name);
		this.setProteinData(proteinData);
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProteinData getProteinData() {
		return this.proteinData;
	}

	public void setProteinData(ProteinData proteinData) {
		this.proteinData = proteinData;
	}

	public List<UserHistory> getHistory() {
		return this.history;
	}

	public void setHistory(List<UserHistory> history) {
		this.history = history;
	}

	public void addHistory(UserHistory history) {
		history.setUser(this);
		this.getHistory().add(history);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.delete(builder.length() - 1, builder.length());
		builder.append(" : id = ").append(this.getId());
		if (this.getName() != null) {
			builder.append(", ").append("name = ").append(this.getName());
		}
		if (this.getProteinData() != null) {
			builder.append(", ").append("proteinData = ").append(this.getProteinData().toString());
		}
		builder.append("]");
		return builder.toString();
	}
}
