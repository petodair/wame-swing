package br.com.wame.model;

import java.time.DayOfWeek;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Work {

	private Long ID;
	private String name;
	private String description;
	private String startDate;
	private String endDate;
	private User user;
	private boolean finalized;
	private Set<DayOfWeek> daysOfWeek;
    private String startShift;
    private String endShift;
    private int numberOfShifts;
	
	public Work() {}
	
	public Work(String name, String description, String startDate, String endDate, 
			String startShift, String endShift) {
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startShift = startShift;
		this.endShift = endShift;
	}


	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public boolean isFinalized() {
		return finalized;
	}
	public void setFinalized(boolean finalized) {
		this.finalized = finalized;
	}

	public User getUser() {
		return user;
	}

	public Set<DayOfWeek> getDaysOfWeek() {
		return daysOfWeek;
	}

	public void setDaysOfWeek(Set<DayOfWeek> daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
	}

	public String getStartShift() {
		return startShift;
	}

	public void setStartShift(String startShift) {
		this.startShift = startShift;
	}

	public String getEndShift() {
		return endShift;
	}

	public void setEndShift(String endShift) {
		this.endShift = endShift;
	}

	public int getNumberOfShifts() {
		return numberOfShifts;
	}

	public void setNumberOfShifts(int numberOfShifts) {
		this.numberOfShifts = numberOfShifts;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Work [ID=" + ID + ", name=" + name + ", description=" + description + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", user=" + user + ", finalized=" + finalized + "]";
	}
	
	
}
