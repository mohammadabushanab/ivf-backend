package com.ivf.entitis;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import com.ivf.converters.JsonConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "procedures")
public class ProcedureEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Convert(converter = JsonConverter.class)
	@Column(name = "values", columnDefinition = "TEXT")
	private Map<String, Object> values;

	@Column(name = "payment_status")
	private String paymentStatus;

	@ManyToOne()
	@JoinColumn(name = "procedure_type_id", referencedColumnName = "id")
	private ProcedureTypeEntity procedureTypeEntity;

	@ManyToOne()
	@JoinColumn(name = "patient_id", referencedColumnName = "id")
	private PatientEntity patientEntity;

	@ManyToOne()
	@JoinColumn(name = "physician_id", referencedColumnName = "id")
	private UserEntity physicianEntity;

	@ManyToOne()
	@JoinColumn(name = "embryologist_id", referencedColumnName = "id")
	private UserEntity embryologistEntity;

	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;

	@Column(name = "scheduled_date")
	private LocalDateTime scheduledDate;

	@Column(name = "notes", columnDefinition = "TEXT")
	private String notes;

	@PrePersist
	public void onCreate() {
		this.createdDate = LocalDateTime.now();
		this.modifiedDate = LocalDateTime.now();
	}

	@PreUpdate
	public void onUpdate() {
		this.modifiedDate = LocalDateTime.now();
	}

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

	public ProcedureTypeEntity getProcedureTypeEntity() {
		return procedureTypeEntity;
	}

	public void setProcedureTypeEntity(ProcedureTypeEntity procedureTypeEntity) {
		this.procedureTypeEntity = procedureTypeEntity;
	}

	public PatientEntity getPatientEntity() {
		return patientEntity;
	}

	public void setPatientEntity(PatientEntity patientEntity) {
		this.patientEntity = patientEntity;
	}

	public UserEntity getPhysicianEntity() {
		return physicianEntity;
	}

	public void setPhysicianEntity(UserEntity physicianEntity) {
		this.physicianEntity = physicianEntity;
	}

	public UserEntity getEmbryologistEntity() {
		return embryologistEntity;
	}

	public void setEmbryologistEntity(UserEntity embryologistEntity) {
		this.embryologistEntity = embryologistEntity;
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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "ProcedureEntity [id=" + id + ", values=" + values + ", paymentStatus=" + paymentStatus
				+ ", procedureTypeEntity=" + procedureTypeEntity + ", patientEntity=" + patientEntity
				+ ", physicianEntity=" + physicianEntity + ", embryologistEntity=" + embryologistEntity
				+ ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", scheduledDate=" + scheduledDate
				+ ", notes=" + notes + "]";
	}
	
	

}
