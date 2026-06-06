package com.bido.profile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ClientProfileDto(
    Long id,
    @NotBlank String firstName,
    @NotBlank String lastName,
    @Pattern(regexp = "^\\+?[\\d\\s\\-\\(\\)]{7,20}$", message = "invalid phone number format") String phoneNumber,
    @NotBlank String companyName,
    @Pattern(regexp = "^(RO)?[0-9]{2,10}$", message = "invalid CUI format") String cui,
    String billingAddress
) {}
