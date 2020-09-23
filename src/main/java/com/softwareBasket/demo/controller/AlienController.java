package com.softwareBasket.demo.controller;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwareBasket.demo.dao.AlienRepo;
import com.softwareBasket.demo.dao.SoftwareAvailableRepo;
import com.softwareBasket.demo.dao.SoftwareDetailsRepo;
import com.softwareBasket.demo.model.Alien;
import com.softwareBasket.demo.model.FinalSoftwareDetails;
import com.softwareBasket.demo.model.SelectedSoftware;
import com.softwareBasket.demo.model.SoftwareAvailable;
import com.softwareBasket.demo.model.SoftwareDetails;
import com.softwareBasket.demo.service.SoftwareService;

@RestController
public class AlienController
{
	@Autowired
	AlienRepo repo;
	
	@Autowired
	SoftwareAvailableRepo avilableRepo;
	
	@Autowired
	SoftwareDetailsRepo detailsRepo;
	
	@Autowired
	SoftwareService softwareService;
	
	List<FinalSoftwareDetails> softwareDetails = new ArrayList<FinalSoftwareDetails>();
	
	SoftwareAvailable soft = new SoftwareAvailable();
	Iterable<SoftwareAvailable> soft2;
	SoftwareDetails softdet = new SoftwareDetails();
	SoftwareDetails softdet2 = new SoftwareDetails();

	@RequestMapping("/")
	public String home()
	{
		return "home.jsp";
	}
	@RequestMapping("/addAlien")
	public String addAlien(Alien alien)
	{
		repo.save(alien);
		return "home.jsp";
	}
	
	@RequestMapping("/test")
	public String test() {
		soft.setSoftwareId(1);
		soft.setSoftwareName("abc");
		avilableRepo.save(soft);
		
		softdet.setSoftwareId(1);
		softdet.setVersions("1");
		softdet.setPaid(false);
		softdet.setCost("0");
		detailsRepo.save(softdet);
		
		softdet2.setSoftwareId(1);
		softdet2.setVersions("2");
		softdet2.setPaid(true);
		softdet2.setCost("20");
		detailsRepo.save(softdet2);
		
		return "saved";
	}
	
//	This API is used to get the List of Software Available
	@GetMapping("/getSoftwares")
	public List<FinalSoftwareDetails> getSoftware() throws JsonProcessingException {
		
		softwareDetails = softwareService.getSoftwareList();
		
		return softwareDetails;
	}
	
	@PostMapping("/chosedSoftware")
	public String chosedSoftware(@RequestBody SelectedSoftware selectedSoftware) {
		
		String val = softwareService.saveTicketRequest(selectedSoftware);
		
		return val;
	}
	
}
