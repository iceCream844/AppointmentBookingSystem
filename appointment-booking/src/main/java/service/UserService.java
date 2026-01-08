package service;

import dto.response.UserResponseDTO;
import dto.resquest.UserRequestDTO;
import service.Common.BaseService;

public interface UserService extends BaseService<UserRequestDTO, UserResponseDTO, Long> {
}
