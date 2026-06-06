package com.bido.profile.controller;

import com.bido.profile.dto.ClientProfileDto;
import com.bido.profile.security.AuthContext;
import com.bido.profile.service.ClientProfileService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client-profiles")
public class ClientProfileController {

    private static final Logger log = LoggerFactory.getLogger(ClientProfileController.class);

    private final ClientProfileService service;

    public ClientProfileController(ClientProfileService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> get(AuthContext auth) {
        if (auth.isAdmin()) {
            log.info("Admin [{}] requested all client profiles", auth.userId());
            return ResponseEntity.ok(service.findAll());
        }
        if (!auth.isClient()) {
            throw AuthContext.forbidden();
        }
        log.info("Client [{}] requested their profile", auth.userId());
        return ResponseEntity.ok(service.findById(auth.userId()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientProfileDto create(@Valid @RequestBody ClientProfileDto dto, AuthContext auth) {
        if (auth.isAdmin()) {
            log.info("Admin [{}] creating client profile for user [{}]", auth.userId(), dto.id());
            return service.create(dto);
        }
        if (!auth.isClient()) {
            throw AuthContext.forbidden();
        }
        log.info("Client [{}] creating their profile", auth.userId());
        ClientProfileDto ownedDto = new ClientProfileDto(
            auth.userId(), dto.firstName(), dto.lastName(), dto.phoneNumber(),
            dto.companyName(), dto.cui(), dto.billingAddress()
        );
        return service.create(ownedDto);
    }

    @PutMapping
    public ClientProfileDto update(@Valid @RequestBody ClientProfileDto dto, AuthContext auth) {
        if (auth.isAdmin()) {
            log.info("Admin [{}] updating client profile for user [{}]", auth.userId(), dto.id());
            return service.update(dto);
        }
        if (!auth.isClient()) {
            throw AuthContext.forbidden();
        }
        log.info("Client [{}] updating their profile", auth.userId());
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
        log.info("Client [{}] deleting their profile", auth.userId());
        service.delete(auth.userId());
    }
}
