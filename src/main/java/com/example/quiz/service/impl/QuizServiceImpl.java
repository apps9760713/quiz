package com.example.quiz.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.quiz.constants.RtnCode;
import com.example.quiz.constants.Type;
import com.example.quiz.entity.Answer;
import com.example.quiz.entity.Quiz;
import com.example.quiz.repository.AnswerDao;
import com.example.quiz.repository.QuizDao;
import com.example.quiz.service.ifs.QuizService;
import com.example.quiz.vo.CreateOrUpdateReq;
import com.example.quiz.vo.SearchReq;
import com.example.quiz.vo.AnswerReq;
import com.example.quiz.vo.BaseRes;
import com.example.quiz.vo.SearchRes;
import com.example.quiz.vo.StatisticsRes;

import org.springframework.util.CollectionUtils;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizDao quizdao;

	@Autowired
	private AnswerDao answerDao;

	@Override
	public BaseRes create(CreateOrUpdateReq req) {
//		set不允許重複
//		LocalDate.now();現在時間
		return checkParams(req, true);
	}

	@Override
	public SearchRes search(SearchReq req) {
		if (!StringUtils.hasText(req.getQuizName())) {
			req.setQuizName("");
			;
		}
		if (req.getStartDate() == null) {
			req.setStartDate(LocalDate.of(1970, 1, 1));
		}
		if (req.getEndDate() == null) {
			req.setEndDate(LocalDate.of(2100, 12, 31));
		}
		if (req.isBackEnd()) {
			return new SearchRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(),
					quizdao.findByQuizNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(
							req.getQuizName(), req.getStartDate(), req.getEndDate()));
		} else {
			return new SearchRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(),
					quizdao.findByQuizNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqualAndPublishTrue(
							req.getQuizName(), req.getStartDate(), req.getEndDate()));
		}
	}

	@Override
	public BaseRes deleteQuiz(List<Integer> quizIds) {
		if (CollectionUtils.isEmpty(quizIds)) {
			return new BaseRes(RtnCode.PARAM_ERROR.getCode(), RtnCode.PARAM_ERROR.getMessage());
		}
		List<Quiz> res = quizdao.findAllByQuizIdInAndPublishFalseOrQuizIdInAndStartDateAfter(quizIds, quizIds,
				LocalDate.now());
		quizdao.deleteAll(res);
		return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
	}

	@Override
	public BaseRes deleteQuestion(int quizId, List<Integer> quIds) {
		if (quizId <= 0 || quIds.isEmpty()) {
			return new BaseRes(RtnCode.PARAM_ERROR.getCode(), RtnCode.PARAM_ERROR.getMessage());
		}
		List<Quiz> res = quizdao.findByQuizIdAndPublishFalseOrQuizIdAndStartDateAfterOrderByQuId(quizId, quizId,
				LocalDate.now());
		if (res.isEmpty()) {
			return new BaseRes(RtnCode.QUIZ_NOT_EXIST.getCode(), RtnCode.QUIZ_NOT_EXIST.getMessage());
		}
		int j = 0;
		for (Integer item : quIds) {
			res.remove(item - 1 - j);
			j++;
		}
		for (int i = 0; i < res.size(); i++) {
			res.get(i).setQuId(i + 1);
		}
		quizdao.deleteByQuizId(quizId);
		if (!res.isEmpty()) {
			quizdao.saveAll(res);
		}
		return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
	}

	@Override
	public BaseRes update(CreateOrUpdateReq req) {
		return checkParams(req, false);
	}

	private BaseRes checkParams(CreateOrUpdateReq req, boolean isCreate) {
		if (CollectionUtils.isEmpty(req.getQuizList())) {
			return new BaseRes(RtnCode.PARAM_ERROR.getCode(), RtnCode.PARAM_ERROR.getMessage());
		}
		Set<Integer> quizIds = new HashSet<>();
		Set<Integer> quIds = new HashSet<>();
		for (Quiz item : req.getQuizList()) {
			if (item.getQuId() <= 0 || item.getQuizId() <= 0 || !StringUtils.hasText(item.getQuizName())
					|| item.getStartDate() == null || item.getEndDate() == null //
					|| !StringUtils.hasText(item.getQuestion()) //
					|| !StringUtils.hasText(item.getType())) {
				return new BaseRes(RtnCode.PARAM_ERROR.getCode(), RtnCode.PARAM_ERROR.getMessage());
			}
			if (item.getStartDate().isAfter(item.getEndDate())) {
				return new BaseRes(RtnCode.TIME_FORMAT_ERROR.getCode(), RtnCode.TIME_FORMAT_ERROR.getMessage());
			}
			quizIds.add(item.getQuizId());
			quIds.add(item.getQuId());
		}
		if (quizIds.size() != 1) {
			return new BaseRes(RtnCode.QUIZ_EXISTS.getCode(), RtnCode.QUIZ_EXISTS.getMessage());
		}
		if (quIds.size() != req.getQuizList().size()) {
			return new BaseRes(RtnCode.DUPLICATED_QUESTIONID.getCode(), RtnCode.DUPLICATED_QUESTIONID.getMessage());
		}
		if (isCreate) {
			if (quizdao.existsByQuizId(req.getQuizList().get(0).getQuizId())) {
				return new BaseRes(RtnCode.QUIZ_EXISTS.getCode(), RtnCode.QUIZ_EXISTS.getMessage());
			}
		} else {
			if (!quizdao.existsByQuizIdAndPublishFalseOrQuizIdAndStartDateAfter(req.getQuizList().get(0).getQuizId(),
					req.getQuizList().get(0).getQuizId(), LocalDate.now())) {
				return new BaseRes(RtnCode.QUIZ_NOT_EXIST.getCode(), RtnCode.QUIZ_NOT_EXIST.getMessage());
			}
		}
		for (Quiz item : req.getQuizList()) {
			item.setPublish(req.isPublished());
		}
		quizdao.saveAll(req.getQuizList());
		return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
	}

	@Override
	public BaseRes answer(AnswerReq req) {
		if (CollectionUtils.isEmpty(req.getAnswerList())) {
			return new BaseRes(RtnCode.PARAM_ERROR.getCode(), RtnCode.PARAM_ERROR.getMessage());
		}
		Set<Integer> quizIds = new HashSet<>();
		Set<Integer> quIds = new HashSet<>();
		for (Answer item : req.getAnswerList()) {
			if (!StringUtils.hasText(item.getName()) || !StringUtils.hasText(item.getPhone())
					|| !StringUtils.hasText(item.getEmail()) || item.getAge() < 0 || item.getQuizId() <= 0
					|| item.getQuId() <= 0 || !StringUtils.hasText(item.getAnswer())) {
				return new BaseRes(RtnCode.PARAM_ERROR.getCode(), RtnCode.PARAM_ERROR.getMessage());
			}
			quizIds.add(item.getQuizId());
			quIds.add(item.getQuId());
		}
		if (quizIds.size() != 1) {
			return new BaseRes(RtnCode.QUIZ_EXISTS.getCode(), RtnCode.QUIZ_EXISTS.getMessage());
		}
		if (quIds.size() != req.getAnswerList().size()) {
			return new BaseRes(RtnCode.DUPLICATED_QUESTIONID.getCode(), RtnCode.DUPLICATED_QUESTIONID.getMessage());
		}
		for (Integer item : quizdao.findQuIdsByQuizIdAndNecessaryTrue(req.getAnswerList().get(0).getQuizId())) {
			if (!StringUtils.hasText(req.getAnswerList().get(item - 1).getAnswer())) {
				return new BaseRes(RtnCode.QUESTION_NO_ANSWER.getCode(), RtnCode.QUESTION_NO_ANSWER.getMessage());
			}
		}
		if (answerDao.existsByQuizIdAndEmail(req.getAnswerList().get(0).getQuizId(),
				req.getAnswerList().get(0).getEmail())) {
			return new BaseRes(RtnCode.DUPLICATED_QUIZ_ANSWER.getCode(), RtnCode.DUPLICATED_QUIZ_ANSWER.getMessage());
		}
		answerDao.saveAll(req.getAnswerList());
		return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
	}

	@Override
	public StatisticsRes statistics(int quizId) {
		if (quizId <= 0) {
			return new StatisticsRes(RtnCode.PARAM_ERROR.getCode(), RtnCode.PARAM_ERROR.getMessage());
		}
		List<Answer> answers = answerDao.findByQuizIdOrderByQuId(quizId);
		List<Quiz> quizs = quizdao.findByQuizId(quizId);
		List<Integer> qus = new ArrayList<>();
		for (Quiz item : quizs) {
			if (!item.getType().equalsIgnoreCase(Type.TEXT.getType())) {
				qus.add(item.getQuId());
			}
		}
		Map<Integer, String> quIdAnswerMap = new HashMap<>();
		for (Answer item : answers) {
			if (qus.contains(item.getQuId())) {
				String str = quIdAnswerMap.get(item.getQuId());
				str += item.getAnswer();
				quIdAnswerMap.put(item.getQuId(), str);
			} else {
				quIdAnswerMap.put(item.getQuId(), item.getAnswer());
			}
		}
		Map<String, Integer> answerCountMap = new HashMap<>();
		Map<Integer, Map<String, Integer>> quizIdAndAnsCountMap = new HashMap<>();
		// map寫法
		for (Entry<Integer, String> item : quIdAnswerMap.entrySet()) {
			String[] optionList = quizs.get(item.getKey() - 1).getOptions().split(";");
			for (String option : optionList) {
				String newStr = item.getValue();
				int length1 = newStr.length();
				newStr = newStr.replace(option, "");
				int length2 = newStr.length();
				int count = (length1 - length2) / option.length();
				answerCountMap.put(option, count);
			}
			quizIdAndAnsCountMap.put(item.getKey(), answerCountMap);
		}
		return new StatisticsRes(RtnCode.PARAM_ERROR.getCode(), RtnCode.PARAM_ERROR.getMessage(), quizIdAndAnsCountMap);
	}

	@Override
	public StatisticsRes statistics1(int quizId) {
		if (quizId <= 0) {
			return new StatisticsRes(RtnCode.PARAM_ERROR.getCode(), RtnCode.PARAM_ERROR.getMessage());
		}
		List<Answer> answers = answerDao.findByQuizId(quizId);
		List<Quiz> quizs = quizdao.findByQuizIdAndTypeNot(quizId, Type.TEXT.getType());
		Map<Integer, Map<String, Integer>> quizIdAndAnsCountMap = new HashMap<>();
		for (int i = 0; i < quizs.size(); i++) {
			int k = i;
			List<String> optionList = Arrays.asList(quizs.get(i).getOptions().split(";"));
			Map<String, Integer> answerCountMap = new HashMap<>();
			for (int j = 0; j < optionList.size(); j++) {
				int l = j;
				answerCountMap.put(optionList.get(l),
						answers.stream().filter(s -> s.getQuId() == quizs.get(k).getQuId())
								.filter(s -> s.getAnswer().contains(optionList.get(l))).collect(Collectors.toList())
								.size());
			}
			quizIdAndAnsCountMap.put(quizs.get(i).getQuId(), answerCountMap);
		}

		return new StatisticsRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(), quizIdAndAnsCountMap);
	}

}
