package com.example.quiz;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import com.example.quiz.entity.Answer;
import com.example.quiz.entity.Quiz;
import com.example.quiz.entity.QuizId;
import com.example.quiz.repository.AnswerDao;
import com.example.quiz.repository.QuizDao;
import com.example.quiz.service.ifs.QuizService;
import com.example.quiz.vo.CreateOrUpdateReq;
import com.example.quiz.vo.StatisticsRes;
import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;

@SpringBootTest
class QuizApplicationTests {

	@Autowired
	private QuizService quizService;
	@Autowired
	private AnswerDao answerDao;
	@Autowired
	private QuizDao quizDao;

	@Test
	void contextLoads() {
	}

	@Test
	public void test() {
		List<Quiz> list = new ArrayList<>();
		list.add(new Quiz(1, 1, "aaa", "", LocalDate.of(2024, 3, 11), LocalDate.of(2024, 3, 19), "aaa", "單", false, "a",
				false));
		list.add(new Quiz(1, 2, "bbb", "", LocalDate.of(2024, 3, 11), LocalDate.of(2024, 3, 19), "aaa", "單", false, "a",
				false));
		list.add(new Quiz(1, 3, "ccc", "", LocalDate.of(2024, 3, 11), LocalDate.of(2024, 3, 19), "aaa", "單", false, "a",
				false));
		System.out.println(list);
		// quizService.create(new CreateOrUpdateReq(list));
	}

	@Test
	public void test1() {
		Map<String, Integer> answerMap = new HashMap<>();
		answerMap.put(null, null);
	}

	@Test
	public void test2() {
		List<Integer> list = Arrays.asList(1, 2, 2, 3, 1, 4, 3, 2, 5, 5, 6);
		List<LinkedList> list2 = new ArrayList<>();
		Collections.sort(list);
		System.out.println(list);
		String str = "A;B;C;D";
		String str1 = "A;B;C";
		String[] strArr = str.split(";");
		LinkedList list1 = new LinkedList<>();
		list1.add(1);
		list1.add("a");
		LinkedList list3 = new LinkedList<String>(Arrays.asList(str1.split(";")));
		System.out.println(list1.get(0));
		list2.add(list1);
		list2.add(list3);
		System.out.println(list1);

	}

	@Test
	public void test3() {
		answerDao.deleteAll();
		quizDao.deleteAll();
		List<Quiz> list2 = new ArrayList<>();// option
		list2.add(new Quiz(1, 1, "aaa", "", LocalDate.of(2024, 3, 11), LocalDate.of(2024, 3, 19), "A", "單", false,
				"A;B;C;D", false));
		list2.add(new Quiz(1, 2, "bbb", "", LocalDate.of(2024, 3, 11), LocalDate.of(2024, 3, 19), "aaa", "單", false,
				"A;B;C;D", false));
		list2.add(new Quiz(1, 3, "ccc", "", LocalDate.of(2024, 3, 11), LocalDate.of(2024, 3, 19), "aaa", "text", false,
				"A;B;C", false));
		list2.add(new Quiz(1, 4, "bbb", "", LocalDate.of(2024, 3, 11), LocalDate.of(2024, 3, 19), "aaa", "單", false,
				"A;B;C;D", false));
		list2.add(new Quiz(1, 5, "ccc", "", LocalDate.of(2024, 3, 11), LocalDate.of(2024, 3, 19), "aaa", "單", false,
				"A;B;C;D", false));
		List<Answer> list = new ArrayList<>();// answer的dao
		List<Integer> list1 = new ArrayList<>();// 各題次數
		for (int i = 1; i < 50; i++) {
			list.add(new Answer("a", "a", "a", 0, 1, i % 5 + 1, String.valueOf((char) (i % 4 + 65))));
		}
		list.add(new Answer("a", "a", "a", 0, 1, 5, "ABC"));
		answerDao.saveAll(list);
		quizDao.saveAll(list2);
		StatisticsRes a = quizService.statistics1(1);
		StatisticsRes b = quizService.statistics(1);
		System.out.println(a.getCode());
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++) {
				if (list2.get(i).getType().equals("text")) {
					continue;
				}
				System.out.printf("quid: %d time: %d\n", i + 1,
						a.getResult().get(i + 1).get(String.valueOf((char) (j % 4 + 65))));
//				System.out.println(a.getResult().get(i + 1).get(String.valueOf((char) (j % 4 + 65))));
			}
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.println(b.getResult().get(i + 1).get(String.valueOf((char) (j % 4 + 65))));
			}
		}
	}

	@Test
	public void test4() {
		List<Integer> list = Arrays.asList(1, 2, 2, 3, 1, 4, 3, 2, 5, 5, 6);
		list = Arrays.asList(1);
		System.out.println(list);
		String str = "A;B;C;D";
		String str1 = "A;B;C";
		String[] strArr = str.split(";");
		List<String> list1 = Arrays.asList(str.split(";"));
		System.out.println(list1.get(0).equalsIgnoreCase("A"));
		//
		Map<Integer, Map<String, Integer>> quizIdAndAnsCountMap = new HashMap<>();
		Map<String, Integer> innermap = new HashMap<>();
//		quizIdAndAnsCountMap.put(1, (Map<String, Integer>) new HashMap<>().put("A", 10));
		Map<String, Integer> answerCountMap = new HashMap<>();
		Map<String, QuizId> testMap = new HashMap<>();
		answerCountMap.put("A", 1);
		quizIdAndAnsCountMap.put(1, answerCountMap);
		answerCountMap.put("B", 5);
		answerCountMap.put("A", 7);
//		quizIdAndAnsCountMap.put(2, answerCountMap);
		System.out.println(quizIdAndAnsCountMap.get(1).get("A"));
		System.out.println(quizIdAndAnsCountMap.get(1).get("B"));
		System.out.println(quizIdAndAnsCountMap.get(1));
//		System.out.println(quizIdAndAnsCountMap.get(2).get("A"));
		QuizId a = new QuizId(1, 2);
		testMap.put("A", a);
		a.setQuId(3);
		a.setQuizId(4);
		testMap.put("B", a);
		System.out.println(testMap.get("A").getQuId());

	}

}
