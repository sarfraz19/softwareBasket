package com.softwareBasket.demo.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class SelectedSoftwareAbsDb {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prooduct_id_seq")
    @SequenceGenerator(name="prooduct_id_seq", sequenceName = "PRODUCT_ID_SEQ", allocationSize = 100)
	private Integer id;
	private UUID ticketNo;
	private String software;
	private String softwareVersion;
	private boolean managerApproval;
	private boolean dhApproval;
	private String approvedBy;
	private String url;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
		return "SelectedSoftwareAbsDb [ticketNo=" + ticketNo + ", software=" + software + ", softwareVersion="
				+ softwareVersion + ", managerApproval=" + managerApproval + ", dhApproval=" + dhApproval
				+ ", approvedBy=" + approvedBy + ", url=" + url + "]";
	}	

}
