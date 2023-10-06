package dev.akiyaaa.moviesreview.controllers;

import dev.akiyaaa.moviesreview.models.DTO.request.LoginRequest;
import dev.akiyaaa.moviesreview.models.DTO.request.SignupRequest;
import dev.akiyaaa.moviesreview.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
//@CrossOrigin(origins = "http://127.0.0.1:5173", methods = {RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST}, allowedHeaders = "*")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(authService.authenticateUser(loginRequest), HttpStatus.OK);
    }
    
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest){
    return new ResponseEntity<>(authService.registerUser(signupRequest), HttpStatus.CREATED);
    }
}
