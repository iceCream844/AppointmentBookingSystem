package com.swc.appointment_booking.service.impl;

import com.swc.appointment_booking.converter.UserMapper;
import com.swc.appointment_booking.dto.response.AppointmentSummaryDTO;
import com.swc.appointment_booking.dto.response.UserResponseDTO;
import com.swc.appointment_booking.dto.request.UserRequestDTO;
import com.swc.appointment_booking.entity.User;
import com.swc.appointment_booking.enums.UserRole;
import com.swc.appointment_booking.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.swc.appointment_booking.service.UserService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponseDTO create(UserRequestDTO dto) {

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(UserRole.valueOf(dto.getRole()));
        User saved = userRepository.save(user);

        return userMapper.mapToResponse(saved);
    }

    @Override
    public UserResponseDTO findById(Long id) {

        User user = userRepository.findById(id).orElse(null);

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setName(user.getName());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setRole(user.getRole().toString());

        if (user.getAppointments() != null) {
            responseDTO.setAppointments(
                    user.getAppointments()
                            .stream()
                            .map(appointment -> {
                                AppointmentSummaryDTO summary = new AppointmentSummaryDTO();
                                summary.setId(appointment.getId());
                                summary.setAppointmentTime(appointment.getAppointmentTime());
                                summary.setStatus(String.valueOf(appointment.getStatus()));
                                return summary;
                            })
                            .toList()
            );
        }

        return responseDTO;
    }

    @Override
    public Page<UserResponseDTO> findAll(Pageable pageable) {

        Page<User> users = userRepository.findAll(pageable);

        return users.map(user -> {
            UserResponseDTO dto = new UserResponseDTO();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            dto.setRole(user.getRole().toString());
            return dto;
        });
    }

    @Override
    public UserResponseDTO update(Long id, UserRequestDTO dto) {
        UserResponseDTO responseDTO = new UserResponseDTO();
        User user = userRepository.findById(id).orElse(null);

        user.setRole(UserRole.valueOf(dto.getRole()));
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        userRepository.save(user);

        return responseDTO;
    }

    @Override
    public void deleteById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        userRepository.delete(user);
    }
}
