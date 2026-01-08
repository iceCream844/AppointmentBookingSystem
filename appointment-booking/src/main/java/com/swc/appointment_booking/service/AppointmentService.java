package com.swc.appointment_booking.service;

import com.swc.appointment_booking.dto.response.AppointmentResponseDTO;
import com.swc.appointment_booking.dto.resquest.AppointmentRequestDTO;
import com.swc.appointment_booking.service.common.BaseService;

public interface AppointmentService extends BaseService<AppointmentRequestDTO, AppointmentResponseDTO, Long> {

    AppointmentResponseDTO findByEmail(Long id);

}
