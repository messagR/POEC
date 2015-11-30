/**
 * test
 */
package com.simpleprogram;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author PC
 *
 */
public class User {

	private int id;
	private String name;
	private ProteinData proteinData;
	private List<UserHistory> history = new ArrayList<UserHistory>();
	private Set<GoalAlert> goalAlert = new HashSet<GoalAlert>();

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

	public Set<GoalAlert> getGoalAlert() {
		return this.goalAlert;
	}

	public void setGoalAlert(Set<GoalAlert> goalAlert) {
		this.goalAlert = goalAlert;
	}

	public void addGoalAlert(GoalAlert goalAlert) {
		this.getGoalAlert().add(goalAlert);
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
