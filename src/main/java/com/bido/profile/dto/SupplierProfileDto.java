package com.bido.profile.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.Duration;

public record SupplierProfileDto(
    Long id,
    @NotBlank String companyName,
    @Min(0) Integer creditBalance,
    @DecimalMin("0.0") Double minOrder,
    Duration minTimePrepOrder,
    @DecimalMin("0.0") @DecimalMax("5.0") Double avgRating,
    Boolean acceptsOnlinePayments,
    Boolean hasLegalInfo,
    @Min(0) Integer totalOffersWon,
    @Min(0) Integer totalDisputesLost,
    @Min(0) Integer totalOffersSubmitted
) {}
