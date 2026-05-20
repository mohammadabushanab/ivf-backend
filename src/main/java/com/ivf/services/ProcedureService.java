package com.ivf.services;

import java.util.List;

import com.ivf.dto.ProcedureCountDTO;
import com.ivf.dto.ProcedureDTO;

public interface ProcedureService {

	List<ProcedureDTO> findBySearchCriteria(ProcedureDTO procedureDTO);
	
	public ProcedureDTO add(ProcedureDTO procedureDTO);
	
	public ProcedureDTO update(ProcedureDTO procedureDTO);
	
	public void delete(ProcedureDTO procedureDTO);
	
	public List<ProcedureCountDTO> findProceduresCountByType();
	
	public Long findTotal();

}
