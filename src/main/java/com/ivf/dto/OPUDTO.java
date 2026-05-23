package com.ivf.dto;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OPUDTO {

	private Long id;

	private Map<String, Object> values;

	private LocalDateTime createdDate;

	private LocalDateTime modifiedDate;

	private String status;

	private boolean isDeleted;

	@JsonProperty("patient")
	private PatientDTO patientDTO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Map<String, Object> getValues() {
		return values;
	}

	public void setValues(Map<String, Object> values) {
		this.values = values;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public PatientDTO getPatientDTO() {
		return patientDTO;
	}

	public void setPatientDTO(PatientDTO patientDTO) {
		this.patientDTO = patientDTO;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "OPUDTO [id=" + id + ", values=" + values + ", createdDate=" + createdDate + ", modifiedDate="
				+ modifiedDate + ", status=" + status + ", isDeleted=" + isDeleted + ", patientDTO=" + patientDTO + "]";
	}

}
