package com.telusko.demo.dao;

import org.springframework.data.repository.CrudRepository;

import com.telusko.demo.model.SoftwareDetails;

public interface SoftwareDetailsRepo extends CrudRepository<SoftwareDetails,Integer> {

}
