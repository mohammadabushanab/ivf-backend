package com.ivf.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ivf.dto.FreezingDTO;
import com.ivf.dto.PatientDTO;
import com.ivf.entitis.FreezingEntity;
import com.ivf.entitis.PatientEntity;
import com.ivf.repositories.FreezingRepository;
import com.ivf.repositories.PatientRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
public class FreezingServiceImpl implements FreezingService {

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private FreezingRepository freezingRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<FreezingDTO> findBySearchCriteria(FreezingDTO freezingDTO) {

		StringBuilder sql = new StringBuilder("SELECT freezing.* FROM freezing ");

		sql.append("INNER JOIN patients ");
		sql.append("ON patients.id = freezing.patient_id ");

		sql.append("WHERE 1=1 ");

		Map<String, Object> params = new HashMap<String, Object>();

		if (freezingDTO.getType() != null && !freezingDTO.getType().isEmpty()) {

			sql.append("AND freezing.type = :type ");

			params.put("type", freezingDTO.getType());
		}

		if (freezingDTO.getPatientDTO() != null) {
			if (freezingDTO.getPatientDTO().getNationalId() != null && !freezingDTO.getPatientDTO().getNationalId().isEmpty()) {

				sql.append("AND patients.national_id = :nationalId ");

				params.put("nationalId", freezingDTO.getPatientDTO().getNationalId());
			}

			if (freezingDTO.getPatientDTO().getName() != null && !freezingDTO.getPatientDTO().getName().isEmpty()) {

				sql.append("AND LOWER(patients.name) like LOWER(:husbandName) ");

				params.put("name", "%" + freezingDTO.getPatientDTO().getName() + "%");
			}

			if (freezingDTO.getPatientDTO().getPhoneNumber() != null
					&& !freezingDTO.getPatientDTO().getPhoneNumber().isEmpty()) {

				sql.append("AND patients.phone_number = :phoneNumber ");

				params.put("phoneNumber", freezingDTO.getPatientDTO().getPhoneNumber());
			}

			if (freezingDTO.getPatientDTO().getHusbandNationalId() != null
					&& !freezingDTO.getPatientDTO().getHusbandNationalId().isEmpty()) {

				sql.append("AND patients.husband_national_id = :husbandNationalId ");

				params.put("husbandNationalId", freezingDTO.getPatientDTO().getHusbandNationalId());
			}

			if (freezingDTO.getPatientDTO().getHusbandName() != null
					&& !freezingDTO.getPatientDTO().getHusbandName().isEmpty()) {

				sql.append("AND LOWER(patients.husband_name) like LOWER(:husbandName) ");

				params.put("husbandName", "%" + freezingDTO.getPatientDTO().getHusbandName() + "%");
			}

			if (freezingDTO.getPatientDTO().getHusbandPhoneNumber() != null
					&& !freezingDTO.getPatientDTO().getHusbandPhoneNumber().isEmpty()) {

				sql.append("AND patients.husband_phone_number = :husbandPhoneNumber ");

				params.put("husbandPhoneNumber", freezingDTO.getPatientDTO().getHusbandPhoneNumber());
			}

		}

		Query query = entityManager.createNativeQuery(sql.toString(), FreezingEntity.class);

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		List<FreezingEntity> freezingEntities = query.getResultList();

		List<FreezingDTO> freezingDTOs = new ArrayList<FreezingDTO>();

		if (freezingEntities != null) {
			for (FreezingEntity freezingEntity : freezingEntities) {
				freezingDTOs.add(mapToDTO(freezingEntity));
			}
		}

		return freezingDTOs;
	}

	@Override
	public Long findTotalByType(FreezingDTO freezingDTO) {

	    StringBuilder sql = new StringBuilder();

	    sql.append("SELECT COALESCE(SUM(freezing.total), 0) ");
	    sql.append("FROM freezing ");

	    Map<String, Object> params = new HashMap<>();

        sql.append("WHERE freezing.type = :type ");
        params.put("type", freezingDTO.getType());

	    Query query = entityManager.createNativeQuery(sql.toString());

	    for (Map.Entry<String, Object> entry : params.entrySet()) {
	        query.setParameter(entry.getKey(), entry.getValue());
	    }

	    Object result = query.getSingleResult();
	    
	    Long total = 0L;
	    if(result != null) {
	    	 total = ((Number) result).longValue();
	    }
	    
	    return total;
	}
	
	
	public FreezingDTO add(FreezingDTO freezingDTO) {

		FreezingEntity entity = mapToEntity(freezingDTO);

		FreezingEntity saved = freezingRepository.save(entity);

		FreezingDTO savedDTO = mapToDTO(saved);

		return savedDTO;
	}

