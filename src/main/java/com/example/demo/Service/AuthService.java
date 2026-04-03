package com.example.demo.Service;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;

public interface AuthService {
	
	AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);

}
