package com.swc.appointment_booking.service.impl;

import com.swc.appointment_booking.converter.UserMapper;
import com.swc.appointment_booking.dto.response.AppointmentSummaryDTO;
import com.swc.appointment_booking.dto.response.UserResponseDTO;
import com.swc.appointment_booking.dto.request.UserRequestDTO;
import com.swc.appointment_booking.entity.User;
import com.swc.appointment_booking.enums.UserRole;
import com.swc.appointment_booking.exception.ResourceNotFoundException;
import com.swc.appointment_booking.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.swc.appointment_booking.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDTO create(UserRequestDTO dto) {

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(UserRole.USER);
        User saved = userRepository.save(user);

        return userMapper.mapToResponse(saved);
    }

    @Override
    public UserResponseDTO findById(Long id) {

        User user = getAndCheckUserExist(id);

        UserResponseDTO  responseDTO =  userMapper.mapToResponse(user);

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

        return users.map(userMapper::mapToResponse);
    }

    @Override
    public UserResponseDTO update(Long id, UserRequestDTO dto) {

        User user = getAndCheckUserExist(id);

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole(UserRole.valueOf(dto.getRole()));

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        User saved = userRepository.save(user);

        return userMapper.mapToResponse(saved);
    }

    @Override
    public void deleteById(Long id) {

       User user = getAndCheckUserExist(id);

        userRepository.delete(user);
    }

    private User getAndCheckUserExist(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID:" + id));
    }

    @Override
    public UserResponseDTO getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userMapper.mapToResponse(user);
    }
}
