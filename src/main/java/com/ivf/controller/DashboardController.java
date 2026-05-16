package com.ivf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivf.dto.DashboardResponseDTO;
import com.ivf.services.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;

	@GetMapping("/find-all")
	public ResponseEntity<DashboardResponseDTO> getDashboardData() {
		System.out.println("getDashboardData");
		return ResponseEntity.ok(dashboardService.getDashboardData());
	}

}
