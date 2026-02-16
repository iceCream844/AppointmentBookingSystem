package com.swc.appointment_booking.service;

import com.swc.appointment_booking.dto.response.UserResponseDTO;
import com.swc.appointment_booking.dto.request.UserRequestDTO;
import com.swc.appointment_booking.service.common.BaseService;

public interface UserService extends BaseService<UserRequestDTO, UserResponseDTO, Long> {
}
