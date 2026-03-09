package com.swc.appointment_booking.converter;

import com.swc.appointment_booking.dto.response.AppointmentResponseDTO;
import com.swc.appointment_booking.entity.Appointment;
import org.springframework.stereotype.Service;

@Service
public class AppointmentMapper {

    public AppointmentResponseDTO mapToResponse(Appointment appointment) {
        AppointmentResponseDTO dto = new AppointmentResponseDTO();
        dto.setId(appointment.getId());
        dto.setAppointmentTime(appointment.getAppointmentTime());
        dto.setUserId(appointment.getUser().getId());
        dto.setStatus(appointment.getStatus().toString());
        return dto;
    }
}
