package com.swc.appointment_booking.service.impl;

import com.swc.appointment_booking.dto.response.UserResponseDTO;
import com.swc.appointment_booking.dto.resquest.UserRequestDTO;
import com.swc.appointment_booking.entity.User;
import com.swc.appointment_booking.enums.UserRole;
import com.swc.appointment_booking.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.swc.appointment_booking.service.UserService;

import java.util.Optional;

@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDTO create(UserRequestDTO dto) {

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(UserRole.valueOf(dto.getRole()));
        userRepository.save(user);

        return null;
    }

    @Override
    public UserResponseDTO findById(Long id) {
        UserResponseDTO responseDTO = new UserResponseDTO();

        User user = userRepository.findById(id).orElseGet(null);

        responseDTO.setRole(user.getRole().toString());
        responseDTO.setName(user.getName());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setId(user.getId());
        responseDTO.setAppointmentId(user.getAppointments());

        return responseDTO;

    }

    @Override
    public Page<UserResponseDTO> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public UserResponseDTO update(Long id, UserRequestDTO dto) {
        UserResponseDTO responseDTO = new UserResponseDTO();
        User user = userRepository.findById(id).orElseGet(null);

        user.setRole(UserRole.valueOf(dto.getRole()));
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        userRepository.save(user);

        return responseDTO;
    }

    @Override
    public void deleteById(Long id) {
        User user = userRepository.findById(id).orElseGet(null);
        userRepository.delete(user);
    }
}
