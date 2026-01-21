package com.exam.onilne_exam.controller;
import com.exam.onilne_exam.dto.AnswerDto;
import com.exam.onilne_exam.entity.Question;
import com.exam.onilne_exam.entity.Result;
import com.exam.onilne_exam.repository.QuestionRepository;
import com.exam.onilne_exam.repository.ResultRepository;
import com.exam.onilne_exam.service.ResultService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final QuestionRepository questionRepo;
    private final ResultService resultService;
    private final ResultRepository resultRepo;

    public StudentController(QuestionRepository questionRepo,
                             ResultService resultService,
                             ResultRepository resultRepo) {
        this.questionRepo = questionRepo;
        this.resultService = resultService;
        this.resultRepo = resultRepo;
    }

    @GetMapping("/questions")
    public List<Question> getQuestions() {
        return questionRepo.findAll();
    }

    @PostMapping("/submit")
    public Result submit(Authentication auth,
                         @RequestBody List<AnswerDto> answers) {
        return resultService.calculateAndSave(auth.getName(), answers);
    }

    @GetMapping("/result")
    public List<Result> getResult(Authentication auth) {
        return resultRepo.findByUsername(auth.getName());
    }
}
