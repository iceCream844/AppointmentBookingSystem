package com.swc.appointment_booking.service.impl;

import com.swc.appointment_booking.dto.response.AppointmentResponseDTO;
import com.swc.appointment_booking.dto.resquest.AppointmentRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.swc.appointment_booking.service.AppointmentService;

public class AppointmentServiceImpl implements AppointmentService {

    @Override
    public AppointmentResponseDTO findByEmail(Long id) {
        return null;
    }

    @Override
    public AppointmentResponseDTO create(AppointmentRequestDTO dto) {
        return null;
    }

    @Override
    public AppointmentResponseDTO findById(Long aLong) {
        return null;
    }

    @Override
    public Page<AppointmentResponseDTO> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public AppointmentResponseDTO update(Long aLong, AppointmentRequestDTO dto) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }
}
