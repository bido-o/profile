package com.bido.profile.controller;

import com.bido.profile.dto.CreateSupplierProfileDto;
import com.bido.profile.security.AuthContext;
import com.bido.profile.service.SupplierProfileService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/supplier-profiles")
public class SupplierProfileController {

    private static final Logger log = LoggerFactory.getLogger(SupplierProfileController.class);

    private final SupplierProfileService service;

    public SupplierProfileController(SupplierProfileService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> get(AuthContext auth) {
        if (auth.isAdmin()) {
            log.info("Admin [{}] requested all supplier profiles", auth.userId());
            return ResponseEntity.ok(service.findAll());
        }
        if (!auth.isSupplier()) {
            throw AuthContext.forbidden();
        }
        log.info("Supplier [{}] requested their profile", auth.userId());
        return ResponseEntity.ok(service.findById(auth.userId()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateSupplierProfileDto create(@Valid @RequestBody CreateSupplierProfileDto dto, AuthContext auth) {
        if (auth.isAdmin()) {
            log.info("Admin [{}] creating supplier profile for user [{}]", auth.userId(), dto.id());
            return service.create(dto);
        }
        if (!auth.isSupplier()) {
            throw AuthContext.forbidden();
        }
        log.info("Supplier [{}] creating their profile", auth.userId());
        CreateSupplierProfileDto ownedDto = new CreateSupplierProfileDto(auth.userId(), dto.companyName());
        return service.create(ownedDto);
    }

    @PutMapping
    public CreateSupplierProfileDto update(@Valid @RequestBody CreateSupplierProfileDto dto, AuthContext auth) {
        if (auth.isAdmin()) {
            log.info("Admin [{}] updating supplier profile for user [{}]", auth.userId(), dto.id());
            return service.update(dto);
        }
        if (!auth.isSupplier()) {
            throw AuthContext.forbidden();
        }
        log.info("Supplier [{}] updating their profile", auth.userId());
        CreateSupplierProfileDto ownedDto = new CreateSupplierProfileDto(auth.userId(), dto.companyName());
        return service.update(ownedDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(AuthContext auth) {
        if (!auth.isSupplier()) {
            throw AuthContext.forbidden();
        }
        log.info("Supplier [{}] deleting their profile", auth.userId());
        service.delete(auth.userId());
    }
}
