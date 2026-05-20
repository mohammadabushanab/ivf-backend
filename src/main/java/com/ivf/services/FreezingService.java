package com.ivf.services;

import java.util.List;

import com.ivf.dto.FreezingDTO;

public interface FreezingService {
	FreezingDTO add(FreezingDTO freezingDTO);

	FreezingDTO update(FreezingDTO freezingDTO);

	void delete(FreezingDTO freezingDTO);

	List<FreezingDTO> findBySearchCriteria(FreezingDTO freezingDTO);
	
	Long findTotalByType(FreezingDTO freezingDTO);
}
