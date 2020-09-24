package com.softwareBasket.demo.controller;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwareBasket.demo.dao.AlienRepo;
import com.softwareBasket.demo.dao.SoftwareAvailableRepo;
import com.softwareBasket.demo.dao.SoftwareDetailsRepo;
import com.softwareBasket.demo.model.Alien;
import com.softwareBasket.demo.model.FinalSoftwareDetails;
import com.softwareBasket.demo.model.SelectedSoftware;
import com.softwareBasket.demo.model.SelectedSoftwareAbsDb;
import com.softwareBasket.demo.model.SelectedSoftwareDb;
import com.softwareBasket.demo.model.SoftwareAvailable;
import com.softwareBasket.demo.model.SoftwareDetails;
import com.softwareBasket.demo.service.SoftwareService;

@RestController
public class SoftwareBasketController
{
	@Autowired
	AlienRepo repo;
	
	@Autowired
	SoftwareAvailableRepo avilableRepo;
	
	@Autowired
	SoftwareDetailsRepo detailsRepo;
	
	@Autowired
	SoftwareService softwareService;
	
	
	
//	This API is used to get the List of Software Available
	@GetMapping("/getSoftwares")
	public List<FinalSoftwareDetails> getSoftware() throws JsonProcessingException {
		
		List<FinalSoftwareDetails> softwareDetails = new ArrayList<FinalSoftwareDetails>();
		softwareDetails = softwareService.getSoftwareList();
		
		return softwareDetails;
	}
	
	@PostMapping("/chosedSoftware")
	public String chosedSoftware(@RequestBody SelectedSoftware selectedSoftware) {
		
		String val = softwareService.saveTicketRequest(selectedSoftware);
		
		return val;
	}
	
	@GetMapping(value = "/employeeTicketDetails/{employeeId}")
	public List<SelectedSoftwareAbsDb> employeeTicketDetails(@PathVariable Long employeeId) {
		List<SelectedSoftwareAbsDb> ticketList = new ArrayList<>();
		ticketList = softwareService.getEmployeeTicketDetails(employeeId);
		return ticketList;
	}
	
	@GetMapping(value = "/approval/{ticketNo}/{approver}")
	public String ticketApproval(@PathVariable UUID ticketNo, @PathVariable String approver) {
		return approver;	
	}
}
