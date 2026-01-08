package com.swc.appointment_booking.dto.resquest;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequestDTO {

    private String name;

    private String email;

    private String password;

    private String role;
}
