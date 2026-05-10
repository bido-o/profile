package com.bido.profile.dto;

import java.time.Duration;

public record SupplierProfileDto(
    Long id,
    String companyName,
    Integer creditBalance,
    Double minOrder,
    Duration minTimePrepOrder,
    Double avgRating,
    Boolean acceptsOnlinePayments,
    Boolean hasLegalInfo,
    Integer totalOffersWon,
    Integer totalDisputesLost,
    Integer totalOffersSubmitted
) {}
