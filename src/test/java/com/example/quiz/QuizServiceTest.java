package com.example.quiz;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.example.quiz.entity.Quiz;
import com.example.quiz.repository.QuizDao;
import com.example.quiz.service.ifs.QuizService;
import com.example.quiz.vo.BaseRes;
import com.example.quiz.vo.CreateOrUpdateReq;

@SpringBootTest
public class QuizServiceTest {

	@Autowired
	private QuizService quizService;
	@Autowired
	private QuizDao quizDao;

	// 每跑一次TEST都會執行
	@BeforeEach
	private void addData() {
		CreateOrUpdateReq req = new CreateOrUpdateReq();
		req.setQuizList(new ArrayList<>(Arrays.asList(new Quiz(3, 1, "a", "a", LocalDate.now().plusDays(2),
				LocalDate.now().plusDays(9), "A", "A", true, "a", false))));
		quizService.create(req);
	}

	@Test
	public void createTest() {
		CreateOrUpdateReq req = new CreateOrUpdateReq();
		BaseRes res = quizService.create(req);
		Assert.isTrue(res.getCode() != 200, "wrong");
		//
		// int quizId, int quId, String quizName, String quizDescription, LocalDate
		// startDate, LocalDate endDate,
		// String question, String type, Boolean neccessary, String options, Boolean
		// publish
		quizIdTest(req, res);
		// quid
		quIdTest(req, res);
		req.setQuizList(new ArrayList<>(Arrays.asList(new Quiz(1, -1, "a", "a", LocalDate.now().plusDays(2),
				LocalDate.now().plusDays(9), "A", "A", true, "a", false))));
		res = quizService.create(req);
		Assert.isTrue(res.getCode() != 200, "quid wrong");
		// quizname
		req.setQuizList(new ArrayList<>(Arrays.asList(new Quiz(1, 1, "", "a", LocalDate.now().plusDays(2),
				LocalDate.now().plusDays(9), "A", "A", true, "a", false))));
		res = quizService.create(req);
		Assert.isTrue(res.getCode() != 200, "quizname wrong");
		// startdate
		req.setQuizList(new ArrayList<>(Arrays
				.asList(new Quiz(1, 1, "a", "a", null, LocalDate.now().plusDays(9), "A", "A", true, "a", false))));
		res = quizService.create(req);
		Assert.isTrue(res.getCode() != 200, "startdate wrong");
		// enddate
		req.setQuizList(new ArrayList<>(Arrays
				.asList(new Quiz(1, 1, "a", "a", LocalDate.now().plusDays(2), null, "A", "A", true, "a", false))));
		res = quizService.create(req);
		Assert.isTrue(res.getCode() != 200, "enddate wrong");
		// question
		req.setQuizList(new ArrayList<>(Arrays.asList(new Quiz(1, 1, "a", "a", LocalDate.now().plusDays(2),
				LocalDate.now().plusDays(9), "", "A", true, "a", false))));
		res = quizService.create(req);
		Assert.isTrue(res.getCode() != 200, "question wrong");
		// type
		req.setQuizList(new ArrayList<>(Arrays.asList(new Quiz(1, 1, "a", "a", LocalDate.now().plusDays(2),
				LocalDate.now().plusDays(9), "A", "", true, "a", false))));
		res = quizService.create(req);
		Assert.isTrue(res.getCode() != 200, "type wrong");
		// startdate>enddate
		req.setQuizList(new ArrayList<>(Arrays.asList(new Quiz(1, 1, "a", "a", LocalDate.now().plusDays(9),
				LocalDate.now().plusDays(2), "A", "A", true, "a", false))));
		res = quizService.create(req);
		Assert.isTrue(res.getCode() != 200, "startdate>enddate wrong");
		// 成功
		req.setQuizList(new ArrayList<>(Arrays.asList(new Quiz(3, 1, "a", "a", LocalDate.now().plusDays(2),
				LocalDate.now().plusDays(9), "A", "A", true, "a", false))));
		res = quizService.create(req);
		Assert.isTrue(res.getCode() != 400, "not success");
		// 測試已存在
		req.setQuizList(new ArrayList<>(Arrays.asList(new Quiz(3, 1, "a", "a", LocalDate.now().plusDays(2),
				LocalDate.now().plusDays(9), "A", "A", true, "a", false))));
		res = quizService.create(req);
		Assert.isTrue(res.getCode() != 200, "exist wrong");
		quizDao.deleteByQuizId(3);

	}

	private void quizIdTest(CreateOrUpdateReq req, BaseRes res) {
		// quizid
		req.setQuizList(new ArrayList<>(Arrays.asList(new Quiz(0, 1, "a", "a", LocalDate.now().plusDays(2),
				LocalDate.now().plusDays(9), "A", "A", true, "a", false))));
		res = quizService.create(req);
		Assert.isTrue(res.getCode() != 200, "quizid wrong");
	}
	
	private void quIdTest(CreateOrUpdateReq req, BaseRes res) {
		req.setQuizList(new ArrayList<>(Arrays.asList(new Quiz(1, -1, "a", "a", LocalDate.now().plusDays(2),
				LocalDate.now().plusDays(9), "A", "A", true, "a", false))));
		res = quizService.create(req);
		Assert.isTrue(res.getCode() != 200, "quid wrong");
	}

	@Test
	public void updateTest() {

	}
}
