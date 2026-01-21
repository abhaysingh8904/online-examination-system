package com.exam.onilne_exam.service;
import com.exam.onilne_exam.dto.AnswerDto;
import com.exam.onilne_exam.entity.Question;
import com.exam.onilne_exam.entity.Result;
import com.exam.onilne_exam.repository.QuestionRepository;
import com.exam.onilne_exam.repository.ResultRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResultService {

    private final QuestionRepository questionRepo;
    private final ResultRepository resultRepo;

    public ResultService(QuestionRepository questionRepo, ResultRepository resultRepo) {
        this.questionRepo = questionRepo;
        this.resultRepo = resultRepo;
    }

    public Result calculateAndSave(String username, List<AnswerDto> answers) {

        int correct = 0;

        for (AnswerDto a : answers) {
            Question q = questionRepo.findById(a.getQuestionId()).orElse(null);
            if (q != null && q.getCorrectAnswer().equals(a.getSelectedAnswer())) {
                correct++;
            }
        }

        Result r = new Result();
        r.setUsername(username);
        r.setTotalQuestions(answers.size());
        r.setCorrectAnswers(correct);
        r.setScore(correct);
        r.setSubmittedAt(LocalDateTime.now());

        return resultRepo.save(r);
    }
}
