package com.ivf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivf.dto.PrintConfigurationsDTO;
import com.ivf.dto.ProcedureTypeDTO;
import com.ivf.services.LookupService;

@RestController
@RequestMapping("/api/lookup")
public class LookupController {
	
	@Autowired
	private LookupService lookupService;

	@GetMapping("/find-all-procedure-types")
	public ResponseEntity<List<ProcedureTypeDTO>> findAllProcedureTypes() {

		return ResponseEntity.ok(lookupService.findAllProcedureTypes());
	}
	
	@GetMapping("/find-print-configurations")
	public ResponseEntity<PrintConfigurationsDTO> findAllPrintConfigurations() {

		return ResponseEntity.ok(lookupService.findAllPrintConfigurations());
	}
}
