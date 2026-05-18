package com.ivf.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ivf.dto.PatientDTO;
import com.ivf.dto.ProcedureDTO;
import com.ivf.dto.ProcedureTypeDTO;
import com.ivf.dto.UserDTO;
import com.ivf.entitis.PatientEntity;
import com.ivf.entitis.ProcedureEntity;
import com.ivf.entitis.ProcedureTypeEntity;
import com.ivf.entitis.UserEntity;
import com.ivf.repositories.PatientRepository;
import com.ivf.repositories.ProcedureRepository;
import com.ivf.repositories.ProcedureTypeRepository;
import com.ivf.repositories.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
public class ProcedureServiceImpl implements ProcedureService {

	@Autowired
	private ProcedureRepository procedureRepository;
	
	@Autowired
	private ProcedureTypeRepository procedureTypeRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private UserRepository userRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<ProcedureDTO> findBySaerchCriteria(ProcedureDTO procedureDTO) {

		StringBuilder sql = new StringBuilder("SELECT procedures.* FROM procedures ");

		sql.append("INNER JOIN procedure_types ");
		sql.append("ON procedure_types.id = procedures.procedure_type_id ");

		sql.append("INNER JOIN patients ");
		sql.append("ON patients.id = procedures.patient_id ");

		sql.append("LEFT JOIN users physician ");
		sql.append("ON physician.id = procedures.physician_id ");
		
		sql.append("LEFT JOIN users embryologist ");
		sql.append("ON embryologist.id = procedures.embryologist_id ");		

		sql.append("WHERE 1=1 ");

		Map<String, Object> params = new HashMap<String, Object>();
		
		if (procedureDTO.getIsPaid()) {
			sql.append("AND procedures.payment_status = 'Paid' ");
		}
		
		if (procedureDTO.getIsReport()) {
			sql.append("AND procedures.values != '{}' ");
		}		

		if (procedureDTO.getId() != null) {
			sql.append("AND procedures.id = :id ");

			params.put("id", procedureDTO.getId());
		}
		else {
			if(procedureDTO.getFromDate() != null  && procedureDTO.getToDate() != null) {
				
				if(procedureDTO.getDateSearchType() != null && !procedureDTO.getDateSearchType().isEmpty() ) {
					if(procedureDTO.getDateSearchType().equals("scheduledDate") ) {
						sql.append("AND procedures.scheduled_date >= :fromDate AND procedures.scheduled_date <= :toDate ");
					}
					else {
						sql.append("AND procedures.created_date >= :fromDate AND procedures.created_date <= :toDate ");
					}
				}
								

				params.put("fromDate", procedureDTO.getFromDate().atStartOfDay());
				params.put("toDate", procedureDTO.getToDate().atTime(23, 59, 59));
			}
			if (procedureDTO.getPatientDTO() != null) {
				if (procedureDTO.getPatientDTO().getNationalId() != null && !procedureDTO.getPatientDTO().getNationalId().isEmpty()) {

					sql.append("AND patients.national_id = :nationalId ");

					params.put("nationalId", procedureDTO.getPatientDTO().getNationalId());
				}

				if (procedureDTO.getPatientDTO().getName() != null && !procedureDTO.getPatientDTO().getName().isEmpty()) {

					sql.append("AND patients.name = :name ");

					params.put("name", procedureDTO.getPatientDTO().getName());
				}

				if (procedureDTO.getPatientDTO().getPhoneNumber() != null && !procedureDTO.getPatientDTO().getPhoneNumber().isEmpty()) {

					sql.append("AND patients.phone_number = :phoneNumber ");

					params.put("phoneNumber", procedureDTO.getPatientDTO().getPhoneNumber());
				}

				if (procedureDTO.getPatientDTO().getHusbandNationalId() != null && !procedureDTO.getPatientDTO().getHusbandNationalId().isEmpty()) {

					sql.append("AND patients.husband_national_id = :husbandNationalId ");

					params.put("husbandNationalId", procedureDTO.getPatientDTO().getHusbandNationalId());
				}

				if (procedureDTO.getPatientDTO().getHusbandName() != null && !procedureDTO.getPatientDTO().getHusbandName().isEmpty()) {

					sql.append("AND patients.husband_name = :husbandName ");

					params.put("husbandName", procedureDTO.getPatientDTO().getHusbandName());
				}

				if (procedureDTO.getPatientDTO().getHusbandPhoneNumber() != null && !procedureDTO.getPatientDTO().getHusbandPhoneNumber().isEmpty()) {

					sql.append("AND patients.husband_phone_number = :husbandPhoneNumber ");

					params.put("husbandPhoneNumber", procedureDTO.getPatientDTO().getHusbandPhoneNumber());
				}

			}
			
			if (procedureDTO.getProcedureTypeDTO() != null) {
				if (procedureDTO.getProcedureTypeDTO().getName() != null && !procedureDTO.getProcedureTypeDTO().getName().isEmpty()) {

					sql.append("AND procedure_types.name = :procedureTypesName ");

					params.put("procedureTypesName", procedureDTO.getProcedureTypeDTO().getName());
				}
			}

		}
		
		Query query = entityManager.createNativeQuery(sql.toString(), ProcedureEntity.class);

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		List<ProcedureEntity> procedureEntities = query.getResultList();

		List<ProcedureDTO> procedureDTOs = new ArrayList<ProcedureDTO>();

		if (procedureEntities != null) {
			for (ProcedureEntity procedureEntity : procedureEntities) {
				procedureDTOs.add(mapToDTO(procedureEntity));
			}
		}

		return procedureDTOs;
	}

