package com.swc.appointment_booking.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class AppointmentRequestDTO {

    @NotNull(message = "Appointment time is required")
    LocalDateTime appointmentTime;

    @NotNull(message = "Appointment status is required")
    String status;

    @NotNull(message = "User id is required")
    Long userId;
}
