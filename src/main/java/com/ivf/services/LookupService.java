package com.ivf.services;

import java.util.List;

import com.ivf.dto.PrintConfigurationsDTO;
import com.ivf.dto.ProcedureTypeDTO;

public interface LookupService {

	List<ProcedureTypeDTO> findAllProcedureTypes();
	
	PrintConfigurationsDTO findAllPrintConfigurations();

}
