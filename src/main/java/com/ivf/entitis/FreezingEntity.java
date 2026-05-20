package com.ivf.entitis;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "freezing")
public class FreezingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "type")
	private String type;

	@Column(name = "total")
	private Long total;

	@Column(name = "remaining")
	private Long remaining;

	@Column(name = "dewar")
	private String dewar;

	@Column(name = "canister")
	private String canister;

	@Column(name = "notes")
	private String notes;

	@Column(name = "date")
	private LocalDateTime date;

	@ManyToOne()
	@JoinColumn(name = "patient_id", referencedColumnName = "id")
	private PatientEntity patientEntity;

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

	public PatientEntity getPatientEntity() {
		return patientEntity;
	}

	public void setPatientEntity(PatientEntity patientEntity) {
		this.patientEntity = patientEntity;
	}

	@Override
	public String toString() {
		return "FreezingEntity [id=" + id + ", type=" + type + ", total=" + total + ", remaining=" + remaining
				+ ", dewar=" + dewar + ", canister=" + canister + ", notes=" + notes + ", date=" + date
				+ ", patientEntity=" + patientEntity + "]";
	}

}
