package com.ivf.entitis;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "patients")
public class PatientEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "national_id")
	private String nationalId;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "age")
	private String age;

	@Column(name = "husband_name")
	private String husbandName;

	@Column(name = "husband_national_id")
	private String husbandNationalId;

	@Column(name = "husband_phone_number")
	private String husbandPhoneNumber;

	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNationalId() {
		return nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getHusbandName() {
		return husbandName;
	}

	public void setHusbandName(String husbandName) {
		this.husbandName = husbandName;
	}

	public String getHusbandNationalId() {
		return husbandNationalId;
	}

	public void setHusbandNationalId(String husbandNationalId) {
		this.husbandNationalId = husbandNationalId;
	}

	public String getHusbandPhoneNumber() {
		return husbandPhoneNumber;
	}

	public void setHusbandPhoneNumber(String husbandPhoneNumber) {
		this.husbandPhoneNumber = husbandPhoneNumber;
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

	@Override
	public String toString() {
		return "PatientEntity [id=" + id + ", name=" + name + ", nationalId=" + nationalId + ", phoneNumber="
				+ phoneNumber + ", age=" + age + ", husbandName=" + husbandName + ", husbandNationalId="
				+ husbandNationalId + ", husbandPhoneNumber=" + husbandPhoneNumber + ", createdDate=" + createdDate
				+ ", modifiedDate=" + modifiedDate + "]";
	}

}
