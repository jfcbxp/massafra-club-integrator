package com.massafra.club.integrator.configs;


import com.massafra.club.integrator.converters.FidemaxCustomerConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Autowired
    private FidemaxCustomerConverter fidemaxCustomerConverter;

    @Bean
    public ModelMapper modelMapper() {

        final var modelMapper = new ModelMapper();

        modelMapper.addConverter(fidemaxCustomerConverter);

        return modelMapper;
    }
}
