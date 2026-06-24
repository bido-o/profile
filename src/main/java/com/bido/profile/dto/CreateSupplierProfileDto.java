package com.bido.profile.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateSupplierProfileDto(
    Long id,
    @NotBlank String companyName
) {}
