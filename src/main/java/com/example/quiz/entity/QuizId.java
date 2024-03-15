package com.example.quiz.entity;

import java.io.Serializable;

public class QuizId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int quizId;
	
	private int quId;

	public QuizId() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuizId(int quizId, int quId) {
		super();
		this.quizId = quizId;
		this.quId = quId;
	}

	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	public int getQuId() {
		return quId;
	}

	public void setQuId(int quId) {
		this.quId = quId;
	}
	
}