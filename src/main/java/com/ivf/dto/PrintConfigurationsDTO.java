package com.ivf.dto;

public class PrintConfigurationsDTO {

	private Long id;

	private String header;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	@Override
	public String toString() {
		return "PrintConfigurationsDTO [id=" + id + ", header=" + header + "]";
	}

}
