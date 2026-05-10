package com.bido.profile.dto;

public record ClientProfileDto(
    Long id,
    String firstName,
    String lastName,
    String phoneNumber,
    String companyName,
    String cui,
    String billingAddress
) {}

