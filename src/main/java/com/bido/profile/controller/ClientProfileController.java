package com.bido.profile.controller;

import com.bido.profile.dto.ClientProfileDto;
import com.bido.profile.security.AuthContext;
import com.bido.profile.service.ClientProfileService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client-profiles")
public class ClientProfileController {

    private final ClientProfileService service;

    public ClientProfileController(ClientProfileService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> get(AuthContext auth) {
        if (auth.isAdmin()) {
            return ResponseEntity.ok(service.findAll());
        }
        if (!auth.isClient()) {
            throw AuthContext.forbidden();
        }
        return ResponseEntity.ok(service.findById(auth.userId()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientProfileDto create(@Valid @RequestBody ClientProfileDto dto, AuthContext auth) {
        if (auth.isAdmin()) {
            return service.create(dto);
        }
        if (!auth.isClient()) {
            throw AuthContext.forbidden();
        }
        ClientProfileDto ownedDto = new ClientProfileDto(
            auth.userId(), dto.firstName(), dto.lastName(), dto.phoneNumber(),
            dto.companyName(), dto.cui(), dto.billingAddress()
        );
        return service.create(ownedDto);
    }

    @PutMapping
    public ClientProfileDto update(@Valid @RequestBody ClientProfileDto dto, AuthContext auth) {
        if (auth.isAdmin()) {
            return service.update(dto);
        }
        if (!auth.isClient()) {
            throw AuthContext.forbidden();
        }
        ClientProfileDto ownedDto = new ClientProfileDto(
            auth.userId(), dto.firstName(), dto.lastName(), dto.phoneNumber(),
            dto.companyName(), dto.cui(), dto.billingAddress()
        );
        return service.update(ownedDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(AuthContext auth) {
        if (!auth.isClient()) {
            throw AuthContext.forbidden();
        }
        service.delete(auth.userId());
    }
}
