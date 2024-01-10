package com.massafra.club.integrator.dto;

import java.time.LocalDate;

public record FidemaxCustomerDTO(String name,
                                 String cgc,
                                 LocalDate birthDate,
                                 String phone,
                                 String password,
                                 String address) {
}
