package com.bido.profile.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.Duration;

public record SupplierProfileDto(
    Long id,
    @NotBlank String companyName,
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
