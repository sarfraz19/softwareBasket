package com.softwareBasket.demo.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.internet.MimeMessage;

import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwareBasket.demo.dao.SelectedSoftwareAbsRepo;
import com.softwareBasket.demo.dao.SoftwareAvailableRepo;
import com.softwareBasket.demo.dao.SoftwareDetailsRepo;
import com.softwareBasket.demo.dao.SoftwareSelectedRepo;
import com.softwareBasket.demo.model.ApprovalPojo;
import com.softwareBasket.demo.model.FinalSoftwareDetails;
import com.softwareBasket.demo.model.SelectedSoftware;
import com.softwareBasket.demo.model.SelectedSoftwareAbs;
import com.softwareBasket.demo.model.SelectedSoftwareAbsDb;
import com.softwareBasket.demo.model.SelectedSoftwareDb;
import com.softwareBasket.demo.model.SoftwareAvailable;
import com.softwareBasket.demo.model.SoftwareDetails;


@Service
public class SoftwareService {
	
	@Value("${spring.mail.username}")
	private String fromEmail;
	
	@Autowired
	SoftwareAvailableRepo avilableRepo;
	
	@Autowired
	SoftwareDetailsRepo softwareDetailsRepo;
	
	@Autowired
	SoftwareAvailableRepo softwareAvailableRepo;
	
	@Autowired
	SoftwareSelectedRepo softwareSelectedRepo;
	
	@Autowired
	SelectedSoftwareAbsRepo selectedSoftwareAbsRepo;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private JavaMailSender javaMailSender;
	
	public SoftwareService(JavaMailSender javaMailSender) {
		super();
		this.javaMailSender = javaMailSender;
	}
	
	Iterable<SoftwareAvailable> soft2;
	
	public List<FinalSoftwareDetails> getSoftwareList() throws JsonProcessingException{
		
		
		List<FinalSoftwareDetails> softwareDetailsList = new ArrayList<FinalSoftwareDetails>();
		soft2 = avilableRepo.findAll();
		
		for(SoftwareAvailable swAvailable : soft2) {
			for(SoftwareDetails swDetails : swAvailable.getSoftwareDetails()) {
				
				FinalSoftwareDetails softwareDetails = new FinalSoftwareDetails();
				
				softwareDetails.setSoftwareName(swAvailable.getSoftwareName());
				softwareDetails.setVersion(swDetails.getVersions());
				softwareDetails.setSoftwareNameVersion(swAvailable.getSoftwareName() +"-"+ swDetails.getVersions());
				softwareDetails.setPaid(swDetails.isPaid());
				softwareDetailsList.add(softwareDetails);
			}
		}
		
		String JsonOutput = objectMapper.writeValueAsString(softwareDetailsList);
		System.out.println(JsonOutput);

		return softwareDetailsList;
	}

