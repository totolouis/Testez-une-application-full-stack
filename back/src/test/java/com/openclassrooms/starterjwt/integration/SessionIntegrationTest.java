package com.openclassrooms.starterjwt.integration;

import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class SessionIntegrationTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void testCreateSession() throws Exception {
        // Given
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        teacherRepository.save(teacher);

        User user = new User();
        user.setId(1L);
        user.setPassword("test");
        user.setFirstName("test");
        user.setLastName("test");
        userRepository.save(user);


        Session session = new Session();
        session.setName("test");
        session.setDate(new Date());
        session.setDescription("test");
        session.setTeacher(teacher);
        session.setUsers(Arrays.asList(user));
        sessionRepository.save(session);

        // When
        mockMvc.perform(get("/api/session")
                .param("id", "1"));

        // Then
        assertNotNull(sessionRepository.findById(1L));
    }

    @Test
    @Transactional
    public void testThrowExceptionWhenUserIsNull() {
        // Given
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        teacherRepository.save(teacher);

        Session session = new Session();
        session.setName("test");
        session.setDate(new Date());
        session.setDescription("test");
        session.setTeacher(teacher);
        session.setUsers(null);
        sessionRepository.save(session);

        assertThrows(NotFoundException.class, () -> sessionService.participate(1L, 999L));
        ;

    }

    @Test
    @Transactional
    public void testThrowExceptionWhenSessionIsNull() {
        // Given
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        teacherRepository.save(teacher);

        User user = new User();
        user.setId(1L);
        user.setPassword("test");
        user.setFirstName("test");
        user.setLastName("test");
        userRepository.save(user);

        assertThrows(NotFoundException.class, () -> sessionService.participate(999L, 1L));
        ;

    }
}
