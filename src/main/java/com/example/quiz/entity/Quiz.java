package com.example.quiz.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "quiz")
@IdClass(value = QuizId.class)
public class Quiz {

	@Id
	@Column(name = "quiz_id")
	@JsonProperty("quiz_Id")
	private int quizId;

	@Id
	@Column(name = "qu_id")
	@JsonProperty("qu_id")
	private int quId;

	@Column(name = "quiz_name")
	@JsonProperty("quiz_name")
	private String quizName;

	@Column(name = "quiz_description")
	@JsonProperty("quiz_description")
	private String quizDescription;

	@Column(name = "start_date")
	@JsonProperty("start_date")
	private LocalDate startDate;

	@Column(name = "end_date")
	@JsonProperty("end_date")
	private LocalDate endDate;

	@Column(name = "question")
	@JsonProperty("question")
	private String question;

	@Column(name = "type")
	@JsonProperty("type")
	private String type;

	@Column(name = "neccesary")
	@JsonProperty("neccesary")
	private Boolean neccessary;

	@Column(name = "options")
	@JsonProperty("options")
	private String options;
	
	@Column(name = "publish")
	@JsonProperty("publish")
	private Boolean publish;

	public Quiz() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Quiz(int quizId, int quId, String quizName, String quizDescription, LocalDate startDate, LocalDate endDate,
			String question, String type, Boolean neccessary, String options, Boolean publish) {
		super();
		this.quizId = quizId;
		this.quId = quId;
		this.quizName = quizName;
		this.quizDescription = quizDescription;
		this.startDate = startDate;
		this.endDate = endDate;
		this.question = question;
		this.type = type;
		this.neccessary = neccessary;
		this.options = options;
		this.publish = publish;
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

	public String getQuizName() {
		return quizName;
	}

	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}

	public String getQuizDescription() {
		return quizDescription;
	}

	public void setQuizDescription(String quizDescription) {
		this.quizDescription = quizDescription;
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

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getNeccessary() {
		return neccessary;
	}

	public void setNeccessary(Boolean neccessary) {
		this.neccessary = neccessary;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public Boolean getPublish() {
		return publish;
	}

	public void setPublish(Boolean publish) {
		this.publish = publish;
	}

	

}
