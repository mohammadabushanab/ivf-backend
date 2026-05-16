package com.ivf.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ivf.dto.DashboardResponseDTO;
import com.ivf.dto.ProcedureCountDTO;
import com.ivf.repositories.PatientRepository;
import com.ivf.repositories.ProcedureRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private ProcedureRepository procedureRepository;

	@PersistenceContext
	private EntityManager entityManager;

	private List<ProcedureCountDTO> countProceduresByType() {

		StringBuilder sql = new StringBuilder();

		sql.append("""
				    SELECT
				        pt.id AS procedure_type_id,
				        pt.name AS procedure_type_name,
				        COUNT(p.id) AS procedure_count
				    FROM procedures p
				    JOIN procedure_types pt
				        ON pt.id = p.procedure_type_id
				    GROUP BY pt.id, pt.name
				    ORDER BY COUNT(p.id) DESC
				""");

		Query query = entityManager.createNativeQuery(sql.toString());

		List<Object[]> rows = query.getResultList();

		List<ProcedureCountDTO> procedureCounts = new ArrayList<>();

		if (rows != null) {
			for (Object[] row : rows) {

				ProcedureCountDTO dto = new ProcedureCountDTO(((Number) row[0]).longValue(),
						row[1] != null ? row[1].toString() : "", ((Number) row[2]).longValue());

				procedureCounts.add(dto);
			}
		}

		return procedureCounts;
	}

	private Long countFrozenEmbryos() {

		StringBuilder sql = new StringBuilder();

		sql.append("""
				    SELECT COALESCE(
				        SUM(
				            jsonb_array_length(
				                COALESCE(p.values::jsonb -> 'embryoRows', '[]'::jsonb)
				            )
				        ), 0
				    )
				    FROM procedures p
				    JOIN procedure_types pt
				        ON pt.id = p.procedure_type_id
				    WHERE pt.name = :procedureTypeName
				""");

		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter("procedureTypeName", "Embryo Freezing");

		Object result = query.getSingleResult();

		return result != null ? ((Number) result).longValue() : 0L;
	}

	private Long countFrozenEggs() {

		StringBuilder sql = new StringBuilder();

		sql.append("""
				    SELECT COALESCE(
				        SUM(
				            jsonb_array_length(
				                COALESCE(p.values::jsonb -> 'oocyteRows', '[]'::jsonb)
				            )
				        ), 0
				    )
				    FROM procedures p
				    JOIN procedure_types pt
				        ON pt.id = p.procedure_type_id
				    WHERE pt.name = :procedureTypeName
				""");

		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter("procedureTypeName", "Egg Freezing");

		Object result = query.getSingleResult();

		return result != null ? ((Number) result).longValue() : 0L;
	}

	private Long countFrozenSpermAmpoules() {

		StringBuilder sql = new StringBuilder();

		sql.append("""
				    SELECT COALESCE(
				        SUM(
				            COALESCE(NULLIF(item ->> 'ampoules', '')::BIGINT, 0)
				        ), 0
				    )
				    FROM procedures p
				    JOIN procedure_types pt
				        ON pt.id = p.procedure_type_id
				    CROSS JOIN jsonb_array_elements(
				        COALESCE(p.values::jsonb -> 'freezingRows', '[]'::jsonb)
				    ) item
				    WHERE pt.name = :procedureTypeName
				""");

		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter("procedureTypeName", "Sperm Freezing");

		Object result = query.getSingleResult();

		return result != null ? ((Number) result).longValue() : 0L;
	}

	@Override
	public DashboardResponseDTO getDashboardData() {

		Long totalPatients = patientRepository.count();
		
		System.out.println(totalPatients);

		Long totalProcedures = procedureRepository.count();
		
		System.out.println(totalProcedures);

		Long frozenEmbryos = countFrozenEmbryos();
		
		System.out.println(frozenEmbryos);

		Long frozenEggs = countFrozenEggs();
		
		System.out.println(frozenEggs);

		Long frozenSpermAmpoules = countFrozenSpermAmpoules();
		
		System.out.println(frozenSpermAmpoules);

		Long totalFreezingItems = safe(frozenEmbryos) + safe(frozenEggs) + safe(frozenSpermAmpoules);

		List<ProcedureCountDTO> procedureCounts = countProceduresByType();

		DashboardResponseDTO dashboardResponseDTO = new DashboardResponseDTO(totalPatients, totalProcedures,
				frozenEmbryos, frozenEggs, frozenSpermAmpoules, totalFreezingItems, procedureCounts);

		return dashboardResponseDTO;
	}

	private Long safe(Long value) {
		return value == null ? 0L : value;
	}
}
