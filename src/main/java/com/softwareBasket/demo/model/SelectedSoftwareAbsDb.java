package com.softwareBasket.demo.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SelectedSoftwareAbsDb {
	
	@Id
	@GeneratedValue
	private int id;
	private UUID ticketNo;
	private String software;
	private String softwareVersion;
	private boolean managerApproval;
	private boolean dhApproval;
	private String approvedBy;
	private String url;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name = "software_selected_id")
	public UUID getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(UUID ticketNo) {
		this.ticketNo = ticketNo;
	}
	public String getSoftware() {
		return software;
	}
	public void setSoftware(String software) {
		this.software = software;
	}
	public String getSoftwareVersion() {
		return softwareVersion;
	}
	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public boolean isManagerApproval() {
		return managerApproval;
	}
	public void setManagerApproval(boolean managerApproval) {
		this.managerApproval = managerApproval;
	}
	public boolean isDhApproval() {
		return dhApproval;
	}
	public void setDhApproval(boolean dhApproval) {
		this.dhApproval = dhApproval;
	}
	@Override
	public String toString() {
		return "SelectedSoftwareAbsDb [id=" + id + ", ticketNo=" + ticketNo + ", software=" + software
				+ ", softwareVersion=" + softwareVersion + ", managerApproval=" + managerApproval + ", dhApproval="
				+ dhApproval + ", approvedBy=" + approvedBy + ", url=" + url + "]";
	}	

}
