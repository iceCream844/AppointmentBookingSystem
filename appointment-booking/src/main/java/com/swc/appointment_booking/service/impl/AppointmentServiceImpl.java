package com.swc.appointment_booking.service.impl;

import com.swc.appointment_booking.dto.response.AppointmentResponseDTO;
import com.swc.appointment_booking.dto.resquest.AppointmentRequestDTO;
import com.swc.appointment_booking.entity.Appointment;
import com.swc.appointment_booking.entity.User;
import com.swc.appointment_booking.repository.AppointmentRepository;
import com.swc.appointment_booking.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.swc.appointment_booking.service.AppointmentService;

public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AppointmentResponseDTO create(AppointmentRequestDTO dto) {
        Appointment appointment = new Appointment();
        User user = userRepository.findById(dto.getUserId()).orElse(null);

        appointment.setStatus(dto.getStatus());
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setUser(user);
        appointmentRepository.save(appointment);

        return null;
    }

    @Override
    public AppointmentResponseDTO findById(Long id) {
        AppointmentResponseDTO responseDTO = new AppointmentResponseDTO();
        Appointment appointment = appointmentRepository.findById(id).orElse(null);

        responseDTO.setAppointmentTime(appointment.getAppointmentTime());
        responseDTO.setId(appointment.getId());
        responseDTO.setUserId(appointment.getUser().getId());
        responseDTO.setStatus(appointment.getStatus());

        return responseDTO;
    }

    @Override
    public Page<AppointmentResponseDTO> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public AppointmentResponseDTO update(Long id, AppointmentRequestDTO dto) {
        Appointment appointment = appointmentRepository.findById(id).orElse(null);
        User user = userRepository.findById(dto.getUserId()).orElse(null);

        appointment.setStatus(dto.getStatus());
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setUser(user);
        appointmentRepository.save(appointment);
        return null;
    }

    @Override
    public void deleteById(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElse(null);

        appointmentRepository.delete(appointment);

    }

    @Override
    public AppointmentResponseDTO findByEmail(Long id) {
        return null;
    }
}
