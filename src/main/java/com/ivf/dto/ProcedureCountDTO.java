package com.ivf.dto;

public class ProcedureCountDTO {
	private Long procedureTypeId;
	private String procedureTypeName;
	private Long count;

	public ProcedureCountDTO(Long procedureTypeId, String procedureTypeName, Long count) {
		this.procedureTypeId = procedureTypeId;
		this.procedureTypeName = procedureTypeName;
		this.count = count;
	}

	public Long getProcedureTypeId() {
		return procedureTypeId;
	}

	public void setProcedureTypeId(Long procedureTypeId) {
		this.procedureTypeId = procedureTypeId;
	}

	public String getProcedureTypeName() {
		return procedureTypeName;
	}

	public void setProcedureTypeName(String procedureTypeName) {
		this.procedureTypeName = procedureTypeName;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "ProcedureCountDTO [procedureTypeId=" + procedureTypeId + ", procedureTypeName=" + procedureTypeName
				+ ", count=" + count + "]";
	}

}