	public ProcedureDTO add(ProcedureDTO procedureDTO) {

		ProcedureEntity entity = mapToEntity(procedureDTO);

		ProcedureEntity saved = procedureRepository.save(entity);

		return mapToDTO(saved);
	}

	public ProcedureDTO update(ProcedureDTO procedureDTO) {

		ProcedureEntity entity = procedureRepository.findById(procedureDTO.getId()).orElseThrow(() -> new RuntimeException("Patient not found"));

		entity.setValues(procedureDTO.getValues());		
		entity.setPaymentStatus(procedureDTO.getPaymentStatus());
		entity.setScheduledDate(procedureDTO.getScheduledDate());
		entity.setNotes(procedureDTO.getNotes());

		if (procedureDTO.getProcedureTypeDTO() != null) {

			ProcedureTypeEntity procedureTypeEntity = procedureTypeRepository.findById(procedureDTO.getProcedureTypeDTO().getId()).orElseThrow(() -> new RuntimeException("Procedure Type not found"));

			entity.setProcedureTypeEntity(procedureTypeEntity);
		}

		if (procedureDTO.getPatientDTO() != null) {

			PatientEntity patientEntity = patientRepository.findById(procedureDTO.getPatientDTO().getId()).orElseThrow(() -> new RuntimeException("Patient Type not found"));

			entity.setPatientEntity(patientEntity);
		}

		if (procedureDTO.getPhysicianDTO() != null) {

			UserEntity physicianEntity = userRepository.findById(procedureDTO.getPhysicianDTO().getId()).orElseThrow(() -> new RuntimeException("Patient Type not found"));

			entity.setPhysicianEntity(physicianEntity);
		}
		
		if (procedureDTO.getEmbryologistDTO() != null) {

			UserEntity embryologistEntity = userRepository.findById(procedureDTO.getEmbryologistDTO().getId()).orElseThrow(() -> new RuntimeException("Patient Type not found"));

			entity.setEmbryologistEntity(embryologistEntity);
		}		
		
		ProcedureEntity updated = procedureRepository.save(entity);

		return mapToDTO(updated);
	}

	public void delete(ProcedureDTO procedureDTO) {
		procedureRepository.deleteById(procedureDTO.getId());
	}

