package com.swc.appointment_booking.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class AppointmentResponseDTO {

    Long id;

    LocalDateTime appointmentTime;

    String status;

    Long userId;
}
