package com.swc.appointment_booking.service;

import com.swc.appointment_booking.dto.response.AppointmentResponseDTO;
import com.swc.appointment_booking.dto.request.AppointmentRequestDTO;
import com.swc.appointment_booking.service.common.BaseService;

import java.util.List;

public interface AppointmentService extends BaseService<AppointmentRequestDTO, AppointmentResponseDTO, Long> {

    List<AppointmentResponseDTO> findByUser(String email, String name);


}
