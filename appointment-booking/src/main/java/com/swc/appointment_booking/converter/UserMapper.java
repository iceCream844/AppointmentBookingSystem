package com.swc.appointment_booking.converter;

import com.swc.appointment_booking.dto.response.AppointmentSummaryDTO;
import com.swc.appointment_booking.dto.response.UserResponseDTO;
import com.swc.appointment_booking.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserResponseDTO mapToResponse(User user) {

        UserResponseDTO dto = new UserResponseDTO();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().toString());

        if (user.getAppointments() != null) {
            dto.setAppointments(
                    user.getAppointments()
                            .stream()
                            .map(appointment -> {
                                AppointmentSummaryDTO summary = new AppointmentSummaryDTO();
                                summary.setId(appointment.getId());
                                summary.setAppointmentTime(appointment.getAppointmentTime());
                                summary.setStatus(appointment.getStatus().toString());
                                return summary;
                            })
                            .toList()
            );
        }

        return dto;
    }
}
