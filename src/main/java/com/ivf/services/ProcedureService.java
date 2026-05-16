package com.ivf.services;

import java.util.List;

import com.ivf.dto.ProcedureDTO;

public interface ProcedureService {

	List<ProcedureDTO> findBySaerchCriteria(ProcedureDTO procedureDTO);
	
	public ProcedureDTO add(ProcedureDTO procedureDTO);
	
	public ProcedureDTO update(ProcedureDTO procedureDTO);
	
	public void delete(ProcedureDTO procedureDTO);

}
