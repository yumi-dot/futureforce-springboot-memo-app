package com.lesson.memo.model;

public enum Priority {
	HIGH("高"),
	MIDDLE("中"),
	LOW("低");
	
	private final String displayValue;
	
	Priority(String displayValue){
		this.displayValue = displayValue;
	}
	
	public String getDisplayValue() {
		return displayValue;
	}
}
