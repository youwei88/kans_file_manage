package pojo;

import java.io.Serializable;

public class Operation implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private String title;

	private String operName;

	private int type;

	private int parentName;

	private int orderNum;

	private String flagName;

	private int status;

	private String displayParentName;
	
	private int isFromSap;
	
	private int optionStatus;
	
	private String code;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getParentName() {
		return parentName;
	}

	public void setParentName(int parentName) {
		this.parentName = parentName;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getFlagName() {
		return flagName;
	}

	public void setFlagName(String flagName) {
		this.flagName = flagName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDisplayParentName() {
		return displayParentName;
	}

	public void setDisplayParentName(String displayParentName) {
		this.displayParentName = displayParentName;
	}

	public int getIsFromSap() {
		return isFromSap;
	}

	public void setIsFromSap(int isFromSap) {
		this.isFromSap = isFromSap;
	}

	public int getOptionStatus() {
		return optionStatus;
	}

	public void setOptionStatus(int optionStatus) {
		this.optionStatus = optionStatus;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	

}