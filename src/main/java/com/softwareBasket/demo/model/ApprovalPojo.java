package com.softwareBasket.demo.model;

import java.util.UUID;

public class ApprovalPojo {

	private UUID ticketNo;
	private String software;
	private String softwareVersion;
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
	@Override
	public String toString() {
		return "ApprovalPojo [ticketNo=" + ticketNo + ", software=" + software + ", softwareVersion=" + softwareVersion
				+ "]";
	}
	
}
