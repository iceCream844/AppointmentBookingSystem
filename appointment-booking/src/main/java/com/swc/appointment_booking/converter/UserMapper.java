package com.swc.appointment_booking.converter;

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
        return dto;
    }

}
