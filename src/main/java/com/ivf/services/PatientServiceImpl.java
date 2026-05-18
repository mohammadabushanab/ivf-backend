package com.ivf.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ivf.dto.PatientDTO;
import com.ivf.entitis.PatientEntity;
import com.ivf.repositories.PatientRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientRepository patientRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<PatientDTO> findBySaerchCriteria(PatientDTO patientDTO) {

		StringBuilder sql = new StringBuilder("SELECT * FROM patients WHERE 1=1 ");

		Map<String, Object> params = new HashMap<String, Object>();

		if (patientDTO.getNationalId() != null && !patientDTO.getNationalId().isEmpty()) {

			sql.append("AND national_id = :nationalId ");

			params.put("nationalId", patientDTO.getNationalId());
		}

		if (patientDTO.getName() != null && !patientDTO.getName().isEmpty()) {

			sql.append("AND name = :name ");

			params.put("name", patientDTO.getName());
		}

		if (patientDTO.getPhoneNumber() != null && !patientDTO.getPhoneNumber().isEmpty()) {

			sql.append("AND phone_number = :phoneNumber ");

			params.put("phoneNumber", patientDTO.getPhoneNumber());
		}

		if (patientDTO.getHusbandNationalId() != null && !patientDTO.getHusbandNationalId().isEmpty()) {

			sql.append("AND husband_national_id = :husbandNationalId ");

			params.put("husbandNationalId", patientDTO.getHusbandNationalId());
		}

		if (patientDTO.getHusbandName() != null && !patientDTO.getHusbandName().isEmpty()) {

			sql.append("AND husband_name = :husbandName ");

			params.put("husbandName", patientDTO.getHusbandName());
		}

		if (patientDTO.getHusbandPhoneNumber() != null && !patientDTO.getHusbandPhoneNumber().isEmpty()) {

			sql.append("AND husband_phone_number = :husbandPhoneNumber ");

			params.put("husbandPhoneNumber", patientDTO.getHusbandPhoneNumber());
		}
		
		if(patientDTO.getFromDate() != null  && patientDTO.getToDate() != null) {
			sql.append("AND created_date >= :fromDate AND created_date <= :toDate ");

			params.put("fromDate", patientDTO.getFromDate().atStartOfDay());
			params.put("toDate", patientDTO.getToDate().atTime(23, 59, 59));
		}		

		Query query = entityManager.createNativeQuery(sql.toString(), PatientEntity.class);

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		List<PatientEntity> patientEntities = query.getResultList();
		
		List<PatientDTO> patientDTOs = new ArrayList<PatientDTO>();
		
		if(patientEntities != null) {
			for(PatientEntity patientEntity : patientEntities) {
				patientDTOs.add(mapToDTO(patientEntity));
			}
		}


		return patientDTOs;
	}
	
	public PatientDTO add(PatientDTO patientDTO) {

	    PatientEntity entity = mapToEntity(patientDTO);

	    PatientEntity saved = patientRepository.save(entity);

	    return mapToDTO(saved);
	}
	
	public PatientDTO update(PatientDTO patientDTO) {

	    PatientEntity entity = patientRepository.findById(patientDTO.getId()).orElseThrow(() -> new RuntimeException("Patient not found"));
	    
	    if (patientDTO.getNationalId() != null && !patientDTO.getNationalId().isEmpty())
	        entity.setNationalId(patientDTO.getNationalId());

	    if (patientDTO.getName() != null && !patientDTO.getName().isEmpty())
	        entity.setName(patientDTO.getName());
	    
	    if (patientDTO.getPhoneNumber() != null && !patientDTO.getPhoneNumber().isEmpty())
	        entity.setPhoneNumber(patientDTO.getPhoneNumber());

	    if (patientDTO.getAge() != null && !patientDTO.getAge().isEmpty())
	        entity.setAge(patientDTO.getAge());

	    if (patientDTO.getHusbandName() != null && !patientDTO.getHusbandName().isEmpty())
	        entity.setHusbandName(patientDTO.getHusbandName());

	    if (patientDTO.getHusbandNationalId() != null && !patientDTO.getHusbandNationalId().isEmpty())
	        entity.setHusbandNationalId(patientDTO.getHusbandNationalId());

	    if (patientDTO.getHusbandPhoneNumber() != null && !patientDTO.getHusbandPhoneNumber().isEmpty())
	        entity.setHusbandPhoneNumber(patientDTO.getHusbandPhoneNumber());

	    PatientEntity updated = patientRepository.save(entity);

	    return mapToDTO(updated);
	}
	
	public void delete(PatientDTO patientDTO) {
	    patientRepository.deleteById(patientDTO.getId());
	}

	private PatientDTO mapToDTO(PatientEntity patientEntity) {
		
		if (patientEntity == null) {
			return null;
		}

		PatientDTO patientDTO = new PatientDTO();

		patientDTO.setId(patientEntity.getId());
		patientDTO.setNationalId(patientEntity.getNationalId());
		patientDTO.setName(patientEntity.getName());		
		patientDTO.setPhoneNumber(patientEntity.getPhoneNumber());
		patientDTO.setAge(patientEntity.getAge());
		patientDTO.setHusbandName(patientEntity.getHusbandName());
		patientDTO.setHusbandNationalId(patientEntity.getHusbandNationalId());
		patientDTO.setHusbandPhoneNumber(patientEntity.getHusbandPhoneNumber());
		patientDTO.setCreatedDate(patientEntity.getCreatedDate());
		patientDTO.setModifiedDate(patientEntity.getModifiedDate());

		return patientDTO;
	}
	
	private PatientEntity mapToEntity(PatientDTO patientDTO) {
		
		if (patientDTO == null) {
			return null;
		}

	    PatientEntity patientEntity = new PatientEntity();

	    patientEntity.setNationalId(patientDTO.getNationalId());
	    patientEntity.setName(patientDTO.getName());	   
	    patientEntity.setPhoneNumber(patientDTO.getPhoneNumber());
	    patientEntity.setAge(patientDTO.getAge());
	    patientEntity.setHusbandName(patientDTO.getHusbandName());
	    patientEntity.setHusbandNationalId(patientDTO.getHusbandNationalId());
	    patientEntity.setHusbandPhoneNumber(patientDTO.getHusbandPhoneNumber());
	    patientEntity.setCreatedDate(patientDTO.getCreatedDate());
	    patientEntity.setModifiedDate(patientDTO.getModifiedDate());

	    return patientEntity;
	}

}
