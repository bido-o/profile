package com.bido.profile.exception;

public record ErrorResponse(int status, String error, String message) {}
