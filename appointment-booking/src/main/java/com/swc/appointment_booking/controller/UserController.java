package com.swc.appointment_booking.controller;

import com.swc.appointment_booking.dto.request.UserRequestDTO;
import com.swc.appointment_booking.dto.response.UserResponseDTO;
import com.swc.appointment_booking.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponseDTO create(@RequestBody UserRequestDTO dto) {
        return userService.create(dto);
    }

    @GetMapping("/{id}")
    public UserResponseDTO findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping
    public Page<UserResponseDTO> findAll(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @PutMapping("/{id}")
    public UserResponseDTO update(@PathVariable Long id,
                                  @RequestBody UserRequestDTO dto) {
        return userService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
