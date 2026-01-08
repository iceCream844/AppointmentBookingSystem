package service;

import dto.response.AppointmentResponseDTO;
import dto.resquest.AppointmentRequestDTO;
import service.Common.BaseService;

public interface AppointmentService extends BaseService<AppointmentRequestDTO, AppointmentResponseDTO, Long> {

    AppointmentResponseDTO findByEmail(Long id);

}
