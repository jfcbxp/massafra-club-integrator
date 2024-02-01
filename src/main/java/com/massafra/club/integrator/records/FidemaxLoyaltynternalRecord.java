package com.massafra.club.integrator.records;

import java.math.BigInteger;

public record FidemaxLoyaltynternalRecord(String cgc,
                                          String phone,
                                          BigInteger points,
                                          String type,
                                          String id,
                                          boolean rollback) {
}
