package com.exam.onilne_exam.repository;
import com.exam.onilne_exam.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {}
