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

import com.ivf.dto.OPUDTO;
import com.ivf.services.OPUService;

@RestController
@RequestMapping("/api/opu")
public class OPUController {

	@Autowired
	private OPUService opuService;

	@PostMapping("/find-by-search-criteria")
	public ResponseEntity<List<OPUDTO>> findBySearchCriteria(@RequestBody OPUDTO request) {
		return ResponseEntity.ok(opuService.findBySearchCriteria(request));
	}

	@PostMapping("/add")
	public ResponseEntity<OPUDTO> add(@RequestBody OPUDTO request) {
		return ResponseEntity.ok(opuService.add(request));
	}

	@PutMapping("/update")
	public ResponseEntity<OPUDTO> update(@RequestBody OPUDTO request) {

		return ResponseEntity.ok(opuService.update(request));
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> delete(@RequestBody OPUDTO request) {

		opuService.delete(request);

		return ResponseEntity.noContent().build();
	}
}
