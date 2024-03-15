package com.example.quiz.vo;

import com.example.quiz.constants.RtnCode;

public class BaseRes {
	
	private int code;

	private String message;

	public BaseRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BaseRes(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
