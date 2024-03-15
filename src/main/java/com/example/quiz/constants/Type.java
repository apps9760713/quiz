package com.example.quiz.constants;

public enum Type {
	
	SINGLE_CHOICE("single"), //
	MUILTY_CHOICE("MUILTY_CHOICE"), //
	TEXT("TEXT");

	private String type;

	private Type(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
