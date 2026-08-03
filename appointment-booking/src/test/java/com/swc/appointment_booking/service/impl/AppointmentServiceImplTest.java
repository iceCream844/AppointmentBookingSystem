package com.swc.appointment_booking.service.impl;

import com.swc.appointment_booking.converter.AppointmentMapper;
import com.swc.appointment_booking.dto.request.AppointmentRequestDTO;
import com.swc.appointment_booking.dto.response.AppointmentResponseDTO;
import com.swc.appointment_booking.entity.Appointment;
import com.swc.appointment_booking.entity.User;
import com.swc.appointment_booking.enums.AppointmentStatus;
import com.swc.appointment_booking.enums.UserRole;
import com.swc.appointment_booking.exception.ResourceNotFoundException;
import com.swc.appointment_booking.repository.AppointmentRepository;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @Mock
    private Authentication authentication;

    @Mock
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testCreateAppointment() {
        AppointmentRequestDTO dto = new AppointmentRequestDTO();
        dto.setAppointmentTime(LocalDateTime.of(2026, 8, 3, 14, 30));
        dto.setStatus(AppointmentStatus.APPROVED.toString());

        User currentUser = new User();
        currentUser.setId(1L);
        currentUser.setName("John");
        currentUser.setEmail("john@email.com");
        currentUser.setRole(UserRole.USER);

        Appointment savedAppointment = new Appointment();
        savedAppointment.setId(1L);
        savedAppointment.setUser(currentUser);
        savedAppointment.setStatus(AppointmentStatus.APPROVED);
        savedAppointment.setAppointmentTime(LocalDateTime.of(2026, 8, 3, 14, 30));

        AppointmentResponseDTO response = new AppointmentResponseDTO();
        response.setAppointmentTime(LocalDateTime.of(2026, 8, 3, 14, 30));
        response.setStatus(AppointmentStatus.APPROVED.toString());
        response.setUserId(currentUser.getId());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(authentication.getName())
                .thenReturn("john@email.com");

        when(appointmentRepository.save(any(Appointment.class)))
                .thenReturn(savedAppointment);

        when(userRepository.findByEmail("john@email.com"))
                .thenReturn(Optional.of(currentUser));

        when(appointmentMapper.mapToResponse(savedAppointment))
                .thenReturn(response);

        AppointmentResponseDTO result = appointmentService.create(dto);

        assertEquals(1L, result.getUserId());
        assertEquals(AppointmentStatus.APPROVED.toString(), result.getStatus());
        assertEquals(LocalDateTime.of(2026, 8, 3, 14, 30), result.getAppointmentTime());
        verify(userRepository).findByEmail("john@email.com");
        verify(appointmentRepository).save(any(Appointment.class));
        verify(appointmentMapper).mapToResponse(savedAppointment);
    }

    @Test
    void testGetAppointmentById() {
        Long target = 1L;

        User foundUser = new User();
        foundUser.setId(1L);
        foundUser.setName("John");
        foundUser.setEmail("john@email.com");
        foundUser.setRole(UserRole.USER);

        Appointment foundAppointment = new Appointment();
        foundAppointment.setId(1L);
        foundAppointment.setUser(foundUser);
        foundAppointment.setStatus(AppointmentStatus.APPROVED);
        foundAppointment.setAppointmentTime(LocalDateTime.of(2026, 8, 3, 14, 30));

        AppointmentResponseDTO response = new AppointmentResponseDTO();
        response.setId(1L);
        response.setUserName("John");
        response.setUserEmail("john@email.com");
        response.setStatus(AppointmentStatus.APPROVED.toString());
        response.setAppointmentTime(LocalDateTime.of(2026, 8, 3, 14, 30));
        response.setUserId(1L);

        when(appointmentRepository.findById(1L))
                .thenReturn(Optional.of(foundAppointment));


        when(appointmentMapper.mapToResponse(foundAppointment))
                .thenReturn(response);

        AppointmentResponseDTO result = appointmentService.findById(target);

        assertEquals(1L, result.getId());
        assertEquals("John", result.getUserName());
        assertEquals("john@email.com", result.getUserEmail());
        assertEquals("APPROVED", result.getStatus());
        verify(appointmentRepository).findById(1L);
        verify(appointmentMapper).mapToResponse(foundAppointment);
    }

    @Test
    void shouldThrowExceptionWhenAppointmentNotFound() {

        when(appointmentRepository.findById(1L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> appointmentService.findById(1L)
        );

        assertEquals("Appointment not found with ID:1", ex.getMessage());

        verify(appointmentRepository).findById(1L);
    }

    @Test
    void shouldReturnAllAppointments() {

        Pageable pageable = PageRequest.of(0, 10);

        User user = new User();
        user.setId(1L);
        user.setName("John");

        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setUser(user);
        appointment.setStatus(AppointmentStatus.APPROVED);

        Page<Appointment> page =
                new PageImpl<>(List.of(appointment));

        AppointmentResponseDTO response =
                new AppointmentResponseDTO();

        response.setId(1L);
        response.setUserId(1L);

        when(appointmentRepository.findAll(pageable))
                .thenReturn(page);

        when(appointmentMapper.mapToResponse(appointment))
                .thenReturn(response);

        Page<AppointmentResponseDTO> result =
                appointmentService.findAll(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(1L,
                result.getContent().get(0).getUserId());

        verify(appointmentRepository).findAll(pageable);
    }

    @Test
    void shouldUpdateAppointmentSuccessfully() {

        AppointmentRequestDTO dto = new AppointmentRequestDTO();
        dto.setStatus("APPROVED");
        dto.setAppointmentTime(
                LocalDateTime.of(2026,8,3,14,30));

        User currentUser = new User();
        currentUser.setId(1L);
        currentUser.setEmail("john@email.com");
        currentUser.setRole(UserRole.USER);

        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setUser(currentUser);

        AppointmentResponseDTO response =
                new AppointmentResponseDTO();
        response.setId(1L);
        response.setStatus("APPROVED");

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        when(authentication.getName())
                .thenReturn("john@email.com");

        when(userRepository.findByEmail("john@email.com"))
                .thenReturn(Optional.of(currentUser));

        when(appointmentRepository.findById(1L))
                .thenReturn(Optional.of(appointment));

        when(appointmentRepository.save(any()))
                .thenReturn(appointment);

        when(appointmentMapper.mapToResponse(appointment))
                .thenReturn(response);

        AppointmentResponseDTO result =
                appointmentService.update(1L,dto);

        assertEquals("APPROVED",result.getStatus());

        verify(appointmentRepository).save(any());
    }

    @Test
    void shouldThrowExceptionWhenUpdateAppointmentNotFound() {

        AppointmentRequestDTO dto =
                new AppointmentRequestDTO();

        when(appointmentRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                ()->appointmentService.update(1L,dto));

        verify(appointmentRepository)
                .findById(1L);
    }

    @Test
    void shouldThrowForbiddenExceptionWhenUpdatingOthersAppointment() {

        AppointmentRequestDTO dto =
                new AppointmentRequestDTO();

        User loginUser = new User();
        loginUser.setId(1L);
        loginUser.setEmail("john@email.com");
        loginUser.setRole(UserRole.USER);

        User owner = new User();
        owner.setId(2L);

        Appointment appointment =
                new Appointment();

        appointment.setUser(owner);

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        when(authentication.getName())
                .thenReturn("john@email.com");

        when(userRepository.findByEmail("john@email.com"))
                .thenReturn(Optional.of(loginUser));

        when(appointmentRepository.findById(1L))
                .thenReturn(Optional.of(appointment));

        assertThrows(
                com.swc.appointment_booking.exception.ForbiddenException.class,
                ()->appointmentService.update(1L,dto));
    }

    @Test
    void shouldAllowAdminUpdateAnyAppointment() {

        AppointmentRequestDTO dto =
                new AppointmentRequestDTO();

        dto.setStatus("APPROVED");

        User admin = new User();
        admin.setId(99L);
        admin.setEmail("admin@email.com");
        admin.setRole(UserRole.ADMIN);

        User owner = new User();
        owner.setId(1L);

        Appointment appointment =
                new Appointment();

        appointment.setUser(owner);

        AppointmentResponseDTO response =
                new AppointmentResponseDTO();

        response.setStatus("APPROVED");

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        when(authentication.getName())
                .thenReturn("admin@email.com");

        when(userRepository.findByEmail("admin@email.com"))
                .thenReturn(Optional.of(admin));

        when(appointmentRepository.findById(1L))
                .thenReturn(Optional.of(appointment));

        when(appointmentRepository.save(any()))
                .thenReturn(appointment);

        when(appointmentMapper.mapToResponse(appointment))
                .thenReturn(response);

        AppointmentResponseDTO result =
                appointmentService.update(1L,dto);

        assertEquals("APPROVED",
                result.getStatus());
    }

    @Test
    void shouldDeleteAppointmentSuccessfully() {

        User user = new User();
        user.setId(1L);
        user.setEmail("john@email.com");
        user.setRole(UserRole.USER);

        Appointment appointment =
                new Appointment();

        appointment.setUser(user);

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        when(authentication.getName())
                .thenReturn("john@email.com");

        when(userRepository.findByEmail("john@email.com"))
                .thenReturn(Optional.of(user));

        when(appointmentRepository.findById(1L))
                .thenReturn(Optional.of(appointment));

        appointmentService.deleteById(1L);

        verify(appointmentRepository)
                .delete(appointment);
    }

    @Test
    void shouldThrowExceptionWhenDeleteAppointmentNotFound() {

        when(appointmentRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                ()->appointmentService.deleteById(1L));
    }

    @Test
    void shouldThrowForbiddenExceptionWhenDeleteOthersAppointment() {

        User loginUser = new User();
        loginUser.setId(1L);
        loginUser.setEmail("john@email.com");
        loginUser.setRole(UserRole.USER);

        User owner = new User();
        owner.setId(2L);

        Appointment appointment =
                new Appointment();

        appointment.setUser(owner);

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        when(authentication.getName())
                .thenReturn("john@email.com");

        when(userRepository.findByEmail("john@email.com"))
                .thenReturn(Optional.of(loginUser));

        when(appointmentRepository.findById(1L))
                .thenReturn(Optional.of(appointment));

        assertThrows(
                com.swc.appointment_booking.exception.ForbiddenException.class,
                ()->appointmentService.deleteById(1L));
    }

    @Test
    void shouldFindAppointmentByEmail() {

        Appointment appointment =
                new Appointment();

        AppointmentResponseDTO response =
                new AppointmentResponseDTO();

        when(appointmentRepository.findByUserEmail("john@email.com"))
                .thenReturn(List.of(appointment));

        when(appointmentMapper.mapToResponse(appointment))
                .thenReturn(response);

        List<AppointmentResponseDTO> result =
                appointmentService.findByUser(
                        "john@email.com",null);

        assertEquals(1,result.size());
    }

    @Test
    void shouldFindAppointmentByName() {

        Appointment appointment =
                new Appointment();

        AppointmentResponseDTO response =
                new AppointmentResponseDTO();

        when(appointmentRepository.findByUserName("John"))
                .thenReturn(List.of(appointment));

        when(appointmentMapper.mapToResponse(appointment))
                .thenReturn(response);

        List<AppointmentResponseDTO> result =
                appointmentService.findByUser(
                        null,"John");

        assertEquals(1,result.size());
    }

    @Test
    void shouldReturnEmptyListWhenSearchConditionEmpty() {

        List<AppointmentResponseDTO> result =
                appointmentService.findByUser(
                        null,null);

        assertEquals(0,result.size());
    }

    @Test
    void shouldReturnCurrentUsersAppointments() {

        User user = new User();
        user.setEmail("john@email.com");

        Appointment appointment =
                new Appointment();

        AppointmentResponseDTO response =
                new AppointmentResponseDTO();

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        when(authentication.getName())
                .thenReturn("john@email.com");

        when(userRepository.findByEmail("john@email.com"))
                .thenReturn(Optional.of(user));

        when(appointmentRepository.findByUserEmail("john@email.com"))
                .thenReturn(List.of(appointment));

        when(appointmentMapper.mapToResponse(appointment))
                .thenReturn(response);

        List<AppointmentResponseDTO> result =
                appointmentService.getMyAppointments();

        assertEquals(1,result.size());
    }

    @Test
    void shouldThrowExceptionWhenCurrentUserNotFound() {

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        when(authentication.getName())
                .thenReturn("john@email.com");

        when(userRepository.findByEmail("john@email.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                ()->appointmentService.getMyAppointments());
    }

}
