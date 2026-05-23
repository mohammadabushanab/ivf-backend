package com.ivf.services;

import java.time.LocalDateTime;
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

		System.out.println(freezingDTO);

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT ");
		sql.append("MAX(freezing.id) AS id, ");
		sql.append("freezing.type AS type, ");
		sql.append("COALESCE(SUM(freezing.total), 0) AS total, ");
		sql.append("COALESCE(SUM(freezing.remaining), 0) AS remaining, ");
		sql.append("STRING_AGG(DISTINCT NULLIF(freezing.dewar, ''), ', ') AS dewar, ");
		sql.append("STRING_AGG(DISTINCT NULLIF(freezing.canister, ''), ', ') AS canister, ");
		sql.append("STRING_AGG(DISTINCT NULLIF(freezing.notes, ''), ', ') AS notes, ");
		sql.append("MAX(freezing.date) AS date, ");
		sql.append("patients.id AS patient_id, ");
		sql.append("patients.name AS patient_name, ");
		sql.append("patients.national_id AS national_id, ");
		sql.append("patients.phone_number AS phone_number, ");
		sql.append("patients.age AS age, ");
		sql.append("patients.husband_name AS husband_name, ");
		sql.append("patients.husband_national_id AS husband_national_id, ");
		sql.append("patients.husband_phone_number AS husband_phone_number ");
		sql.append("FROM freezing ");

		sql.append("INNER JOIN patients ");
		sql.append("ON patients.id = freezing.patient_id ");

		sql.append("WHERE 1=1 ");

		Map<String, Object> params = new HashMap<String, Object>();

		if (freezingDTO.getType() != null && !freezingDTO.getType().isEmpty()) {

			sql.append("AND freezing.type = :type ");

			params.put("type", freezingDTO.getType());
		}

		if (freezingDTO.getPatientDTO() != null) {

			if (freezingDTO.getPatientDTO().getNationalId() != null
					&& !freezingDTO.getPatientDTO().getNationalId().isEmpty()) {

				sql.append("AND patients.national_id = :nationalId ");

				params.put("nationalId", freezingDTO.getPatientDTO().getNationalId());
			}

			if (freezingDTO.getPatientDTO().getName() != null
					&& !freezingDTO.getPatientDTO().getName().isEmpty()) {

				sql.append("AND LOWER(patients.name) like LOWER(:name) ");

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

		sql.append("GROUP BY ");
		sql.append("freezing.type, ");
		sql.append("patients.id ");

		sql.append("ORDER BY MAX(freezing.date) DESC ");

		Query query = entityManager.createNativeQuery(sql.toString());

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		List<Object[]> results = query.getResultList();

		List<FreezingDTO> freezingDTOs = new ArrayList<FreezingDTO>();

		if (results != null) {

			for (Object[] row : results) {

				FreezingDTO dto = new FreezingDTO();

				dto.setId(row[0] != null ? ((Number) row[0]).longValue() : null);
				dto.setType(row[1] != null ? row[1].toString() : null);
				dto.setTotal(row[2] != null ? ((Number) row[2]).longValue() : 0L);
				dto.setRemaining(row[3] != null ? ((Number) row[3]).longValue() : 0L);
				dto.setDewar(row[4] != null ? row[4].toString() : null);
				dto.setCanister(row[5] != null ? row[5].toString() : null);
				dto.setNotes(row[6] != null ? row[6].toString() : null);

				if (row[7] != null) {
					dto.setDate((LocalDateTime) row[7]);
				}

				PatientDTO patientDTO = new PatientDTO();

				if (row[8] != null) {
					patientDTO.setId(((Number) row[8]).longValue());
				}

				patientDTO.setName(row[9] != null ? row[9].toString() : null);

				patientDTO.setNationalId(row[10] != null ? row[10].toString() : null);

				patientDTO.setPhoneNumber(row[11] != null ? row[11].toString() : null);

				patientDTO.setAge(row[12] != null ? row[12].toString() : null);

				patientDTO.setHusbandName(row[13] != null ? row[13].toString() : null);

				patientDTO.setHusbandNationalId(row[14] != null ? row[14].toString() : null);

				patientDTO.setHusbandPhoneNumber(row[15] != null ? row[15].toString() : null);

				dto.setPatientDTO(patientDTO);

				freezingDTOs.add(dto);
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
