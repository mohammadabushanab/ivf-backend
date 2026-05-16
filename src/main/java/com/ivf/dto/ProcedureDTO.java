package com.ivf.dto;

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

	private Boolean isPaid;

	private Boolean isReport;

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

	public Boolean getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}

	public Boolean getIsReport() {
		return isReport;
	}

	public void setIsReport(Boolean isReport) {
		this.isReport = isReport;
	}

	@Override
	public String toString() {
		return "ProcedureDTO [id=" + id + ", values=" + values + ", paymentStatus=" + paymentStatus
				+ ", procedureTypeDTO=" + procedureTypeDTO + ", patientDTO=" + patientDTO + ", physicianDTO="
				+ physicianDTO + ", embryologistDTO=" + embryologistDTO + ", createdDate=" + createdDate
				+ ", modifiedDate=" + modifiedDate + ", isPaid=" + isPaid + ", isReport=" + isReport + "]";
	}

}
