package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTest {

    private UserMapper userMapper;

    private UserDto userDto;
    private User user;

    @BeforeEach
    void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);

        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("john.doe@example.com");
        userDto.setLastName("Doe");
        userDto.setFirstName("John");
        userDto.setPassword("password");
        userDto.setAdmin(false);
        userDto.setCreatedAt(LocalDateTime.now());
        userDto.setUpdatedAt(LocalDateTime.now());

        user = User.builder()
                .id(1L)
                .email("john.doe@example.com")
                .lastName("Doe")
                .firstName("John")
                .password("password")
                .admin(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testToEntity() {
        User user = userMapper.toEntity(userDto);

        assertEquals(1L, user.getId());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("Doe", user.getLastName());
        assertEquals("John", user.getFirstName());
        assertEquals("password", user.getPassword());
        assertFalse(user.isAdmin());
        assertEquals(userDto.getCreatedAt(), user.getCreatedAt());
        assertEquals(userDto.getUpdatedAt(), user.getUpdatedAt());
    }

    @Test
    void testToEntityWithUserDtoNull() {
        User user = userMapper.toEntity((UserDto) null);
        assertNull(user);
    }

    @Test
    void testToEntityFromList() {
        List<User> userList = userMapper.toEntity(Arrays.asList(userDto));

        assertEquals(1, userList.size());
    }

    @Test
    void testToEntityFromListNull() {
        List<User> userList = userMapper.toEntity((List<UserDto>) null);
        assertNull(userList);
    }

    @Test
    void testToDto() {
        UserDto userDto = userMapper.toDto(user);

        assertEquals(1L, userDto.getId());
        assertEquals("john.doe@example.com", userDto.getEmail());
        assertEquals("Doe", userDto.getLastName());
        assertEquals("John", userDto.getFirstName());
        assertEquals("password", userDto.getPassword());
        assertFalse(userDto.isAdmin());
        assertEquals(user.getCreatedAt(), userDto.getCreatedAt());
        assertEquals(user.getUpdatedAt(), userDto.getUpdatedAt());
    }

    @Test
    void testToDtoFromList() {
        List<UserDto> userDtoList = userMapper.toDto(Arrays.asList(user));

        assertEquals(1, userDtoList.size());
    }

    @Test
    void testToDtoFromListNull() {
        List<UserDto> userDtoList = userMapper.toDto((List<User>) null);
        assertNull(userDtoList);
    }

    @Test
    void testToDtoNullUser() {
        UserDto userDto = userMapper.toDto((User) null);
        assertNull(userDto);
    }
}