package com.globallogic.usuarios.services;

import com.globallogic.usuarios.dto.UserRequestDTO;
import com.globallogic.usuarios.dto.UserResponseDTO;


public interface UserService {

    UserResponseDTO save(UserRequestDTO userRequest) throws Exception;

}
