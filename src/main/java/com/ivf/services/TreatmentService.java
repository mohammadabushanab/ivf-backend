package com.ivf.services;

import java.util.List;

import com.ivf.dto.TreatmentDTO;

public interface TreatmentService {

	List<TreatmentDTO> findBySearchCriteria(TreatmentDTO treatmentDTO);
	
	public TreatmentDTO add(TreatmentDTO treatmentDTO);
	
	public TreatmentDTO update(TreatmentDTO treatmentDTO);
	
	public void delete(TreatmentDTO treatmentDTO);

}
