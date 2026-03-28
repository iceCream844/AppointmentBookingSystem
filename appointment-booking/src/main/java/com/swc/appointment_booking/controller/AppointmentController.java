package com.swc.appointment_booking.controller;

import com.swc.appointment_booking.dto.request.AppointmentRequestDTO;
import com.swc.appointment_booking.dto.response.AppointmentResponseDTO;
import com.swc.appointment_booking.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<AppointmentResponseDTO> create(@Valid @RequestBody AppointmentRequestDTO dto) {
        AppointmentResponseDTO response = appointmentService.create(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> findById(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<AppointmentResponseDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(appointmentService.findAll(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> update(@Valid
            @PathVariable Long id,
            @RequestBody AppointmentRequestDTO dto) {

        return ResponseEntity.ok(appointmentService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        appointmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<AppointmentResponseDTO>> findByUser(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String name) {

        return ResponseEntity.ok(appointmentService.findByUser(email, name));
    }
}
