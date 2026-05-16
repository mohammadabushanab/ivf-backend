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

import com.ivf.dto.ProcedureDTO;
import com.ivf.services.ProcedureService;

@RestController
@RequestMapping("/api/procedure")
public class ProcedureController {
	
	@Autowired
	private ProcedureService procedureService;

	@PostMapping("/find-by-search-criteria")
	public ResponseEntity<List<ProcedureDTO>> findBySearchCriteria(@RequestBody ProcedureDTO request) {
		return ResponseEntity.ok(procedureService.findBySaerchCriteria(request));
	}
	
	@PostMapping("/add")
    public ResponseEntity<ProcedureDTO> add(@RequestBody ProcedureDTO request) {
        return ResponseEntity.ok(procedureService.add(request));
    }

    @PutMapping("/update")
    public ResponseEntity<ProcedureDTO> update(@RequestBody ProcedureDTO request) {

        return ResponseEntity.ok(procedureService.update(request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody ProcedureDTO request) {

        procedureService.delete(request);

        return ResponseEntity.noContent().build();
    }
}
