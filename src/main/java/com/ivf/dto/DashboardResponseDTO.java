package com.ivf.dto;

import java.util.List;

public class DashboardResponseDTO {

	private Long totalPatients;
	private Long totalProcedures;
	private Long frozenEmbryos;
	private Long frozenEggs;
	private Long frozenSpermAmpoules;
	private Long totalFreezingItems;
	private List<ProcedureCountDTO> proceduresCounts;

	public DashboardResponseDTO(Long totalPatients, Long totalProcedures, Long frozenEmbryos, Long frozenEggs,
			Long frozenSpermAmpoules, Long totalFreezingItems, List<ProcedureCountDTO> procedureCounts) {
		this.totalPatients = totalPatients;
		this.totalProcedures = totalProcedures;
		this.frozenEmbryos = frozenEmbryos;
		this.frozenEggs = frozenEggs;
		this.frozenSpermAmpoules = frozenSpermAmpoules;
		this.totalFreezingItems = totalFreezingItems;
		this.proceduresCounts = procedureCounts;
	}

	public Long getTotalPatients() {
		return totalPatients;
	}

	public void setTotalPatients(Long totalPatients) {
		this.totalPatients = totalPatients;
	}

	public Long getTotalProcedures() {
		return totalProcedures;
	}

	public void setTotalProcedures(Long totalProcedures) {
		this.totalProcedures = totalProcedures;
	}

	public Long getFrozenEmbryos() {
		return frozenEmbryos;
	}

	public void setFrozenEmbryos(Long frozenEmbryos) {
		this.frozenEmbryos = frozenEmbryos;
	}

	public Long getFrozenEggs() {
		return frozenEggs;
	}

	public void setFrozenEggs(Long frozenEggs) {
		this.frozenEggs = frozenEggs;
	}

	public Long getFrozenSpermAmpoules() {
		return frozenSpermAmpoules;
	}

	public void setFrozenSpermAmpoules(Long frozenSpermAmpoules) {
		this.frozenSpermAmpoules = frozenSpermAmpoules;
	}

	public Long getTotalFreezingItems() {
		return totalFreezingItems;
	}

	public void setTotalFreezingItems(Long totalFreezingItems) {
		this.totalFreezingItems = totalFreezingItems;
	}

	public List<ProcedureCountDTO> getProceduresCounts() {
		return proceduresCounts;
	}

	public void setProceduresCounts(List<ProcedureCountDTO> proceduresCounts) {
		this.proceduresCounts = proceduresCounts;
	}

	@Override
	public String toString() {
		return "DashboardResponseDTO [totalPatients=" + totalPatients + ", totalProcedures=" + totalProcedures
				+ ", frozenEmbryos=" + frozenEmbryos + ", frozenEggs=" + frozenEggs + ", frozenSpermAmpoules="
				+ frozenSpermAmpoules + ", totalFreezingItems=" + totalFreezingItems + ", proceduresCounts="
				+ proceduresCounts + "]";
	}

}
