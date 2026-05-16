package com.ivf.services;

import java.util.List;

import com.ivf.dto.PatientDTO;

public interface PatientService {

	List<PatientDTO> findBySaerchCriteria(PatientDTO patientDTO);
	
	public PatientDTO add(PatientDTO patientDTO);
	
	public PatientDTO update(PatientDTO patientDTO);
	
	public void delete(PatientDTO patientDTO);

}
