package com.swc.appointment_booking.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponseDTO {

    private String id;

    private String name;

    private String email;

    private String role;

    private Long appointmentId;
}
