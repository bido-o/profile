package com.bido.profile.controller;

import com.bido.profile.dto.SupplierProfileDto;
import com.bido.profile.service.SupplierProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier-profiles")
public class SupplierProfileController {

    private final SupplierProfileService service;

    public SupplierProfileController(SupplierProfileService service) {
        this.service = service;
    }

    @GetMapping
    public List<SupplierProfileDto> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public SupplierProfileDto getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SupplierProfileDto create(@RequestBody SupplierProfileDto dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public SupplierProfileDto update(@PathVariable Long id, @RequestBody SupplierProfileDto dto) {
        SupplierProfileDto updatedDto = new SupplierProfileDto(
            id, dto.companyName(), dto.creditBalance(), dto.minOrder(),
            dto.minTimePrepOrder(), dto.avgRating(), dto.acceptsOnlinePayments(),
            dto.hasLegalInfo(), dto.totalOffersWon(), dto.totalDisputesLost(),
            dto.totalOffersSubmitted()
        );
        return service.save(updatedDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