	private ProcedureDTO mapToDTO(ProcedureEntity procedureEntity) {
		
		if (procedureEntity == null) {
			return null;
		}

		ProcedureDTO procedureDTO = new ProcedureDTO();

		procedureDTO.setId(procedureEntity.getId());
		procedureDTO.setValues(procedureEntity.getValues());
		procedureDTO.setPaymentStatus(procedureEntity.getPaymentStatus());
		procedureDTO.setCreatedDate(procedureEntity.getCreatedDate());
		procedureDTO.setModifiedDate(procedureEntity.getModifiedDate());
		procedureDTO.setScheduledDate(procedureEntity.getScheduledDate());
		procedureDTO.setNotes(procedureEntity.getNotes());

		if (procedureEntity.getProcedureTypeEntity() != null) {

			ProcedureTypeDTO procedureTypeDTO = new ProcedureTypeDTO();

			procedureTypeDTO.setId(procedureEntity.getProcedureTypeEntity().getId());

			procedureTypeDTO.setName(procedureEntity.getProcedureTypeEntity().getName());
			
			procedureTypeDTO.setWorksheetTemplate(procedureEntity.getProcedureTypeEntity().getWorksheetTemplate());
			
			procedureTypeDTO.setPrice(procedureEntity.getProcedureTypeEntity().getPrice());

			procedureDTO.setProcedureTypeDTO(procedureTypeDTO);
		}

		if (procedureEntity.getPatientEntity() != null) {

			PatientDTO patientDTO = new PatientDTO();

			patientDTO.setId(procedureEntity.getPatientEntity().getId());

			patientDTO.setName(procedureEntity.getPatientEntity().getName());

			patientDTO.setNationalId(procedureEntity.getPatientEntity().getNationalId());

			patientDTO.setPhoneNumber(procedureEntity.getPatientEntity().getPhoneNumber());

			patientDTO.setAge(procedureEntity.getPatientEntity().getAge());

			patientDTO.setHusbandName(procedureEntity.getPatientEntity().getHusbandName());

			patientDTO.setHusbandNationalId(procedureEntity.getPatientEntity().getHusbandNationalId());

			patientDTO.setHusbandPhoneNumber(procedureEntity.getPatientEntity().getHusbandPhoneNumber());

			patientDTO.setCreatedDate(procedureEntity.getPatientEntity().getCreatedDate());

			patientDTO.setModifiedDate(procedureEntity.getPatientEntity().getModifiedDate());

			procedureDTO.setPatientDTO(patientDTO);
		}

		if (procedureEntity.getPhysicianEntity() != null) {

			UserDTO physicianDTO = new UserDTO();

			physicianDTO.setId(procedureEntity.getPhysicianEntity().getId());

			physicianDTO.setName(procedureEntity.getPhysicianEntity().getName());

			physicianDTO.setEmail(procedureEntity.getPhysicianEntity().getEmail());

			physicianDTO.setPhoneNumber(procedureEntity.getPhysicianEntity().getPhoneNumber());

			physicianDTO.setPassword(procedureEntity.getPhysicianEntity().getPassword());

			physicianDTO.setRole(procedureEntity.getPhysicianEntity().getRole());

			physicianDTO.setToken(null);

			procedureDTO.setPhysicianDTO(physicianDTO);
		}
		
		if (procedureEntity.getEmbryologistEntity() != null) {

			UserDTO embryologistDTO = new UserDTO();

			embryologistDTO.setId(procedureEntity.getEmbryologistEntity().getId());

			embryologistDTO.setName(procedureEntity.getEmbryologistEntity().getName());

			embryologistDTO.setEmail(procedureEntity.getEmbryologistEntity().getEmail());

			embryologistDTO.setPhoneNumber(procedureEntity.getEmbryologistEntity().getPhoneNumber());

			embryologistDTO.setPassword(procedureEntity.getEmbryologistEntity().getPassword());

			embryologistDTO.setRole(procedureEntity.getEmbryologistEntity().getRole());

			embryologistDTO.setToken(null);

			procedureDTO.setEmbryologistDTO(embryologistDTO);
		}		

		return procedureDTO;
	}

	private ProcedureEntity mapToEntity(ProcedureDTO procedureDTO) {

		if (procedureDTO == null) {
			return null;
		}

		ProcedureEntity procedureEntity = new ProcedureEntity();

		procedureEntity.setValues(procedureDTO.getValues());
		procedureEntity.setPaymentStatus(procedureDTO.getPaymentStatus());
		procedureEntity.setCreatedDate(procedureDTO.getCreatedDate());
		procedureEntity.setModifiedDate(procedureDTO.getModifiedDate());
		procedureEntity.setScheduledDate(procedureDTO.getScheduledDate());
		procedureEntity.setNotes(procedureDTO.getNotes());

		if (procedureDTO.getProcedureTypeDTO() != null) {

			ProcedureTypeEntity procedureTypeEntity = new ProcedureTypeEntity();

			procedureTypeEntity.setId(procedureDTO.getProcedureTypeDTO().getId());

			procedureEntity.setProcedureTypeEntity(procedureTypeEntity);
		}

		if (procedureDTO.getPatientDTO() != null && procedureDTO.getPatientDTO().getId() != null) {

			PatientEntity patientEntity = new PatientEntity();

			patientEntity.setId(procedureDTO.getPatientDTO().getId());

			procedureEntity.setPatientEntity(patientEntity);
		}

		if (procedureDTO.getPhysicianDTO() != null && procedureDTO.getPhysicianDTO().getId() != null) {

			UserEntity physicianEntity = new UserEntity();

			physicianEntity.setId(procedureDTO.getPhysicianDTO().getId());

			procedureEntity.setPhysicianEntity(physicianEntity);
			
			System.out.println(physicianEntity);
		}
		
		if (procedureDTO.getEmbryologistDTO() != null && procedureDTO.getEmbryologistDTO().getId() != null) {

			UserEntity embryologistEntity = new UserEntity();

			embryologistEntity.setId(procedureDTO.getEmbryologistDTO().getId());

			procedureEntity.setEmbryologistEntity(embryologistEntity);
			
			System.out.println(embryologistEntity);
		}		

		return procedureEntity;
	}

}
