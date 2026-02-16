package com.swc.appointment_booking.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class AppointmentRequestDTO {

    LocalDateTime appointmentTime;

    String status;

    Long userId;
}
