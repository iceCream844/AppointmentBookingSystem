package com.swc.appointment_booking.service.impl;

import com.swc.appointment_booking.dto.response.AppointmentResponseDTO;
import com.swc.appointment_booking.dto.request.AppointmentRequestDTO;
import com.swc.appointment_booking.entity.Appointment;
import com.swc.appointment_booking.entity.User;
import com.swc.appointment_booking.enums.AppointmentStatus;
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

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AppointmentResponseDTO create(AppointmentRequestDTO dto) {
        Appointment appointment = new Appointment();
        User user = userRepository.findById(dto.getUserId()).orElse(null);

        appointment.setStatus(AppointmentStatus.valueOf(dto.getStatus()));
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
        responseDTO.setStatus(String.valueOf(appointment.getStatus()));

        return responseDTO;
    }

    @Override
    public Page<AppointmentResponseDTO> findAll(Pageable pageable) {
        Page<Appointment> appointments = appointmentRepository.findAll(pageable);

        return  appointments.map(appointment -> {
            AppointmentResponseDTO dto = new AppointmentResponseDTO();
            dto.setStatus(String.valueOf(appointment.getStatus()));
            dto.setId(appointment.getId());
            dto.setAppointmentTime(appointment.getAppointmentTime());
            dto.setUserId(appointment.getUser().getId());
            return dto;
        });
    }

    @Override
    public AppointmentResponseDTO update(Long id, AppointmentRequestDTO dto) {
        Appointment appointment = appointmentRepository.findById(id).orElse(null);
        User user = userRepository.findById(dto.getUserId()).orElse(null);

        appointment.setStatus(AppointmentStatus.valueOf(dto.getStatus()));
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
    public List<AppointmentResponseDTO> findByUser(String email, String name) {
        List<Appointment> list = new ArrayList<>();
        if (!String.valueOf(email).isEmpty()) {
            list = appointmentRepository.findByUserEmail(email);
        }else if (!String.valueOf(name).isEmpty()){
            list = appointmentRepository.findByUserName(name);
        }

        return list.stream()
                .map(a -> {
                    AppointmentResponseDTO dto = new AppointmentResponseDTO();
                    dto.setId(a.getId());
                    dto.setAppointmentTime(a.getAppointmentTime());
                    dto.setStatus(String.valueOf(a.getStatus()));

                    if (a.getUser() != null) {
                        dto.setUserId(a.getUser().getId());
                    }

                    return dto;
                })
                .toList();

    }
}
