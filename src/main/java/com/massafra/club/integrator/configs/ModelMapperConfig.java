package com.massafra.club.integrator.configs;


import com.massafra.club.integrator.converters.FidemaxCustomerConverter;
import com.massafra.club.integrator.converters.FidemaxOrderItemProfessionalConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ModelMapperConfig {
    private final FidemaxCustomerConverter fidemaxCustomerConverter;
    private final FidemaxOrderItemProfessionalConverter fidemaxOrderItemProfessionalConverter;

    @Bean
    public ModelMapper modelMapper() {

        final var modelMapper = new ModelMapper();

        modelMapper.addConverter(fidemaxCustomerConverter);
        modelMapper.addConverter(fidemaxOrderItemProfessionalConverter);

        return modelMapper;
    }
}
