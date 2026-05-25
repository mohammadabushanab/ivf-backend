package com.ivf.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.ivf.dto.OPUDTO;
import com.ivf.dto.PatientDTO;
import com.ivf.entitis.OPUEntity;
import com.ivf.entitis.PatientEntity;
import com.ivf.repositories.OPURepository;
import com.ivf.repositories.PatientRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
public class OPUServiceImpl implements OPUService {

	@Autowired
	private OPURepository opuRepository;

	@Autowired
	private PatientRepository patientRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Override
	public List<OPUDTO> findBySearchCriteria(OPUDTO opuDTO) {

		StringBuilder sql = new StringBuilder("SELECT opus.* FROM opus ");

		sql.append("INNER JOIN patients ");
		sql.append("ON patients.id = opus.patient_id ");

		sql.append("WHERE 1=1 ");

		Map<String, Object> params = new HashMap<String, Object>();

		if (opuDTO.getId() != null) {
			sql.append("AND opus.id = :id ");

			params.put("id", opuDTO.getId());
		}
		
		if (opuDTO.getStatus() != null && !opuDTO.getStatus().isEmpty()) {
			sql.append("AND opus.status != :status ");

			params.put("status", opuDTO.getStatus());
		}

		Query query = entityManager.createNativeQuery(sql.toString(), OPUEntity.class);

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		List<OPUEntity> opuEntities = query.getResultList();

		List<OPUDTO> opuDTOs = new ArrayList<OPUDTO>();

		if (opuEntities != null) {
			for (OPUEntity opuEntity : opuEntities) {
				opuDTOs.add(mapToDTO(opuEntity));
			}
		}

		return opuDTOs;
	}

	public OPUDTO add(OPUDTO opuDTO) {

		OPUEntity entity = mapToEntity(opuDTO);

		OPUEntity saved = opuRepository.save(entity);

		OPUDTO savedDTO = mapToDTO(saved);

		messagingTemplate.convertAndSend("/topic/opus", savedDTO);

		return savedDTO;
	}

	public OPUDTO update(OPUDTO opuDTO) {

		OPUEntity entity = opuRepository.findById(opuDTO.getId())
				.orElseThrow(() -> new RuntimeException("Patient not found"));
		
		entity.setValues(opuDTO.getValues());

		entity.setStatus(opuDTO.getStatus());
		
		if (opuDTO.getPatientDTO() != null) {

			PatientEntity patientEntity = patientRepository.findById(opuDTO.getPatientDTO().getId())
					.orElseThrow(() -> new RuntimeException("Patient Type not found"));

			entity.setPatientEntity(patientEntity);
		}

		OPUEntity updated = opuRepository.save(entity);

		OPUDTO updatedDTO = mapToDTO(updated);

		messagingTemplate.convertAndSend("/topic/opus", updatedDTO);

		return updatedDTO;
	}

	public void delete(OPUDTO opuDTO) {
		opuRepository.deleteById(opuDTO.getId());
		
		OPUDTO deletedDTO = new OPUDTO();

	    deletedDTO.setId(opuDTO.getId());

	    deletedDTO.setDeleted(true);

	    messagingTemplate.convertAndSend("/topic/opus", deletedDTO);
	}

	private OPUDTO mapToDTO(OPUEntity opuEntity) {

		if (opuEntity == null) {
			return null;
		}

		OPUDTO opuDTO = new OPUDTO();

		opuDTO.setId(opuEntity.getId());
		opuDTO.setValues(opuEntity.getValues());
		opuDTO.setCreatedDate(opuEntity.getCreatedDate());
		opuDTO.setModifiedDate(opuEntity.getModifiedDate());
		opuDTO.setStatus(opuEntity.getStatus());

		if (opuEntity.getPatientEntity() != null) {

			PatientDTO patientDTO = new PatientDTO();

			patientDTO.setId(opuEntity.getPatientEntity().getId());

			patientDTO.setName(opuEntity.getPatientEntity().getName());

			patientDTO.setNationalId(opuEntity.getPatientEntity().getNationalId());

			patientDTO.setPhoneNumber(opuEntity.getPatientEntity().getPhoneNumber());

			patientDTO.setAge(opuEntity.getPatientEntity().getAge());

			patientDTO.setHusbandName(opuEntity.getPatientEntity().getHusbandName());

			patientDTO.setHusbandNationalId(opuEntity.getPatientEntity().getHusbandNationalId());

			patientDTO.setHusbandPhoneNumber(opuEntity.getPatientEntity().getHusbandPhoneNumber());

			patientDTO.setCreatedDate(opuEntity.getPatientEntity().getCreatedDate());

			patientDTO.setModifiedDate(opuEntity.getPatientEntity().getModifiedDate());

			opuDTO.setPatientDTO(patientDTO);
		}

		return opuDTO;
	}

	private OPUEntity mapToEntity(OPUDTO opuDTO) {

		if (opuDTO == null) {
			return null;
		}

		OPUEntity opuEntity = new OPUEntity();

		opuEntity.setValues(opuDTO.getValues());
		
		opuEntity.setStatus(opuDTO.getStatus());

		if (opuDTO.getPatientDTO() != null && opuDTO.getPatientDTO().getId() != null) {

			PatientEntity patientEntity = new PatientEntity();
			
			patientEntity = patientRepository.findById(opuDTO.getPatientDTO().getId()).orElse(null);

			opuEntity.setPatientEntity(patientEntity);
		}

		return opuEntity;
	}

}