	public String saveTicketRequest(SelectedSoftware selectedSoftware) {
		
		SelectedSoftwareDb selectedSoftwareDb = new SelectedSoftwareDb();
		
		List<SelectedSoftwareAbsDb> selectedSoftwareAbsDbList = new ArrayList<>();
		SoftwareAvailable softAvailable = new SoftwareAvailable();
		SoftwareDetails softDetails = new SoftwareDetails();
		
		String ticketNo = UUID.randomUUID().toString();
		OffsetDateTime date = OffsetDateTime.now();
		selectedSoftwareDb.setDate(date);
		selectedSoftwareDb.setTicketNo(ticketNo);
		selectedSoftwareDb.setEmployeeId(selectedSoftware.getEmployeeId());
		selectedSoftwareDb.setEmployeeEmail(selectedSoftware.getEmployeeEmail());
		selectedSoftwareDb.setManagerEmail(selectedSoftware.getManagerEmail());
		selectedSoftwareDb.setDirectorEmail(selectedSoftware.getDirectorEmail());
		
		int cost = 0;

		for (SelectedSoftwareAbs selectedSoftwareAbs : selectedSoftware.getSelectedSoftware()) {
			
			SelectedSoftwareAbsDb selectedSoftwareAbsDb = new SelectedSoftwareAbsDb();
			selectedSoftwareAbsDb.setTicketNo(ticketNo);
			selectedSoftwareAbsDb.setSoftware(selectedSoftwareAbs.getSoftwareName());
			selectedSoftwareAbsDb.setSoftwareVersion(selectedSoftwareAbs.getSoftwareVersion());
			
			softAvailable = softwareAvailableRepo.findBySoftwareName(selectedSoftwareAbs.getSoftwareName()).get(0);
			softDetails = softwareDetailsRepo.findBySoftwareIdAndVersions(softAvailable.getSoftwareId(), selectedSoftwareAbs.getSoftwareVersion()).get(0);
			
			if (softDetails.isPaid()) {
			cost = cost + Integer.valueOf(softDetails.getCost());
			}
			System.out.println(cost);
			
			
			int softCost = Integer.valueOf(softDetails.getCost());
			
			if (softCost > 19 && softCost <100 ) {	
				selectedSoftwareAbsDb.setManagerApproval(true);
				selectedSoftwareAbsDb.setApprovedBy("Waiting");
			} else if (softCost > 100) {
				selectedSoftwareAbsDb.setDhApproval(true);
				selectedSoftwareAbsDb.setApprovedBy("Waiting");
			} else {
				selectedSoftwareAbsDb.setManagerApproval(false);
				selectedSoftwareAbsDb.setDhApproval(false);
				selectedSoftwareAbsDb.setApprovedBy("Free");
			}
			
			selectedSoftwareAbsDbList.add(selectedSoftwareAbsDb);

		}
		
		selectedSoftwareDb.setSoftwareSelected(selectedSoftwareAbsDbList);
		selectedSoftwareDb.setTotalCost(cost);
		softwareSelectedRepo.save(selectedSoftwareDb);
		
		if (cost > 10000) {
//			mail to DH for approval
			emailSender(selectedSoftwareDb.getDirectorEmail(), selectedSoftwareDb.getEmployeeEmail(), selectedSoftwareDb.getTicketNo());
		} else {
			ApprovalPojo managerApproval = new ApprovalPojo();
			List<ApprovalPojo> managerApprovalList = new ArrayList<>();
			ApprovalPojo dhApproval = new ApprovalPojo();
			List<ApprovalPojo> dhApprovalList = new ArrayList<>();
			
			for (SelectedSoftwareAbsDb selectSoft : selectedSoftwareDb.getSoftwareSelected()) {
				if (selectSoft.isManagerApproval()) {
					managerApproval.setTicketNo(ticketNo);
					managerApproval.setSoftware(selectSoft.getSoftware());
					managerApproval.setSoftwareVersion(selectSoft.getSoftwareVersion());
					managerApprovalList.add(managerApproval);
				} else if (selectSoft.isDhApproval()) {
					dhApproval.setTicketNo(ticketNo);
					dhApproval.setSoftware(selectSoft.getSoftware());
					dhApproval.setSoftwareVersion(selectSoft.getSoftwareVersion());
					dhApprovalList.add(managerApproval);
				}
			}
			if(managerApprovalList.size() > 0 && dhApprovalList.size() > 0) {
//				mail to manager & dh
				emailSender(selectedSoftwareDb.getManagerEmail(), selectedSoftwareDb.getEmployeeEmail(), selectedSoftwareDb.getTicketNo());
				emailSender(selectedSoftwareDb.getDirectorEmail(), selectedSoftwareDb.getEmployeeEmail(), selectedSoftwareDb.getTicketNo());
				System.out.println("manager & director email triggered");
			} else if (managerApprovalList.size() > 0) {
//				mail to manager alone
				emailSender(selectedSoftwareDb.getManagerEmail(), selectedSoftwareDb.getEmployeeEmail(), selectedSoftwareDb.getTicketNo());
				System.out.println("manager email triggered");
			} else if (dhApprovalList.size() > 0) {
//				mail to DH
				emailSender(selectedSoftwareDb.getDirectorEmail(), selectedSoftwareDb.getEmployeeEmail(), selectedSoftwareDb.getTicketNo());
				System.out.println("director email triggered");
			}
		}
		
		return "Success";
	}
	
	private void emailSender(String approverEmail, String employeeEmail, String ticketNo) {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper msgHelper = new MimeMessageHelper(message, "UTF-8");
		String htmlContent = "<h1>pls approve by click below link</h1><a href=\"https://www.w3schools.com\">Visit W3Schools.com!</a>";
		try {
			msgHelper.setTo(approverEmail);
			msgHelper.setFrom(fromEmail);
			msgHelper.setCc(employeeEmail);
			msgHelper.setPriority(1);
			msgHelper.setSubject("Software | Approval Required");
			msgHelper.setText(htmlContent, true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.getStackTrace();
		}
		javaMailSender.send(message);
	}
	
	public List<SelectedSoftwareDb> getEmployeeTicketDetails(Long employeeId) {
		List<SelectedSoftwareDb> ticketList = new ArrayList<>();
		ticketList = softwareSelectedRepo.findByEmployeeId(employeeId);
		return ticketList;
	}

	public String approveTicker(String tickerNo, String approver, String status) {
		
		List<SelectedSoftwareAbsDb> softwareAbsDb= new ArrayList<>(); 
		softwareAbsDb = selectedSoftwareAbsRepo.findByTicketNo(tickerNo);
		
		for (SelectedSoftwareAbsDb soft : softwareAbsDb) {
			if (soft.isManagerApproval() && approver.equalsIgnoreCase("Manager")) {
				soft.setApprovedBy(status);
			} else if (soft.isDhApproval() && approver.equalsIgnoreCase("Director")) {
				soft.setApprovedBy(status);
			}
		}
		selectedSoftwareAbsRepo.saveAll(softwareAbsDb);
		return "success";
	}
}
