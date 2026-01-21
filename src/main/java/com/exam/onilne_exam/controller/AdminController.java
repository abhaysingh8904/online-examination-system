package com.exam.onilne_exam.controller;
import com.exam.onilne_exam.entity.Question;
import com.exam.onilne_exam.repository.QuestionRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final QuestionRepository repo;

    public AdminController(QuestionRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/add-question")
    public Question addQuestion(@RequestBody Question q) {
        return repo.save(q);
    }
}
