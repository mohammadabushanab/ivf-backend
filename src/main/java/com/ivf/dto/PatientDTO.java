package com.ivf.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PatientDTO {

	private Long id;

	private String name;

	private String nationalId;

	private String phoneNumber;

	private String age;

	private String husbandName;

	private String husbandNationalId;

	private String husbandPhoneNumber;

	private LocalDateTime createdDate;

	private LocalDateTime modifiedDate;

	private LocalDate fromDate;

	private LocalDate toDate;

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

	@Override
	public String toString() {
		return "PatientDTO [id=" + id + ", name=" + name + ", nationalId=" + nationalId + ", phoneNumber=" + phoneNumber
				+ ", age=" + age + ", husbandName=" + husbandName + ", husbandNationalId=" + husbandNationalId
				+ ", husbandPhoneNumber=" + husbandPhoneNumber + ", createdDate=" + createdDate + ", modifiedDate="
				+ modifiedDate + ", fromDate=" + fromDate + ", toDate=" + toDate + "]";
	}

}
