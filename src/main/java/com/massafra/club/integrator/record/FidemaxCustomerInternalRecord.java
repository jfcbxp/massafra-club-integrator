package com.massafra.club.integrator.record;

import java.time.LocalDate;

public record FidemaxCustomerInternalRecord(String name,
                                            String cgc,
                                            LocalDate birthDate,
                                            String phone,
                                            String password,
                                            String street,
                                            String zipCode,
                                            String neighborhood,
                                            String complement) {
}
