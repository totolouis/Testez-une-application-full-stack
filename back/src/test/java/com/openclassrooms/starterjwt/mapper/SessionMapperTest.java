package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class SessionMapperTest {

    @Mock
    private TeacherService teacherService;

    @Mock
    private UserService userService;

    @InjectMocks
    private SessionMapper sessionMapper = Mappers.getMapper(SessionMapper.class);

    private SessionDto sessionDto;
    private Teacher teacher;
    private User user1;
    private User user2;
    private Session session;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sessionDto = new SessionDto();
        sessionDto.setDescription("Yoga Session");
        sessionDto.setTeacher_id(1L);
        sessionDto.setUsers(Arrays.asList(1L, 2L));

        teacher = new Teacher();
        teacher.setId(1L);

        user1 = new User();
        user1.setId(1L);

        user2 = new User();
        user2.setId(2L);

        session = new Session();
        session.setDescription("Yoga Session");
        session.setTeacher(teacher);
        session.setUsers(Arrays.asList(user1, user2));

        when(teacherService.findById(1L)).thenReturn(teacher);
        when(userService.findById(1L)).thenReturn(user1);
        when(userService.findById(2L)).thenReturn(user2);
    }

    @Test
    void testToEntity() {
        Session session = sessionMapper.toEntity(sessionDto);

        assertEquals("Yoga Session", session.getDescription());
        assertEquals(teacher, session.getTeacher());
        assertEquals(Arrays.asList(user1, user2), session.getUsers());
    }

    @Test
    void testToEntityWithSessionDtoNull() {
        Session session = sessionMapper.toEntity((SessionDto) null);
        assertNull(session);
    }

    @Test
    void testToEntityFromList() {
        List<Session> sessionList = sessionMapper.toEntity(Arrays.asList(sessionDto));

        assertEquals(1, sessionList.size());
    }

    @Test
    void testToEntityFromListNull() {
        List<Session> sessionList = sessionMapper.toEntity((List<SessionDto>) null);
        assertNull(sessionList);
    }

    @Test
    void testToDto() {
        SessionDto sessionDto = sessionMapper.toDto(session);

        assertEquals("Yoga Session", sessionDto.getDescription());
        assertEquals(1L, sessionDto.getTeacher_id());
        assertEquals(Arrays.asList(1L, 2L), sessionDto.getUsers());
    }

    @Test
    void testToDtoFromList() {
        List<SessionDto> sessionDtoList = sessionMapper.toDto(Arrays.asList(session));

        assertEquals(1, sessionDtoList.size());
    }

    @Test
    void testToDtoFromListNull() {
        List<SessionDto> sessionDtoList = sessionMapper.toDto((List<Session>) null);
        assertNull(sessionDtoList);
    }

    @Test
    void testToDtoNullSession() {
        SessionDto sessionDto = sessionMapper.toDto((Session) null);

        assertNull(sessionDto);
    }

    @Test
    void testToDtoWithNullTeacher() {
        session.setTeacher(null);

        SessionDto sessionDto = sessionMapper.toDto(session);

        assertEquals("Yoga Session", sessionDto.getDescription());
        assertNull(sessionDto.getTeacher_id());
        assertEquals(Arrays.asList(1L, 2L), sessionDto.getUsers());
    }

    @Test
    void testToDtoWithNullTeacherId() {
        teacher.setId(null);

        SessionDto sessionDto = sessionMapper.toDto(session);

        assertEquals("Yoga Session", sessionDto.getDescription());
        assertNull(sessionDto.getTeacher_id());
        assertEquals(Arrays.asList(1L, 2L), sessionDto.getUsers());
    }
}