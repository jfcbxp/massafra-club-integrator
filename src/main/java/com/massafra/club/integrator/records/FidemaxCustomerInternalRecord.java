package com.massafra.club.integrator.records;

import java.time.LocalDate;

public record FidemaxCustomerInternalRecord(String name,
                                            String cgc,
                                            String email,
                                            LocalDate birthDate,
                                            String phone,
                                            String password,
                                            String street,
                                            String zipCode,
                                            String neighborhood,
                                            String complement,
                                            Integer id) {
}
