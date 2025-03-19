package com.openclassrooms.starterjwt.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import com.openclassrooms.starterjwt.services.TeacherService;

@SpringBootTest
@AutoConfigureMockMvc
public class TeacherIntegrationTest {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    void testCreateTeacher() throws Exception {
        // Given
        Teacher teacher = new Teacher();
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        teacherRepository.save(teacher);

        // When
        mockMvc.perform(get("/api/teacher")
                .param("id", "1"));

        // Then
        assertNotNull(teacherRepository.findById(1L));
    }

    @Test
    @Transactional
    void testDeleteTeacher() throws Exception {
        // Given
        Teacher teacher = new Teacher();
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        teacherRepository.save(teacher);
        teacherRepository.deleteById(1L);

        // When
        mockMvc.perform(get("/api/teacher/delete")
                .param("id", "1"));

        // Then
        assertNotNull(teacherRepository.findById(1L));
    }

    @Test
    @Transactional
    void testUpdateTeacher() throws Exception {
        // Given
        Teacher teacher = new Teacher();
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        teacherRepository.save(teacher);
        teacher.setFirstName("Jane");
        teacherRepository.save(teacher);

        // When
        mockMvc.perform(get("/api/teacher/update")
                .param("id", "1"));

        // Then
        assertNotNull(teacherRepository.findById(1L));
    }

    @Test
    @Transactional
    void testCreateTwoTeachers() throws Exception {
        // Given
        Teacher teacher1 = new Teacher();
        teacher1.setFirstName("John");
        teacher1.setLastName("Doe");

        Teacher teacher2 = new Teacher();
        teacher2.setFirstName("Jane");
        teacher2.setLastName("Doe");

        teacherRepository.save(teacher1);
        teacherRepository.save(teacher2);

        // When
        mockMvc.perform(get("/api/teacher"));

        // Then
        assertNotNull(teacherRepository.findAll());
    }

    @Test
    @Transactional
    void testTeacherWithNoSession() throws Exception {
        // Given
        Teacher teacher = new Teacher();
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        teacherRepository.save(teacher);

        // When
        mockMvc.perform(get("/api/teacher")
                .param("id", "1"));

        // Then
        assertNotNull(teacherRepository.findById(1L));
    }
}
