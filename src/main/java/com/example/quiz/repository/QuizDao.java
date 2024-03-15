package com.example.quiz.repository;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.quiz.entity.Quiz;
import com.example.quiz.entity.QuizId;

@Repository
@Transactional // delete要用的
public interface QuizDao extends JpaRepository<Quiz, QuizId> {

	public boolean existsByQuizId(int quizId);

	public List<Quiz> findByQuizNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(String quizName,
			LocalDate startDate, LocalDate endDate);

	public List<Quiz> findAllByQuizIdInAndPublishFalseOrQuizIdInAndStartDateAfter(List<Integer> quizIds,
			List<Integer> quizIds1, LocalDate now);

	public void deleteAllByQuizIdInAndPublishFalseOrStartDateAfter(List<Integer> quizIds, LocalDate now);

	public void deleteByQuizIdAndQuIdInAndPublishFalseOrQuizIdAndQuIdInAndStartDateAfter(int quizId, List<Integer> quIds,
			 int quizId1, List<Integer> quIds1,
			LocalDate now);
	
	public List<Quiz> findByQuizIdAndPublishFalseOrQuizIdAndStartDateAfterOrderByQuId(int quizId, int quizId2, LocalDate now);
	
	public void deleteByQuizId(int quizId);
	
	public boolean existsByQuizIdAndPublishFalseOrQuizIdAndStartDateAfter(int quizId, int quizId2, LocalDate now);
	
	public List<Quiz> findByQuizNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqualAndPublishTrue(String quizName,
			LocalDate startDate, LocalDate endDate);
//	撈欄位名字要nativequery=true
	@Query(value = "select qu_id from quiz where quiz_id = ?1 and neccesary = true", nativeQuery = true)
	public List<Integer> findQuIdsByQuizIdAndNecessaryTrue(int quizId);
	
	public List<Quiz> findByQuizId(int quizId);
	
	public List<Quiz> findByQuizIdAndTypeNot(int quizId, String type);
}
