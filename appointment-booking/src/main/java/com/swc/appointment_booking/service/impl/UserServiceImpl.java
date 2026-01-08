package com.swc.appointment_booking.service.impl;

import com.swc.appointment_booking.dto.response.UserResponseDTO;
import com.swc.appointment_booking.dto.resquest.UserRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.swc.appointment_booking.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public UserResponseDTO create(UserRequestDTO dto) {
        return null;
    }

    @Override
    public UserResponseDTO findById(Long aLong) {
        return null;
    }

    @Override
    public Page<UserResponseDTO> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public UserResponseDTO update(Long aLong, UserRequestDTO dto) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }
}
