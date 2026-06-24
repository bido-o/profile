package com.bido.profile.service;

import com.bido.profile.dto.CreateClientProfileDto;
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

    public List<CreateClientProfileDto> findAll() {
        List<CreateClientProfileDto> profiles = repository.findAll().stream().map(this::mapToDto).toList();
        log.debug("Fetched {} client profiles", profiles.size());
        return profiles;
    }

    public CreateClientProfileDto findById(Long id) {
        return repository.findById(id).map(this::mapToDto)
                .orElseThrow(() -> {
                    log.warn("Client profile not found for user [{}]", id);
                    return new ProfileNotFoundException("Client profile not found");
                });
    }

    public CreateClientProfileDto create(CreateClientProfileDto dto) {
        if (dto.id() != null && repository.existsById(dto.id())) {
            log.warn("Attempt to create duplicate client profile for user [{}]", dto.id());
            throw new ProfileAlreadyExistsException("Client profile already exists");
        }
        CreateClientProfileDto saved = persist(dto);
        log.info("Created client profile for user [{}]", saved.id());
        return saved;
    }

    public CreateClientProfileDto update(CreateClientProfileDto dto) {
        if (!repository.existsById(dto.id())) {
            log.warn("Attempt to update non-existent client profile for user [{}]", dto.id());
            throw new ProfileNotFoundException("Client profile not found");
        }
        CreateClientProfileDto saved = persist(dto);
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

    private CreateClientProfileDto persist(CreateClientProfileDto dto) {
        ClientProfile entity = repository.findById(dto.id() != null ? dto.id() : -1L).orElse(new ClientProfile());

        entity.setId(dto.id());
        entity.setFirstName(dto.firstName());
        entity.setLastName(dto.lastName());

        return mapToDto(repository.save(entity));
    }

    private CreateClientProfileDto mapToDto(ClientProfile entity) {
        return new CreateClientProfileDto(
            entity.getId(),
            entity.getFirstName(),
            entity.getLastName()
        );
    }
}
