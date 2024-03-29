package com.massafra.club.integrator.configs;


import com.massafra.club.integrator.converters.FidemaxCustomerConverter;
import com.massafra.club.integrator.converters.FidemaxOrderItemProfessionalConverter;
import com.massafra.club.integrator.converters.FidemaxOrderProfessionalConverter;
import com.massafra.club.integrator.converters.FidemaxOrderRefundItemProfessionalConverter;
import com.massafra.club.integrator.converters.FidemaxOrderRefundProfessionalConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ModelMapperConfig {
    private final FidemaxCustomerConverter fidemaxCustomerConverter;
    private final FidemaxOrderItemProfessionalConverter fidemaxOrderItemProfessionalConverter;
    private final FidemaxOrderRefundItemProfessionalConverter fidemaxOrderRefundItemProfessionalConverter;
    private final FidemaxOrderProfessionalConverter fidemaxOrderProfessionalConverter;
    private final FidemaxOrderRefundProfessionalConverter fidemaxOrderRefundProfessionalConverter;


    @Bean
    public ModelMapper modelMapper() {

        final var modelMapper = new ModelMapper();

        modelMapper.addConverter(fidemaxCustomerConverter);
        modelMapper.addConverter(fidemaxOrderItemProfessionalConverter);
        modelMapper.addConverter(fidemaxOrderRefundItemProfessionalConverter);
        modelMapper.addConverter(fidemaxOrderProfessionalConverter);
        modelMapper.addConverter(fidemaxOrderRefundProfessionalConverter);
        
        return modelMapper;
    }
}
