/**
 * test
 */
package com.simpleprogram.ancien3;

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
	private GoalAlert goalAlert;

	User() {
		this("");
	}

	User(String name) {
		this(name, new ProteinData());
	}

	User(String name, ProteinData proteinData) {
		this.setProteinData(proteinData);
		this.setName(name);
	}

	User(String name, ProteinData proteinData, GoalAlert goalAlert) {
		this.setGoalAlert(goalAlert);
		this.setProteinData(proteinData);
		this.setName(name);
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
		proteinData.setUser(this);
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

	public GoalAlert getGoalAlert() {
		return this.goalAlert;
	}

	public void setGoalAlert(GoalAlert goalAlert) {
		this.goalAlert = goalAlert;
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
