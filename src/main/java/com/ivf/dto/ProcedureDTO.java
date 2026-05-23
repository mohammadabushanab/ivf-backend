package com.ivf.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProcedureDTO {

	private Long id;

	private Map<String, Object> values;

	private String paymentStatus;

	@JsonProperty("procedureType")
	private ProcedureTypeDTO procedureTypeDTO;

	@JsonProperty("patient")
	private PatientDTO patientDTO;

	@JsonProperty("physician")
	private UserDTO physicianDTO;

	@JsonProperty("embryologist")
	private UserDTO embryologistDTO;

	private LocalDateTime createdDate;

	private LocalDateTime modifiedDate;

	private LocalDateTime scheduledDate;

	private String dateSearchType;

	private LocalDate fromDate;

	private LocalDate toDate;

	private String notes;

	private String status;

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

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public ProcedureTypeDTO getProcedureTypeDTO() {
		return procedureTypeDTO;
	}

	public void setProcedureTypeDTO(ProcedureTypeDTO procedureTypeDTO) {
		this.procedureTypeDTO = procedureTypeDTO;
	}

	public PatientDTO getPatientDTO() {
		return patientDTO;
	}

	public void setPatientDTO(PatientDTO patientDTO) {
		this.patientDTO = patientDTO;
	}

	public UserDTO getPhysicianDTO() {
		return physicianDTO;
	}

	public void setPhysicianDTO(UserDTO physicianDTO) {
		this.physicianDTO = physicianDTO;
	}

	public UserDTO getEmbryologistDTO() {
		return embryologistDTO;
	}

	public void setEmbryologistDTO(UserDTO embryologistDTO) {
		this.embryologistDTO = embryologistDTO;
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

	public LocalDateTime getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(LocalDateTime scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public String getDateSearchType() {
		return dateSearchType;
	}

	public void setDateSearchType(String dateSearchType) {
		this.dateSearchType = dateSearchType;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ProcedureDTO [id=" + id + ", values=" + values + ", paymentStatus=" + paymentStatus
				+ ", procedureTypeDTO=" + procedureTypeDTO + ", patientDTO=" + patientDTO + ", physicianDTO="
				+ physicianDTO + ", embryologistDTO=" + embryologistDTO + ", createdDate=" + createdDate
				+ ", modifiedDate=" + modifiedDate + ", scheduledDate=" + scheduledDate + ", dateSearchType="
				+ dateSearchType + ", fromDate=" + fromDate + ", toDate=" + toDate + ", notes=" + notes + ", status="
				+ status + "]";
	}

}
