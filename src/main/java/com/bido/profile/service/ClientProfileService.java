package com.bido.profile.service;

import com.bido.profile.dto.ClientProfileDto;
import com.bido.profile.entity.ClientProfile;
import com.bido.profile.repository.ClientProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClientProfileService {

    private final ClientProfileRepository repository;

    public ClientProfileService(ClientProfileRepository repository) {
        this.repository = repository;
    }

    public List<ClientProfileDto> findAll() {
        return repository.findAll().stream().map(this::mapToDto).toList();
    }

    public ClientProfileDto findById(Long id) {
        return repository.findById(id).map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("Client profile not found"));
    }

    public ClientProfileDto save(ClientProfileDto dto) {
        ClientProfile entity = repository.findById(dto.id()).orElse(new ClientProfile());
        
        entity.setId(dto.id());
        entity.setFirstName(dto.firstName());
        entity.setLastName(dto.lastName());
        entity.setPhoneNumber(dto.phoneNumber());
        entity.setCompanyName(dto.companyName());
        entity.setCui(dto.cui());
        entity.setBillingAddress(dto.billingAddress());
        
        return mapToDto(repository.save(entity));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private ClientProfileDto mapToDto(ClientProfile entity) {
        return new ClientProfileDto(
            entity.getId(),
            entity.getFirstName(),
            entity.getLastName(),
            entity.getPhoneNumber(),
            entity.getCompanyName(),
            entity.getCui(),
            entity.getBillingAddress()
        );
    }
}

