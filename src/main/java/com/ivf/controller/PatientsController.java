package com.ivf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivf.dto.PatientDTO;
import com.ivf.services.PatientService;

@RestController
@RequestMapping("/api/patient")
public class PatientsController {
	
	@Autowired
	private PatientService patientService;

	@PostMapping("/find-by-search-criteria")
	public ResponseEntity<List<PatientDTO>> findBySearchCriteria(@RequestBody PatientDTO request) {
		return ResponseEntity.ok(patientService.findBySaerchCriteria(request));
	}
	
	@PostMapping("/add")
    public ResponseEntity<PatientDTO> add(@RequestBody PatientDTO request) {
        return ResponseEntity.ok(patientService.add(request));
    }

    @PutMapping("/update")
    public ResponseEntity<PatientDTO> update(@RequestBody PatientDTO request) {

        return ResponseEntity.ok(patientService.update(request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody PatientDTO request) {

        patientService.delete(request);

        return ResponseEntity.noContent().build();
    }
}