	public FreezingDTO update(FreezingDTO freezingDTO) {

		FreezingEntity entity = freezingRepository.findById(freezingDTO.getId()).orElseThrow(() -> new RuntimeException("Patient not found"));
		
		entity.setType(freezingDTO.getType());
		entity.setDate(freezingDTO.getDate());
		entity.setTotal(freezingDTO.getTotal());
		entity.setRemaining(freezingDTO.getRemaining());
		entity.setDewar(freezingDTO.getDewar());
		entity.setCanister(freezingDTO.getCanister());
		entity.setNotes(freezingDTO.getNotes());		

		if (freezingDTO.getPatientDTO() != null) {

			PatientEntity patientEntity = patientRepository.findById(freezingDTO.getPatientDTO().getId())
					.orElseThrow(() -> new RuntimeException("Patient Type not found"));

			entity.setPatientEntity(patientEntity);
		}

		FreezingEntity updated = freezingRepository.save(entity);

		FreezingDTO updatedDTO = mapToDTO(updated);

		return updatedDTO;
	}

	public void delete(FreezingDTO freezingDTO) {
		freezingRepository.deleteById(freezingDTO.getId());
	}

	private FreezingDTO mapToDTO(FreezingEntity freezingEntity) {
		if (freezingEntity == null) {
			return null;
		}

		FreezingDTO freezingDTO = new FreezingDTO();

		freezingDTO.setId(freezingEntity.getId());
		freezingDTO.setType(freezingEntity.getType());
		freezingDTO.setDate(freezingEntity.getDate());
		freezingDTO.setTotal(freezingEntity.getTotal());
		freezingDTO.setRemaining(freezingEntity.getRemaining());
		freezingDTO.setDewar(freezingEntity.getDewar());
		freezingDTO.setCanister(freezingEntity.getCanister());
		freezingDTO.setNotes(freezingEntity.getNotes());
		
		if (freezingEntity.getPatientEntity() != null) {

			PatientDTO patientDTO = new PatientDTO();

			patientDTO.setId(freezingEntity.getPatientEntity().getId());

			patientDTO.setName(freezingEntity.getPatientEntity().getName());

			patientDTO.setNationalId(freezingEntity.getPatientEntity().getNationalId());

			patientDTO.setPhoneNumber(freezingEntity.getPatientEntity().getPhoneNumber());

			patientDTO.setAge(freezingEntity.getPatientEntity().getAge());

			patientDTO.setHusbandName(freezingEntity.getPatientEntity().getHusbandName());

			patientDTO.setHusbandNationalId(freezingEntity.getPatientEntity().getHusbandNationalId());

			patientDTO.setHusbandPhoneNumber(freezingEntity.getPatientEntity().getHusbandPhoneNumber());

			patientDTO.setCreatedDate(freezingEntity.getPatientEntity().getCreatedDate());

			patientDTO.setModifiedDate(freezingEntity.getPatientEntity().getModifiedDate());

			freezingDTO.setPatientDTO(patientDTO);
		}

		return freezingDTO;
	}

	private FreezingEntity mapToEntity(FreezingDTO freezingDTO) {

		if (freezingDTO == null) {
			return null;
		}

		FreezingEntity freezingEntity = new FreezingEntity();

		freezingEntity.setType(freezingDTO.getType());
		freezingEntity.setDate(freezingDTO.getDate());
		freezingEntity.setTotal(freezingDTO.getTotal());
		freezingEntity.setRemaining(freezingDTO.getRemaining());
		freezingEntity.setDewar(freezingDTO.getDewar());
		freezingEntity.setCanister(freezingDTO.getCanister());
		freezingEntity.setNotes(freezingDTO.getNotes());

		if (freezingDTO.getPatientDTO() != null && freezingDTO.getPatientDTO().getId() != null) {

			PatientEntity patientEntity = new PatientEntity();

			patientEntity.setId(freezingDTO.getPatientDTO().getId());

			freezingEntity.setPatientEntity(patientEntity);
		}

		return freezingEntity;
	}

}
