package com.bido.profile.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateClientProfileDto(
    Long id,
    @NotBlank String firstName,
    @NotBlank String lastName
) {}
