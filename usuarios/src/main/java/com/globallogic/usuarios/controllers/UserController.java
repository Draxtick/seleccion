package com.globallogic.usuarios.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.globallogic.usuarios.dto.UserRequestDTO;
import com.globallogic.usuarios.dto.UserResponseDTO;
import com.globallogic.usuarios.entities.User;
import com.globallogic.usuarios.services.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;




@RestController
@AllArgsConstructor
public class UserController {


    private UserService userService;


    @SuppressWarnings({ "rawtypes", "unchecked" })
    @PostMapping("/user")
    public ResponseEntity createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) throws Exception {
        System.out.println("hello");
        ResponseEntity response;

        try {
            UserResponseDTO userResponseDTO = userService.save(userRequestDTO);
            response = new ResponseEntity(userResponseDTO, HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println("error " + e);
            e.printStackTrace();
            throw e;

        }
        return response;
    }


}