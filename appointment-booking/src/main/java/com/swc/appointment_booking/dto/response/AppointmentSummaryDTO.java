package com.swc.appointment_booking.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentSummaryDTO {

    private Long id;

    private LocalDateTime appointmentTime;

    private String status;
}