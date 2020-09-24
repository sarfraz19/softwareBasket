package com.softwareBasket.demo.model;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class SelectedSoftwareDb {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prooduct_id_seq")
    @SequenceGenerator(name="prooduct_id_seq", sequenceName = "PRODUCT_ID_SEQ", allocationSize = 100)
	private Integer id;
	private Long employeeId;
	private UUID ticketNo;
	private OffsetDateTime date;
	private String managerEmail;
	private String directorEmail;
	private int totalCost;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@OneToMany(targetEntity = SelectedSoftwareAbsDb.class, mappedBy = "ticketNo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<SelectedSoftwareAbsDb> softwareSelected;
	
	public List<SelectedSoftwareAbsDb> getSoftwareSelected() {
		return softwareSelected;
	}
	public void setSoftwareSelected(List<SelectedSoftwareAbsDb> softwareSelected) {
		this.softwareSelected = softwareSelected;
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
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
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
		return "SelectedSoftwareDb [id=" + id + ", employeeId=" + employeeId + ", ticketNo=" + ticketNo + ", date="
				+ date + ", managerEmail=" + managerEmail + ", directorEmail=" + directorEmail + ", totalCost="
				+ totalCost + ", softwareSelected=" + softwareSelected + "]";
	}
	
	

}
