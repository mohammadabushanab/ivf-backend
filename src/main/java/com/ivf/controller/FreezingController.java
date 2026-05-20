package com.ivf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivf.dto.FreezingDTO;
import com.ivf.services.FreezingService;

@RestController
@RequestMapping("/api/freezing")
public class FreezingController {

	@Autowired
	private FreezingService freezingService;
	
	@PostMapping("/find-by-search-criteria")
	public ResponseEntity<List<FreezingDTO>> findBySearchCriteria(FreezingDTO freezingDTO) {
		return ResponseEntity.ok(freezingService.findBySearchCriteria(freezingDTO));
	}

	@PostMapping("/find-total-by-type")
	public ResponseEntity<Long> findTotalByType(FreezingDTO freezingDTO) {
		return ResponseEntity.ok(freezingService.findTotalByType(freezingDTO));
	}

}
