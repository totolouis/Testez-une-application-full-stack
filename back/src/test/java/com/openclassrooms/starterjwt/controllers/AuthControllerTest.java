package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import com.openclassrooms.starterjwt.payload.response.MessageResponse;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthController authController;

    private User user;
    private UserDetailsImpl userDetails;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        user = new User("john.doe@example.com", "Doe", "John", "password", false);
        user.setId(1L);

        userDetails = new UserDetailsImpl(1L, "john.doe@example.com", "Doe", "John", true, "password");

        authentication = mock(Authentication.class);
    }

    @Test
    void authenticateUser_ShouldReturnJwtResponse_WhenCredentialsAreValid() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("john.doe@example.com");
        loginRequest.setPassword("password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("jwt-token");
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));

        ResponseEntity<?> response = authController.authenticateUser(loginRequest);

        assertEquals(200, response.getStatusCodeValue());
        JwtResponse jwtResponse = (JwtResponse) response.getBody();
        assertEquals("jwt-token", jwtResponse.getToken());
        assertEquals(user.getId(), jwtResponse.getId());
    }

    @Test
    void registerUser_ShouldReturnSuccessMessage_WhenUserIsRegistered() {
        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setEmail("john.doe@example.com");
        signUpRequest.setLastName("Doe");
        signUpRequest.setFirstName("John");
        signUpRequest.setPassword("password");

        when(userRepository.existsByEmail("john.doe@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encoded-password");

        ResponseEntity<?> response = authController.registerUser(signUpRequest);

        assertEquals(200, response.getStatusCodeValue());
        MessageResponse messageResponse = (MessageResponse) response.getBody();
        assertEquals("User registered successfully!", messageResponse.getMessage());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_ShouldReturnErrorMessage_WhenEmailIsAlreadyTaken() {
        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setEmail("john.doe@example.com");
        signUpRequest.setLastName("Doe");
        signUpRequest.setFirstName("John");
        signUpRequest.setPassword("password");

        when(userRepository.existsByEmail("john.doe@example.com")).thenReturn(true);

        ResponseEntity<?> response = authController.registerUser(signUpRequest);

        assertEquals(400, response.getStatusCodeValue());
        MessageResponse messageResponse = (MessageResponse) response.getBody();
        assertEquals("Error: Email is already taken!", messageResponse.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }
}