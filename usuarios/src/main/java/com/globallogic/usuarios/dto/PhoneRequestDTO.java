package com.globallogic.usuarios.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor

//agregar anotaciones lombok a todos los beans
public class PhoneRequestDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String number;
    private String citycode;
    private String countrycode;
}
