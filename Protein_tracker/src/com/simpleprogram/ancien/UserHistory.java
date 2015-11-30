/**
 * test
 */
package com.simpleprogram.ancien;

import java.util.Date;

/**
 * @author PC
 *
 */
public class UserHistory {

	private Date entryTime;
	private String entry;

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.delete(builder.length() - 1, builder.length());
		builder.append(" : entryTime = ").append(this.getEntryTime()).append(", ");
		if (this.getEntry() != null) {
			builder.append(", ").append("entry = ").append(this.getEntry());
		}
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.entry == null) ? 0 : this.entry.hashCode());
		result = (prime * result) + ((this.entryTime == null) ? 0 : this.entryTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (this.getClass() != obj.getClass()) {
			return false;
		}

		UserHistory other = (UserHistory) obj;
		if (this.entry == null) {
			if (other.entry != null) {
				return false;
			}
		} else if (!this.entry.equals(other.entry)) {
			return false;
		}

		if (this.entryTime == null) {
			if (other.entryTime != null) {
				return false;
			}
		} else if (!this.entryTime.equals(other.entryTime)) {
			return false;
		}

		return true;
	}

}
