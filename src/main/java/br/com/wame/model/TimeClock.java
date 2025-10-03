package br.com.wame.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.wame.enums.Status;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeClock {
	
	private Long ID;
	private String startShift;
	private String endShift;
	private String clockIn;
	private String clockOut;
	private boolean completed;
	private Status status;
	
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
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
	public String getClockIn() {
		return clockIn;
	}
	public void setClockIn(String clockIn) {
		this.clockIn = clockIn;
	}
	public String getClockOut() {
		return clockOut;
	}
	public void setClockOut(String clockOut) {
		this.clockOut = clockOut;
	}
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	

}
