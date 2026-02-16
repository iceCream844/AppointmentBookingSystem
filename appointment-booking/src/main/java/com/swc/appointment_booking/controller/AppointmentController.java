package com.swc.appointment_booking.controller;

import com.swc.appointment_booking.dto.request.AppointmentRequestDTO;
import com.swc.appointment_booking.dto.response.AppointmentResponseDTO;
import com.swc.appointment_booking.service.AppointmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public AppointmentResponseDTO create(@RequestBody AppointmentRequestDTO dto) {
        return appointmentService.create(dto);
    }

    @GetMapping("/{id}")
    public AppointmentResponseDTO findById(@PathVariable Long id) {
        return appointmentService.findById(id);
    }

    @GetMapping
    public Page<AppointmentResponseDTO> findAll(Pageable pageable) {
        return appointmentService.findAll(pageable);
    }

    @PutMapping("/{id}")
    public AppointmentResponseDTO update(@PathVariable Long id,
                                         @RequestBody AppointmentRequestDTO dto) {
        return appointmentService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        appointmentService.deleteById(id);
    }

    @GetMapping("/search")
    public List<AppointmentResponseDTO> findByUser(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String name) {

        return appointmentService.findByUser(email, name);
    }
}
