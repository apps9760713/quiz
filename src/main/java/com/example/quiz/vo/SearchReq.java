package com.example.quiz.vo;

import java.time.LocalDate;

public class SearchReq {

	private String quizName;

	private LocalDate startDate;

	private LocalDate endDate;

	private boolean isBackEnd;

	public SearchReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SearchReq(String quizName, LocalDate startDate, LocalDate endDate, boolean isBackEnd) {
		super();
		this.quizName = quizName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isBackEnd = isBackEnd;
	}

	public String getQuizName() {
		return quizName;
	}

	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public boolean isBackEnd() {
		return isBackEnd;
	}

	public void setBackEnd(boolean isBackEnd) {
		this.isBackEnd = isBackEnd;
	}

}
