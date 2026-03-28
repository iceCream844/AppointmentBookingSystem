package com.swc.appointment_booking.service.impl;

import com.swc.appointment_booking.converter.AppointmentMapper;
import com.swc.appointment_booking.dto.response.AppointmentResponseDTO;
import com.swc.appointment_booking.dto.request.AppointmentRequestDTO;
import com.swc.appointment_booking.entity.Appointment;
import com.swc.appointment_booking.entity.User;
import com.swc.appointment_booking.enums.AppointmentStatus;
import com.swc.appointment_booking.exception.ResourceNotFoundException;
import com.swc.appointment_booking.repository.AppointmentRepository;
import com.swc.appointment_booking.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.swc.appointment_booking.service.AppointmentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final AppointmentMapper appointmentMapper;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, UserRepository userRepository, AppointmentMapper appointmentMapper) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.appointmentMapper = appointmentMapper;
    }

    @Override
    public AppointmentResponseDTO create(AppointmentRequestDTO dto) {
        Appointment appointment = new Appointment();
        User user = getAndCheckUserExist(dto.getUserId());

        appointment.setStatus(AppointmentStatus.valueOf(dto.getStatus()));
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setUser(user);
        Appointment saved = appointmentRepository.save(appointment);

        return appointmentMapper.mapToResponse(saved);
    }

    @Override
    public AppointmentResponseDTO findById(Long id) {

        Appointment appointment = getAndCheckAppointmentExist(id);


        return appointmentMapper.mapToResponse(appointment);
    }

    @Override
    public Page<AppointmentResponseDTO> findAll(Pageable pageable) {
        Page<Appointment> appointments = appointmentRepository.findAll(pageable);

        return appointments.map(appointmentMapper::mapToResponse);

    }

    @Override
    public AppointmentResponseDTO update(Long id, AppointmentRequestDTO dto) {
        Appointment appointment = getAndCheckAppointmentExist(id);
        User user = getAndCheckUserExist(dto.getUserId());

        appointment.setStatus(AppointmentStatus.valueOf(dto.getStatus()));
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setUser(user);
        Appointment saved = appointmentRepository.save(appointment);

        return appointmentMapper.mapToResponse(saved);
    }

    @Override
    public void deleteById(Long id) {
        Appointment appointment = getAndCheckAppointmentExist(id);
        appointmentRepository.delete(appointment);
    }

    @Override
    public List<AppointmentResponseDTO> findByUser(String email, String name) {
        List<Appointment> list = new ArrayList<>();
        if (email != null && !email.isBlank()) {
            list = appointmentRepository.findByUserEmail(email);
        } else if (name != null && !name.isBlank()) {
            list = appointmentRepository.findByUserName(name);
        }
        return list.stream()
                .map(appointmentMapper::mapToResponse)
                .toList();

    }

    private Appointment getAndCheckAppointmentExist(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with ID:" + id));
    }

    private User getAndCheckUserExist(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID:" + id));
    }
}
