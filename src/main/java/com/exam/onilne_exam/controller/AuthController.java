package com.exam.onilne_exam.controller;
import com.exam.onilne_exam.entity.User;
import com.exam.onilne_exam.repository.UserRepository;
import com.exam.onilne_exam.security.JwtUtil;
import org.springframework.web.bind.annotation.*;
import com.exam.onilne_exam.dto.RegisterRequestDTO;
import com.exam.onilne_exam.dto.LoginRequestDTO;
import org.springframework.http.ResponseEntity;




@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    // ================= REGISTER =================
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO dto) {

        if (userRepository.findByUsername(dto.getUsername()) != null) {
            return ResponseEntity
                    .badRequest()
                    .body("User already exists");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword()); // (plain for now)
        user.setRole(dto.getRole()); // ROLE_ADMIN / ROLE_STUDENT

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO dto) {

        User dbUser = userRepository.findByUsername(dto.getUsername());

        if (dbUser == null) {
            return ResponseEntity
                    .status(401)
                    .body("User not found");
        }

        if (!dbUser.getPassword().equals(dto.getPassword())) {
            return ResponseEntity
                    .status(401)
                    .body("Invalid password");
        }

        if (dbUser.getRole() == null) {
            return ResponseEntity
                    .status(500)
                    .body("User role not assigned");
        }

        String token = jwtUtil.generateToken(
                dbUser.getUsername(),
                dbUser.getRole()
        );

        return ResponseEntity.ok(token);
    }
}

