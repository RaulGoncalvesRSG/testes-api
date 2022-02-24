package com.raul.demo.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Integer id;
    private String name;
    private String email;
    
    //Libera apenas para escrita. Dessa forma a senha n é retornada no DTO
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}