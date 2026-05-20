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
import com.ivf.dto.TreatmentDTO;
import com.ivf.entitis.PatientEntity;
import com.ivf.entitis.TreatmentEntity;
import com.ivf.repositories.PatientRepository;
import com.ivf.repositories.TreatmentRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
public class TreatmentServiceImpl implements TreatmentService {

	@Autowired
	private TreatmentRepository treatmentRepository;

	@Autowired
	private PatientRepository patientRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Override
	public List<TreatmentDTO> findBySearchCriteria(TreatmentDTO treatmentDTO) {

		StringBuilder sql = new StringBuilder("SELECT treatments.* FROM treatments ");

		sql.append("INNER JOIN patients ");
		sql.append("ON patients.id = treatments.patient_id ");

		sql.append("WHERE 1=1 ");

		Map<String, Object> params = new HashMap<String, Object>();

		if (treatmentDTO.getId() != null) {
			sql.append("AND treatment.id = :id ");

			params.put("id", treatmentDTO.getId());
		}

		Query query = entityManager.createNativeQuery(sql.toString(), TreatmentEntity.class);

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		List<TreatmentEntity> treatmentEntities = query.getResultList();

		List<TreatmentDTO> treatmentDTOs = new ArrayList<TreatmentDTO>();

		if (treatmentEntities != null) {
			for (TreatmentEntity treatmentEntity : treatmentEntities) {
				treatmentDTOs.add(mapToDTO(treatmentEntity));
			}
		}

		return treatmentDTOs;
	}

	public TreatmentDTO add(TreatmentDTO treatmentDTO) {

		TreatmentEntity entity = mapToEntity(treatmentDTO);

		TreatmentEntity saved = treatmentRepository.save(entity);

		TreatmentDTO savedDTO = mapToDTO(saved);

		messagingTemplate.convertAndSend("/topic/treatments", savedDTO);

		return savedDTO;
	}

	public TreatmentDTO update(TreatmentDTO treatmentDTO) {

		TreatmentEntity entity = treatmentRepository.findById(treatmentDTO.getId())
				.orElseThrow(() -> new RuntimeException("Patient not found"));

		entity.setType(treatmentDTO.getType());
		entity.setValues(treatmentDTO.getValues());

		if (treatmentDTO.getPatientDTO() != null) {

			PatientEntity patientEntity = patientRepository.findById(treatmentDTO.getPatientDTO().getId())
					.orElseThrow(() -> new RuntimeException("Patient Type not found"));

			entity.setPatientEntity(patientEntity);
		}

		TreatmentEntity updated = treatmentRepository.save(entity);

		TreatmentDTO updatedDTO = mapToDTO(updated);

		messagingTemplate.convertAndSend("/topic/treatments", updatedDTO);

		return updatedDTO;
	}

	public void delete(TreatmentDTO treatmentDTO) {
		treatmentRepository.deleteById(treatmentDTO.getId());
		
		TreatmentDTO deletedDTO = new TreatmentDTO();

	    deletedDTO.setId(treatmentDTO.getId());

	    deletedDTO.setDeleted(true);

	    messagingTemplate.convertAndSend("/topic/treatmentss", deletedDTO);
	}

	private TreatmentDTO mapToDTO(TreatmentEntity treatmentEntity) {

		if (treatmentEntity == null) {
			return null;
		}

		TreatmentDTO treatmentDTO = new TreatmentDTO();

		treatmentDTO.setId(treatmentEntity.getId());
		treatmentDTO.setType(treatmentEntity.getType());
		treatmentDTO.setValues(treatmentEntity.getValues());
		treatmentDTO.setCreatedDate(treatmentEntity.getCreatedDate());
		treatmentDTO.setModifiedDate(treatmentEntity.getModifiedDate());

		if (treatmentEntity.getPatientEntity() != null) {

			PatientDTO patientDTO = new PatientDTO();

			patientDTO.setId(treatmentEntity.getPatientEntity().getId());

			patientDTO.setName(treatmentEntity.getPatientEntity().getName());

			patientDTO.setNationalId(treatmentEntity.getPatientEntity().getNationalId());

			patientDTO.setPhoneNumber(treatmentEntity.getPatientEntity().getPhoneNumber());

			patientDTO.setAge(treatmentEntity.getPatientEntity().getAge());

			patientDTO.setHusbandName(treatmentEntity.getPatientEntity().getHusbandName());

			patientDTO.setHusbandNationalId(treatmentEntity.getPatientEntity().getHusbandNationalId());

			patientDTO.setHusbandPhoneNumber(treatmentEntity.getPatientEntity().getHusbandPhoneNumber());

			patientDTO.setCreatedDate(treatmentEntity.getPatientEntity().getCreatedDate());

			patientDTO.setModifiedDate(treatmentEntity.getPatientEntity().getModifiedDate());

			treatmentDTO.setPatientDTO(patientDTO);
		}

		return treatmentDTO;
	}

	private TreatmentEntity mapToEntity(TreatmentDTO treatmentDTO) {

		if (treatmentDTO == null) {
			return null;
		}

		TreatmentEntity treatmentEntity = new TreatmentEntity();

		treatmentEntity.setType(treatmentDTO.getType());
		treatmentEntity.setValues(treatmentDTO.getValues());

		if (treatmentDTO.getPatientDTO() != null && treatmentDTO.getPatientDTO().getId() != null) {

			PatientEntity patientEntity = new PatientEntity();

			patientEntity.setId(treatmentDTO.getPatientDTO().getId());

			treatmentEntity.setPatientEntity(patientEntity);
		}

		return treatmentEntity;
	}

}
