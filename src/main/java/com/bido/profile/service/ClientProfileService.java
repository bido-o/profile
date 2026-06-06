package com.bido.profile.service;

import com.bido.profile.dto.ClientProfileDto;
import com.bido.profile.entity.ClientProfile;
import com.bido.profile.exception.ProfileAlreadyExistsException;
import com.bido.profile.exception.ProfileNotFoundException;
import com.bido.profile.repository.ClientProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClientProfileService {

    private static final Logger log = LoggerFactory.getLogger(ClientProfileService.class);

    private final ClientProfileRepository repository;

    public ClientProfileService(ClientProfileRepository repository) {
        this.repository = repository;
    }

    public List<ClientProfileDto> findAll() {
        List<ClientProfileDto> profiles = repository.findAll().stream().map(this::mapToDto).toList();
        log.debug("Fetched {} client profiles", profiles.size());
        return profiles;
    }

    public ClientProfileDto findById(Long id) {
        return repository.findById(id).map(this::mapToDto)
                .orElseThrow(() -> {
                    log.warn("Client profile not found for user [{}]", id);
                    return new ProfileNotFoundException("Client profile not found");
                });
    }

    public ClientProfileDto create(ClientProfileDto dto) {
        if (dto.id() != null && repository.existsById(dto.id())) {
            log.warn("Attempt to create duplicate client profile for user [{}]", dto.id());
            throw new ProfileAlreadyExistsException("Client profile already exists");
        }
        ClientProfileDto saved = persist(dto);
        log.info("Created client profile for user [{}]", saved.id());
        return saved;
    }

    public ClientProfileDto update(ClientProfileDto dto) {
        if (!repository.existsById(dto.id())) {
            log.warn("Attempt to update non-existent client profile for user [{}]", dto.id());
            throw new ProfileNotFoundException("Client profile not found");
        }
        ClientProfileDto saved = persist(dto);
        log.info("Updated client profile for user [{}]", saved.id());
        return saved;
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            log.warn("Attempt to delete non-existent client profile for user [{}]", id);
            throw new ProfileNotFoundException("Client profile not found");
        }
        repository.deleteById(id);
        log.info("Deleted client profile for user [{}]", id);
    }

    private ClientProfileDto persist(ClientProfileDto dto) {
        ClientProfile entity = repository.findById(dto.id() != null ? dto.id() : -1L).orElse(new ClientProfile());

        entity.setId(dto.id());
        entity.setFirstName(dto.firstName());
        entity.setLastName(dto.lastName());
        entity.setPhoneNumber(dto.phoneNumber());
        entity.setCompanyName(dto.companyName());
        entity.setCui(dto.cui());
        entity.setBillingAddress(dto.billingAddress());

        return mapToDto(repository.save(entity));
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
