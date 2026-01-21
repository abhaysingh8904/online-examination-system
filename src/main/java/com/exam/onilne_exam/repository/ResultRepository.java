package com.exam.onilne_exam.repository;
import com.exam.onilne_exam.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findByUsername(String username);
}
