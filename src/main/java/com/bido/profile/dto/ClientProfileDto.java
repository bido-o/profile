package com.bido.profile.dto;

import jakarta.validation.constraints.NotBlank;

public record ClientProfileDto(
    Long id,
    @NotBlank String firstName,
    @NotBlank String lastName,
    String phoneNumber,
    @NotBlank String companyName,
    String cui,
    String billingAddress
) {}
