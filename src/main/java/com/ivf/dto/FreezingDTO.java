package com.ivf.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FreezingDTO {

	private Long id;

	private String type;

	private Long total;

	private Long remaining;

	private String dewar;

	private String canister;

	private String notes;

	private LocalDateTime date;

	@JsonProperty("patient")
	private PatientDTO patientDTO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getRemaining() {
		return remaining;
	}

	public void setRemaining(Long remaining) {
		this.remaining = remaining;
	}

	public String getDewar() {
		return dewar;
	}

	public void setDewar(String dewar) {
		this.dewar = dewar;
	}

	public String getCanister() {
		return canister;
	}

	public void setCanister(String canister) {
		this.canister = canister;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public PatientDTO getPatientDTO() {
		return patientDTO;
	}

	public void setPatientDTO(PatientDTO patientDTO) {
		this.patientDTO = patientDTO;
	}

	@Override
	public String toString() {
		return "FreezingDTO [id=" + id + ", type=" + type + ", total=" + total + ", remaining=" + remaining + ", dewar="
				+ dewar + ", canister=" + canister + ", notes=" + notes + ", date=" + date + ", patientDTO="
				+ patientDTO + "]";
	}

}
