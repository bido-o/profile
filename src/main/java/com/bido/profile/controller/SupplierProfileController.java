package com.bido.profile.controller;

import com.bido.profile.dto.SupplierProfileDto;
import com.bido.profile.security.AuthContext;
import com.bido.profile.service.SupplierProfileService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/supplier-profiles")
public class SupplierProfileController {

    private final SupplierProfileService service;

    public SupplierProfileController(SupplierProfileService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> get(AuthContext auth) {
        if (auth.isAdmin()) {
            return ResponseEntity.ok(service.findAll());
        }
        if (!auth.isSupplier()) {
            throw AuthContext.forbidden();
        }
        return ResponseEntity.ok(service.findById(auth.userId()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SupplierProfileDto create(@Valid @RequestBody SupplierProfileDto dto, AuthContext auth) {
        if (auth.isAdmin()) {
            return service.create(dto);
        }
        if (!auth.isSupplier()) {
            throw AuthContext.forbidden();
        }
        SupplierProfileDto ownedDto = new SupplierProfileDto(
            auth.userId(), dto.companyName(), dto.creditBalance(), dto.minOrder(),
            dto.minTimePrepOrder(), dto.avgRating(), dto.acceptsOnlinePayments(),
            dto.hasLegalInfo(), dto.totalOffersWon(), dto.totalDisputesLost(),
            dto.totalOffersSubmitted()
        );
        return service.create(ownedDto);
    }

    @PutMapping
    public SupplierProfileDto update(@Valid @RequestBody SupplierProfileDto dto, AuthContext auth) {
        if (auth.isAdmin()) {
            return service.update(dto);
        }
        if (!auth.isSupplier()) {
            throw AuthContext.forbidden();
        }
        SupplierProfileDto ownedDto = new SupplierProfileDto(
            auth.userId(), dto.companyName(), dto.creditBalance(), dto.minOrder(),
            dto.minTimePrepOrder(), dto.avgRating(), dto.acceptsOnlinePayments(),
            dto.hasLegalInfo(), dto.totalOffersWon(), dto.totalDisputesLost(),
            dto.totalOffersSubmitted()
        );
        return service.update(ownedDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(AuthContext auth) {
        if (!auth.isSupplier()) {
            throw AuthContext.forbidden();
        }
        service.delete(auth.userId());
    }
}
