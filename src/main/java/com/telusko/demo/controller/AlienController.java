package com.telusko.demo.controller;

import org.springframework.stereotype.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telusko.demo.dao.AlienRepo;
import com.telusko.demo.dao.SoftwareAvailableRepo;
import com.telusko.demo.dao.SoftwareDetailsRepo;
import com.telusko.demo.model.Alien;
import com.telusko.demo.model.SelectedSoftware;
import com.telusko.demo.model.SoftwareAvailable;
import com.telusko.demo.model.SoftwareDetails;

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
	private ObjectMapper objectMapper;
	
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
	
	@GetMapping("/getSoftwares")
	public Iterable<SoftwareAvailable> getTest() throws JsonProcessingException {
		 soft2 = avilableRepo.findAll();
		 System.out.println(soft2.toString());
		 String JsonOutput = objectMapper.writeValueAsString(soft2);
		 System.out.println(JsonOutput);
		return soft2;
	}
	
	@PostMapping("/chosedSoftware")
	public String chosedSoftware(@RequestBody SelectedSoftware selected) {
		
		System.out.println(selected);
		
		return null;
	}
	
}
