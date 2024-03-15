package com.example.quiz.service.ifs;

import java.time.LocalDate;
import java.util.List;

import com.example.quiz.vo.CreateOrUpdateReq;
import com.example.quiz.vo.SearchReq;
import com.example.quiz.entity.Quiz;
import com.example.quiz.vo.AnswerReq;
import com.example.quiz.vo.BaseRes;
import com.example.quiz.vo.SearchRes;
import com.example.quiz.vo.StatisticsRes;

public interface QuizService {
	
	public BaseRes create(CreateOrUpdateReq req);
	
	public SearchRes search(SearchReq req);
	
	public BaseRes deleteQuiz(List<Integer> quizIds);
	
	public BaseRes deleteQuestion(int quiz_id, List<Integer> quIds);
	
	public BaseRes update(CreateOrUpdateReq req);
	
	public BaseRes answer(AnswerReq req);
	
	public StatisticsRes statistics(int quizId);
	
	public StatisticsRes statistics1(int quizId);
}
