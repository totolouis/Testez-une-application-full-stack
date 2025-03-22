package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherControllerTest {

    @Mock
    private TeacherService teacherService;

    @Mock
    private TeacherMapper teacherMapper;

    @InjectMocks
    private TeacherController teacherController;

    private Teacher teacher;
    private TeacherDto teacherDto;

    @BeforeEach
    void setUp() {
        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        teacherDto = new TeacherDto();
        teacherDto.setId(1L);
        teacherDto.setFirstName("John");
        teacherDto.setLastName("Doe");
    }

    @Test
    void findById_ShouldReturnTeacher_WhenTeacherExists() {
        when(teacherService.findById(1L)).thenReturn(teacher);
        when(teacherMapper.toDto(teacher)).thenReturn(teacherDto);

        ResponseEntity<?> response = teacherController.findById("1");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(teacher.getId(), ((TeacherDto) Objects.requireNonNull(response.getBody())).getId());
    }

    @Test
    void findById_ShouldReturnNotFound_WhenTeacherDoesNotExist() {
        when(teacherService.findById(1L)).thenReturn(null);

        ResponseEntity<?> response = teacherController.findById("1");

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void findById_ShouldReturnBadRequest_WhenIdIsInvalid() {
        ResponseEntity<?> response = teacherController.findById("invalid");

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void findAll_ShouldReturnListOfTeachers() {
        List<Teacher> teachers = Arrays.asList(teacher);
        when(teacherService.findAll()).thenReturn(teachers);

        when(teacherMapper.toDto(teachers)).thenReturn(Arrays.asList(teacherDto));

        ResponseEntity<?> response = teacherController.findAll();

        assertEquals(200, response.getStatusCodeValue());
    }
}
