package com.softwareBasket.demo.model;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class SelectedSoftwareDb {
	
	@Id
	@GeneratedValue
	private int id;
	private UUID ticketNo;
	private OffsetDateTime date;
	private int employeeId;
	private String managerEmail;
	private String directorEmail;
	private int totalCost;
	
	@OneToMany(targetEntity = SelectedSoftwareAbsDb.class, mappedBy = "ticketNo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<SelectedSoftwareAbsDb> softwareSelected;
	
	public int getId() {
		return id;
	}
	public List<SelectedSoftwareAbsDb> getSoftwareSelected() {
		return softwareSelected;
	}
	public void setSoftwareSelected(List<SelectedSoftwareAbsDb> softwareSelected) {
		this.softwareSelected = softwareSelected;
	}
	public void setId(int id) {
		this.id = id;
	}
	public UUID getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(UUID ticketNo) {
		this.ticketNo = ticketNo;
	}
	public OffsetDateTime getDate() {
		return date;
	}
	public void setDate(OffsetDateTime date) {
		this.date = date;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getManagerEmail() {
		return managerEmail;
	}
	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}
	public String getDirectorEmail() {
		return directorEmail;
	}
	public void setDirectorEmail(String directorEmail) {
		this.directorEmail = directorEmail;
	}
	public int getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(int totalCost) {
		this.totalCost = totalCost;
	}
	@Override
	public String toString() {
		return "SelectedSoftwareDb [id=" + id + ", ticketNo=" + ticketNo + ", date=" + date + ", employeeId="
				+ employeeId + ", managerEmail=" + managerEmail + ", directorEmail=" + directorEmail + ", totalCost="
				+ totalCost + ", softwareSelected=" + softwareSelected + "]";
	}
	
	

}
