package com.raul.demo.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean		//@Bean para fazer a injeção de dependência com o ModelMapper
    public ModelMapper mapper() {
        return new ModelMapper();
    }
}