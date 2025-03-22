package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TeacherMapperTest {

    private TeacherMapper teacherMapper;

    private TeacherDto teacherDto;
    private Teacher teacher;

    @BeforeEach
    void setUp() {
        teacherMapper = Mappers.getMapper(TeacherMapper.class);

        teacherDto = new TeacherDto();
        teacherDto.setId(1L);
        teacherDto.setFirstName("John");
        teacherDto.setLastName("Doe");
        teacherDto.setCreatedAt(LocalDateTime.now());
        teacherDto.setUpdatedAt(LocalDateTime.now());

        teacher = Teacher.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testToEntity() {
        Teacher teacher = teacherMapper.toEntity(teacherDto);

        assertEquals(1L, teacher.getId());
        assertEquals("John", teacher.getFirstName());
        assertEquals("Doe", teacher.getLastName());
        assertEquals(teacherDto.getCreatedAt(), teacher.getCreatedAt());
        assertEquals(teacherDto.getUpdatedAt(), teacher.getUpdatedAt());
    }

    @Test
    void testToEntityWithTeacherDtoNull() {
        Teacher teacher = teacherMapper.toEntity((TeacherDto) null);
        assertNull(teacher);
    }

    @Test
    void testToEntityFromList() {
        List<Teacher> teacherList = teacherMapper.toEntity(Arrays.asList(teacherDto));

        assertEquals(1, teacherList.size());
    }

    @Test
    void testToEntityFromListNull() {
        List<Teacher> teacherList = teacherMapper.toEntity((List<TeacherDto>) null);
        assertNull(teacherList);
    }

    @Test
    void testToDto() {
        TeacherDto teacherDto = teacherMapper.toDto(teacher);

        assertEquals(1L, teacherDto.getId());
        assertEquals("John", teacherDto.getFirstName());
        assertEquals("Doe", teacherDto.getLastName());
        assertEquals(teacher.getCreatedAt(), teacherDto.getCreatedAt());
        assertEquals(teacher.getUpdatedAt(), teacherDto.getUpdatedAt());
    }

    @Test
    void testToDtoFromList() {
        List<TeacherDto> teacherDtoList = teacherMapper.toDto(Arrays.asList(teacher));

        assertEquals(1, teacherDtoList.size());
    }

    @Test
    void testToDtoFromListNull() {
        List<TeacherDto> teacherDtoList = teacherMapper.toDto((List<Teacher>) null);
        assertNull(teacherDtoList);
    }

    @Test
    void testToDtoNullTeacher() {
        TeacherDto teacherDto = teacherMapper.toDto((Teacher) null);
        assertNull(teacherDto);
    }
}