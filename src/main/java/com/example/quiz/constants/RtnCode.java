package com.example.quiz.constants;

import java.util.List;

import com.example.quiz.entity.Quiz;

public enum RtnCode {
	
	SUCCESS(200, "Success"),//
	PARAM_ERROR(400, "PARAM_ERROR"),//
	QUIZ_EXISTS(400, "QUIZ_EXISTS"),//
	QUIZ_NOT_EXIST(400, "QUIZ_NOT_EXIST"),//
	DUPLICATED_QUESTIONID(400, "DUPLICATED_QUESTIONID"),//
	TIME_FORMAT_ERROR(400, "TIME_FORMAT_ERROR"),//
	QUIZ_ID_ERROR(400, "QUIZ_ID_ERROR"),//
	QUESTION_NO_ANSWER(400, "QUESTION_NO_ANSWER"),//
	DUPLICATED_QUIZ_ANSWER(400, "DUPLICATED_QUIZ_ANSWER"),//
	QUIZ_ID_NOT_MATCH(400, "QUIZ_ID_NOT_MATCH");
	
	
	

	private int code;

	private String message;

	private RtnCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
