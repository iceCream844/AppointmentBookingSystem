package com.swc.appointment_booking.service.impl;

import com.swc.appointment_booking.converter.UserMapper;
import com.swc.appointment_booking.dto.request.UserRequestDTO;
import com.swc.appointment_booking.dto.response.AppointmentSummaryDTO;
import com.swc.appointment_booking.dto.response.UserResponseDTO;
import com.swc.appointment_booking.entity.Appointment;
import com.swc.appointment_booking.entity.User;
import com.swc.appointment_booking.enums.AppointmentStatus;
import com.swc.appointment_booking.enums.UserRole;
import com.swc.appointment_booking.exception.ResourceNotFoundException;
import com.swc.appointment_booking.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private Authentication authentication;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testCreateUser() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setName("John");
        dto.setEmail("john@email.com");
        dto.setPassword("123456");

        User savedUser = new User();
        savedUser.setName("John");

        UserResponseDTO response = new UserResponseDTO();
        response.setName("John");

        when(passwordEncoder.encode("123456"))
                .thenReturn("encodedPassword");

        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        when(userMapper.mapToResponse(savedUser))
                .thenReturn(response);

        UserResponseDTO result = userService.create(dto);

        assertEquals("John", result.getName());
        verify(passwordEncoder).encode("123456");
        verify(userRepository).save(any(User.class));
        verify(userMapper).mapToResponse(savedUser);
    }

    @Test
    void testGetUserById() {
        Long target = 1L;

        User foundUser = new User();
        foundUser.setId(1L);
        foundUser.setName("John");
        foundUser.setEmail("john@email.com");
        foundUser.setRole(UserRole.USER);

        UserResponseDTO response = new UserResponseDTO();
        response.setId(1L);
        response.setName("John");
        response.setEmail("john@email.com");
        response.setRole("USER");

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(foundUser));

        when(userMapper.mapToResponse(foundUser))
                .thenReturn(response);

        UserResponseDTO result = userService.findById(target);

        assertEquals(1L, result.getId());
        assertEquals("John", result.getName());
        assertEquals("john@email.com", result.getEmail());
        assertEquals("USER", result.getRole());
        verify(userRepository).findById(1L);
        verify(userMapper).mapToResponse(foundUser);
    }


    @Test
    void testGetUserByIdWithAppointment() {
        Long target = 1L;

        User foundUser = new User();
        foundUser.setId(1L);
        foundUser.setName("John");
        foundUser.setEmail("john@email.com");
        foundUser.setRole(UserRole.USER);

        Appointment appointment = new Appointment();
        appointment.setUser(foundUser);
        appointment.setStatus(AppointmentStatus.APPROVED);
        appointment.setId(1L);
        appointment.setAppointmentTime(LocalDateTime.of(2026, 8, 3, 14, 30));

        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(appointment);

        foundUser.setAppointments(appointmentList);

        UserResponseDTO response = new UserResponseDTO();
        response.setId(1L);
        response.setName("John");
        response.setEmail("john@email.com");
        response.setRole("USER");
        List<AppointmentSummaryDTO> summaryDTOS = new ArrayList<>();
        AppointmentSummaryDTO summaryDTO = new AppointmentSummaryDTO();
        summaryDTO.setId(1L);
        summaryDTO.setAppointmentTime(LocalDateTime.of(2026, 8, 3, 14, 30));
        summaryDTO.setStatus(String.valueOf(AppointmentStatus.APPROVED));

        summaryDTOS.add(summaryDTO);

        response.setAppointments(summaryDTOS);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(foundUser));

        when(userMapper.mapToResponse(foundUser))
                .thenReturn(response);

        UserResponseDTO result = userService.findById(target);

        assertEquals(1, result.getAppointments().size());

        AppointmentSummaryDTO summary = result.getAppointments().get(0);

        assertEquals(1L, summary.getId());
        assertEquals(AppointmentStatus.APPROVED.toString(), summary.getStatus());
        assertEquals(
                LocalDateTime.of(2026, 8, 3, 14, 30),
                summary.getAppointmentTime()
        );
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.findById(1L));

        verify(userRepository).findById(1L);
    }


    @Test
    void testUpdateUserById() {
        Long target = 1L;

        UserRequestDTO dto = new UserRequestDTO();
        dto.setName("Dick");
        dto.setEmail("john@email.com");
        dto.setRole(String.valueOf(UserRole.ADMIN));
        dto.setPassword("123456");

        User foundUser = new User();
        foundUser.setId(1L);
        foundUser.setName("John");
        foundUser.setEmail("john@email.com");
        foundUser.setRole(UserRole.USER);


        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName("Dick");
        savedUser.setEmail("john@email.com");
        savedUser.setRole(UserRole.ADMIN);

        UserResponseDTO response = new UserResponseDTO();
        response.setId(1L);
        response.setName("Dick");
        response.setEmail("john@email.com");
        response.setRole(String.valueOf(UserRole.ADMIN));

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(foundUser));

        when(passwordEncoder.encode("123456"))
                .thenReturn("encodedPassword");

        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        when(userMapper.mapToResponse(savedUser))
                .thenReturn(response);

        UserResponseDTO result = userService.update(target,dto);

        assertEquals("Dick", result.getName());
        assertEquals("ADMIN", result.getRole());
        verify(userRepository).findById(1L);
        verify(passwordEncoder).encode("123456");
        verify(userRepository).save(any(User.class));
        verify(userMapper).mapToResponse(savedUser);
    }

    @Test
    void shouldThrowExceptionWhenUpdateUserNotFound() {

        Long target = 1L;

        UserRequestDTO dto = new UserRequestDTO();
        dto.setName("Dick");
        dto.setEmail("john@email.com");
        dto.setRole("ADMIN");
        dto.setPassword("123456");

        when(userRepository.findById(target))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.update(target, dto));

        verify(userRepository).findById(target);
    }

    @Test
    void testDeleteUserById() {
        Long target = 1L;

        User foundUser = new User();
        foundUser.setId(1L);
        foundUser.setName("John");
        foundUser.setEmail("john@email.com");
        foundUser.setRole(UserRole.USER);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(foundUser));

        userService.deleteById(target);

        verify(userRepository).findById(1L);
        verify(userRepository).delete(foundUser);
    }

    @Test
    void shouldThrowExceptionWhenDeleteUserNotFound() {
        Long target = 1L;

        when(userRepository.findById(target))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.deleteById(target));

        verify(userRepository).findById(target);

    }

    @Test
    void shouldGetCurrentUser() {

        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(authentication.getName())
                .thenReturn("john@email.com");

        User user = new User();
        user.setName("John");
        user.setEmail("john@email.com");
        user.setRole(UserRole.USER);

        when(userRepository.findByEmail("john@email.com"))
                .thenReturn(Optional.of(user));

        UserResponseDTO response = new UserResponseDTO();
        response.setName("John");
        response.setEmail("john@email.com");
        response.setRole("USER");

        when(userMapper.mapToResponse(user))
                .thenReturn(response);

        UserResponseDTO result = userService.getCurrentUser();

        assertEquals("John", result.getName());

        verify(userRepository).findByEmail("john@email.com");
        verify(userMapper).mapToResponse(user);
    }

    @Test
    void shouldReturnAllUsers() {
        Pageable pageable = PageRequest.of(0, 10);

        User user = new User();
        user.setName("John");

        Page<User> page = new PageImpl<>(List.of(user));

        UserResponseDTO response = new UserResponseDTO();
        response.setName("John");

        when(userRepository.findAll(pageable))
                .thenReturn(page);

        when(userMapper.mapToResponse(user))
                .thenReturn(response);

        Page<UserResponseDTO> result = userService.findAll(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("John", result.getContent().get(0).getName());

        verify(userRepository).findAll(pageable);
    }

}