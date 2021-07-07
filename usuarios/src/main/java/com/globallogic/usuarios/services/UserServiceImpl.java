package com.globallogic.usuarios.services;

import com.globallogic.usuarios.dto.UserRequestDTO;
import com.globallogic.usuarios.dto.UserResponseDTO;
import com.globallogic.usuarios.entities.Phone;
import com.globallogic.usuarios.entities.User;
import com.globallogic.usuarios.exceptions.DuplicateEmailException;
import com.globallogic.usuarios.exceptions.DuplicateUserException;
import com.globallogic.usuarios.repository.UserRepository;
import com.globallogic.usuarios.security.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private JwtTokenUtil jwtTokenUtil;
    @Override
    public UserResponseDTO save(UserRequestDTO userRequest) throws Exception{

        User userSaved;


        if (isDuplicateEmail(userRequest.getEmail())) {

                if (isDuplicateUser(userRequest.getName())) {

                    User user = User.builder().name(userRequest.getName()).
                            email(userRequest.getEmail()).
                            password(userRequest.getPassword()).
                            phones(   userRequest.
                                    getPhones().
                                    stream().
                                    map(phoneRequestDTO -> {return Phone.builder().
                                            citycode(phoneRequestDTO.getCitycode()).
                                            countrycode(phoneRequestDTO.getCountrycode()).
                                            number(phoneRequestDTO.getNumber()).build();
                                    }).collect(Collectors.toList())).
                            dateOfCreation(LocalDateTime.now()).
                            dateOfModified(LocalDateTime.now()).token(jwtTokenUtil.generateToken(userRequest)).
                            lastLogin(LocalDateTime.now()).password(userRequest.getPassword()).
                            isActive(true).
                            build();
                    userSaved = userRepository.save(user);
                    return UserResponseDTO.builder().
                            id(userSaved.getId()).
                            name(userSaved.getName()).
                            dateOfCreation(userSaved.getDateOfCreation()).
                            dateOfModified(userSaved.getDateOfModified()).
                            lastLogin(userSaved.getLastLogin()).
                            token(userSaved.getToken()).
                            isActive(userSaved.isActive()).
                            build();
                } else {
                  throw new DuplicateUserException();
                }
            } else {
                throw new DuplicateEmailException();
            }




    }


    private boolean isDuplicateEmail(String email) {

        int counter =userRepository.countByEmail(email);

        return counter <= 0;

    }


    private boolean isDuplicateUser(String userName) {

        int counter=userRepository.countByName(userName);

        return counter <= 0;

    }
}
