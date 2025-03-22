package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionControllerTest {

    @Mock
    private SessionService sessionService;

    @Mock
    private SessionMapper sessionMapper;

    @InjectMocks
    private SessionController sessionController;

    private Session session;
    private SessionDto sessionDto;

    @BeforeEach
    void setUp() {
        session = new Session();
        session.setId(1L);

        sessionDto = new SessionDto();
    }

    @Test
    void findById_ShouldReturnSession_WhenSessionExists() {
        when(sessionService.getById(1L)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        ResponseEntity<?> response = sessionController.findById("1");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sessionDto, response.getBody());
    }

    @Test
    void findById_ShouldReturnNotFound_WhenSessionDoesNotExist() {
        when(sessionService.getById(1L)).thenReturn(null);

        ResponseEntity<?> response = sessionController.findById("1");

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void findById_ShouldReturnBadRequest_WhenIdIsInvalid() {
        ResponseEntity<?> response = sessionController.findById("invalid");

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void findAll_ShouldReturnListOfSessions() {
        List<Session> sessions = Arrays.asList(session);
        when(sessionService.findAll()).thenReturn(sessions);
        when(sessionMapper.toDto(sessions)).thenReturn(Arrays.asList(sessionDto));

        ResponseEntity<?> response = sessionController.findAll();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(Arrays.asList(sessionDto), response.getBody());
    }

    @Test
    void create_ShouldReturnCreatedSession() {
        when(sessionMapper.toEntity(sessionDto)).thenReturn(session);
        when(sessionService.create(session)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        ResponseEntity<?> response = sessionController.create(sessionDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sessionDto, response.getBody());
    }

    @Test
    void update_ShouldReturnUpdatedSession() {
        when(sessionMapper.toEntity(sessionDto)).thenReturn(session);
        when(sessionService.update(1L, session)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        ResponseEntity<?> response = sessionController.update("1", sessionDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sessionDto, response.getBody());
    }

    @Test
    void update_ShouldReturnBadRequest_WhenIdIsInvalid() {
        ResponseEntity<?> response = sessionController.update("invalid", sessionDto);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void save_ShouldReturnOk_WhenSessionExists() {
        when(sessionService.getById(1L)).thenReturn(session);

        ResponseEntity<?> response = sessionController.save("1");

        assertEquals(200, response.getStatusCodeValue());
        verify(sessionService, times(1)).delete(1L);
    }

    @Test
    void save_ShouldReturnNotFound_WhenSessionDoesNotExist() {
        when(sessionService.getById(1L)).thenReturn(null);

        ResponseEntity<?> response = sessionController.save("1");

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void save_ShouldReturnBadRequest_WhenIdIsInvalid() {
        ResponseEntity<?> response = sessionController.save("invalid");

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void participate_ShouldReturnOk_WhenValidIds() {
        ResponseEntity<?> response = sessionController.participate("1", "2");

        assertEquals(200, response.getStatusCodeValue());
        verify(sessionService, times(1)).participate(1L, 2L);
    }

    @Test
    void participate_ShouldReturnBadRequest_WhenIdsAreInvalid() {
        ResponseEntity<?> response = sessionController.participate("invalid", "2");

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void noLongerParticipate_ShouldReturnOk_WhenValidIds() {
        ResponseEntity<?> response = sessionController.noLongerParticipate("1", "2");

        assertEquals(200, response.getStatusCodeValue());
        verify(sessionService, times(1)).noLongerParticipate(1L, 2L);
    }

    @Test
    void noLongerParticipate_ShouldReturnBadRequest_WhenIdsAreInvalid() {
        ResponseEntity<?> response = sessionController.noLongerParticipate("invalid", "2");

        assertEquals(400, response.getStatusCodeValue());
    }
}
