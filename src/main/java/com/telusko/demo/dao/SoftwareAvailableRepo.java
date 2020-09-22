package com.telusko.demo.dao;

import org.springframework.data.repository.CrudRepository;

import com.telusko.demo.model.SoftwareAvailable;

public interface SoftwareAvailableRepo extends CrudRepository<SoftwareAvailable,Integer> {

}
