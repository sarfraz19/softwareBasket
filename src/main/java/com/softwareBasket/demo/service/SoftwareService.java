package com.softwareBasket.demo.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	Iterable<SoftwareAvailable> soft2;
	
	public List<FinalSoftwareDetails> getSoftwareList() throws JsonProcessingException{
		
		FinalSoftwareDetails softwareDetails = new FinalSoftwareDetails();
		List<FinalSoftwareDetails> softwareDetailsList = new ArrayList<>();
		soft2 = avilableRepo.findAll();
		
		for(SoftwareAvailable swAvailable : soft2) {
			for(SoftwareDetails swDetails : swAvailable.getSoftwareDetails()) {
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
		SelectedSoftwareAbsDb selectedSoftwareAbsDb = new SelectedSoftwareAbsDb();
		List<SelectedSoftwareAbsDb> selectedSoftwareAbsDbList = new ArrayList<>();
		SoftwareAvailable softAvailable = new SoftwareAvailable();
		SoftwareDetails softDetails = new SoftwareDetails();
		
		UUID ticketNo = UUID.randomUUID();
		OffsetDateTime date = OffsetDateTime.now();
		
		selectedSoftwareDb.setDate(date);
		selectedSoftwareDb.setTicketNo(ticketNo);
		selectedSoftwareDb.setEmployeeId(selectedSoftware.getEmployeeId());
		selectedSoftwareDb.setManagerEmail(selectedSoftware.getManagerEmail());
		selectedSoftwareDb.setDirectorEmail(selectedSoftware.getDirectorEmail());
		
		int cost = 0;
		
		for (SelectedSoftwareAbs selectedSoftwareAbs : selectedSoftware.getSelectedSoftware()) {
			selectedSoftwareAbsDb.setTicketNo(ticketNo);
			selectedSoftwareAbsDb.setSoftware(selectedSoftwareAbs.getSoftwareName());
			selectedSoftwareAbsDb.setSoftwareVersion(selectedSoftwareAbs.getSoftwareVersion());
			
			softAvailable = softwareAvailableRepo.findBySoftwareName(selectedSoftwareAbs.getSoftwareName()).get(0);
			softDetails = softwareDetailsRepo.findBySoftwareIdAndVersions(softAvailable.getSoftwareId(), selectedSoftwareAbs.getSoftwareVersion()).get(0);
			
			if (softDetails.isPaid()) {
			cost = cost + Integer.valueOf(softDetails.getCost());
			}
			System.out.println(cost);
			
			selectedSoftwareAbsDb.setManagerApproval(false);
			selectedSoftwareAbsDb.setDhApproval(false);
			selectedSoftwareAbsDb.setApprovedBy("Free");
			int softCost = Integer.valueOf(softDetails.getCost());
			
			if (softCost > 50 && softCost <100 ) {	
				selectedSoftwareAbsDb.setManagerApproval(true);
				selectedSoftwareAbsDb.setApprovedBy("Waiting");
			} else if (softCost > 100) {
				selectedSoftwareAbsDb.setDhApproval(true);
				selectedSoftwareAbsDb.setApprovedBy("Waiting");
			}
			
			selectedSoftwareAbsDb.setUrl("http://github.com/reponame/"+selectedSoftwareAbs.getSoftwareName()+"/"+selectedSoftwareAbs.getSoftwareVersion());
			selectedSoftwareAbsDbList.add(selectedSoftwareAbsDb);
		}
		
		selectedSoftwareDb.setSoftwareSelected(selectedSoftwareAbsDbList);
		selectedSoftwareDb.setTotalCost(cost);
		softwareSelectedRepo.save(selectedSoftwareDb);
		
		if (cost > 10000) {
//			mail to DH for approval
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
			} else if (managerApprovalList.size() > 0) {
//				mail to manager alone
			} else if (dhApprovalList.size() > 0) {
//				mail to DH
			}
		}
		
		return "Success";
	}
	
	public List<SelectedSoftwareAbsDb> getEmployeeTicketDetails(Long employeeId) {
		List<SelectedSoftwareDb> ticketList = new ArrayList<>();
		ticketList = softwareSelectedRepo.findByEmployeeId(employeeId);
		return ticketList.get(0).getSoftwareSelected();
	}

//	public approveTicker(UUID tickerNo, String approver) {
//		selectedSoftwareAbsRepo
//	}
}
