package com.swc.appointment_booking.dto.response;

import com.swc.appointment_booking.entity.Appointment;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserResponseDTO {

    private Long id;

    private String name;

    private String email;

    private String role;

    private List<Appointment> appointmentId;
}
