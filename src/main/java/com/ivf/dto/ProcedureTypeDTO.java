package com.ivf.dto;

import java.math.BigDecimal;

public class ProcedureTypeDTO {

	private Long id;

	private String name;

	private String worksheetTemplate;

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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "ProcedureTypeDTO [id=" + id + ", name=" + name + ", worksheetTemplate=" + worksheetTemplate + ", price="
				+ price + "]";
	}

}
