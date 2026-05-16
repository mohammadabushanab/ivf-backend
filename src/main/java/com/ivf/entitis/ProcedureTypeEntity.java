package com.ivf.entitis;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "procedure_types", schema = "ivf")
public class ProcedureTypeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "worksheet_template", columnDefinition = "text")
	private String worksheetTemplate;

	@Column(name = "price",precision = 12, scale = 2, nullable = false)
	private BigDecimal price;

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

	public String getWorksheetTemplate() {
		return worksheetTemplate;
	}

	public void setWorksheetTemplate(String worksheetTemplate) {
		this.worksheetTemplate = worksheetTemplate;

	}

	@Override
	public String toString() {
		return "ProcedureTypeEntity [id=" + id + ", name=" + name + ", worksheetTemplate=" + worksheetTemplate
				+ ", price=" + price + "]";
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
