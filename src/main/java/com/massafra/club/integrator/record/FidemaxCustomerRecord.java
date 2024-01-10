package com.massafra.club.integrator.record;

import java.time.LocalDate;

public record FidemaxCustomerRecord(String name,
                                    String cgc,
                                    LocalDate birthDate,
                                    String phone,
                                    String password,
                                    String address) {
}
