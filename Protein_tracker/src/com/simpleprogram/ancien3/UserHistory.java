/**
 * test
 */
package com.simpleprogram.ancien3;

import java.util.Date;

/**
 * @author PC
 *
 */
public class UserHistory {

	private int id;
	private Date entryTime;
	private String entry;
	private User user;

	public UserHistory() {
		this(new Date());
	}

	public UserHistory(Date date) {
		this(date, "");
	}

	public UserHistory(Date entryDate, String entry) {
		this.setEntryTime(entryDate);
		this.setEntry(entry);
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getEntryTime() {
		return this.entryTime;
	}

	public void setEntryTime(Date entryDate) {
		this.entryTime = entryDate;
	}

	public String getEntry() {
		return this.entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
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
		builder.append(" : id = ").append(this.getId());
		builder.append(" : entryTime = ").append(this.getEntryTime()).append(", ");
		if (this.getEntry() != null) {
			builder.append(", ").append("protein = ").append(this.getEntry());
		}
		if (this.getUser() != null) {
			builder.append(", ").append("user = ").append(this.getUser().toString());
		}
		builder.append("]");
		return builder.toString();
	}

}
