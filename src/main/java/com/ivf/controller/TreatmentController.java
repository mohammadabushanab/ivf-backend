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

import com.ivf.dto.TreatmentDTO;
import com.ivf.services.TreatmentService;

@RestController
@RequestMapping("/api/treatment")
public class TreatmentController {

	@Autowired
	private TreatmentService treatmentService;

	@PostMapping("/find-by-search-criteria")
	public ResponseEntity<List<TreatmentDTO>> findBySearchCriteria(@RequestBody TreatmentDTO request) {
		return ResponseEntity.ok(treatmentService.findBySearchCriteria(request));
	}

	@PostMapping("/add")
	public ResponseEntity<TreatmentDTO> add(@RequestBody TreatmentDTO request) {
		return ResponseEntity.ok(treatmentService.add(request));
	}

	@PutMapping("/update")
	public ResponseEntity<TreatmentDTO> update(@RequestBody TreatmentDTO request) {

		return ResponseEntity.ok(treatmentService.update(request));
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> delete(@RequestBody TreatmentDTO request) {

		treatmentService.delete(request);

		return ResponseEntity.noContent().build();
	}
}
