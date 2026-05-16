package com.ivf.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ivf.dto.PrintConfigurationsDTO;
import com.ivf.dto.ProcedureTypeDTO;
import com.ivf.entitis.PrintConfigurationsEntity;
import com.ivf.entitis.ProcedureTypeEntity;
import com.ivf.repositories.PrintConfigurationsRepository;
import com.ivf.repositories.ProcedureTypeRepository;

@Service
public class LookupServiceImpl implements LookupService {

	@Autowired
	private ProcedureTypeRepository procedureLookupRepository;
	
	@Autowired
	private PrintConfigurationsRepository printConfigurationsRepository;

	@Override
	public List<ProcedureTypeDTO> findAllProcedureTypes(){
		
		List<ProcedureTypeEntity> procedureTypeEntityEntities =procedureLookupRepository.findAll();
		
		List<ProcedureTypeDTO> ProcedureTypeDTODTOs = new ArrayList<ProcedureTypeDTO>();
		
		if(procedureTypeEntityEntities != null) {
			for(ProcedureTypeEntity procedureLookupEntity : procedureTypeEntityEntities) {
				ProcedureTypeDTODTOs.add(mapProcedureTypeEntityToDTO(procedureLookupEntity));
			}
		}


		return ProcedureTypeDTODTOs;
	}
	
	@Override
	public PrintConfigurationsDTO findAllPrintConfigurations(){
		
		List<PrintConfigurationsEntity> printConfigurationsEntities =printConfigurationsRepository.findAll();
		
		PrintConfigurationsDTO printConfigurationsDTO = new PrintConfigurationsDTO();
		
		if(printConfigurationsEntities != null) {
			printConfigurationsDTO = mapPrintConfigurationEntityToDTO(printConfigurationsEntities.get(0));
		}


		return printConfigurationsDTO;
	}
	
	private ProcedureTypeDTO mapProcedureTypeEntityToDTO(ProcedureTypeEntity procedureTypeEntity) {

		ProcedureTypeDTO procedureTypeDTO = new ProcedureTypeDTO();

		procedureTypeDTO.setId(procedureTypeEntity.getId());
		procedureTypeDTO.setName(procedureTypeEntity.getName());
		procedureTypeDTO.setWorksheetTemplate(procedureTypeEntity.getWorksheetTemplate());
		procedureTypeDTO.setPrice(procedureTypeEntity.getPrice());

		return procedureTypeDTO;
	}
	
	private PrintConfigurationsDTO mapPrintConfigurationEntityToDTO(PrintConfigurationsEntity printConfigurationsEntity) {

		PrintConfigurationsDTO printConfigurationsDTO = new PrintConfigurationsDTO();

		printConfigurationsDTO.setId(printConfigurationsEntity.getId());
		printConfigurationsDTO.setHeader(printConfigurationsEntity.getHeader());

		return printConfigurationsDTO;
	}
}
