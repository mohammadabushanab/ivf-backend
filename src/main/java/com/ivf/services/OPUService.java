package com.ivf.services;

import java.util.List;

import com.ivf.dto.OPUDTO;

public interface OPUService {

	List<OPUDTO> findBySearchCriteria(OPUDTO opuDTO);
	
	public OPUDTO add(OPUDTO opuDTO);
	
	public OPUDTO update(OPUDTO opuDTO);
	
	public void delete(OPUDTO opuDTO);

}
