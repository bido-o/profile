package com.bido.profile.controller;

import com.bido.profile.dto.ClientProfileDto;
import com.bido.profile.service.ClientProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client-profiles")
public class ClientProfileController {

    private final ClientProfileService service;

    public ClientProfileController(ClientProfileService service) {
        this.service = service;
    }

    @GetMapping
    public List<ClientProfileDto> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ClientProfileDto getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientProfileDto create(@RequestBody ClientProfileDto dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public ClientProfileDto update(@PathVariable Long id, @RequestBody ClientProfileDto dto) {
        ClientProfileDto updatedDto = new ClientProfileDto(
            id, dto.firstName(), dto.lastName(), dto.phoneNumber(),
            dto.companyName(), dto.cui(), dto.billingAddress()
        );
        return service.save(updatedDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

