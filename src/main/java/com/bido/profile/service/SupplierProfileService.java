package com.bido.profile.service;

import com.bido.profile.dto.CreateSupplierProfileDto;
import com.bido.profile.entity.SupplierProfile;
import com.bido.profile.exception.ProfileAlreadyExistsException;
import com.bido.profile.exception.ProfileNotFoundException;
import com.bido.profile.repository.SupplierProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SupplierProfileService {

    private static final Logger log = LoggerFactory.getLogger(SupplierProfileService.class);

    private final SupplierProfileRepository repository;

    public SupplierProfileService(SupplierProfileRepository repository) {
        this.repository = repository;
    }

    public List<CreateSupplierProfileDto> findAll() {
        List<CreateSupplierProfileDto> profiles = repository.findAll().stream().map(this::mapToDto).toList();
        log.debug("Fetched {} supplier profiles", profiles.size());
        return profiles;
    }

    public CreateSupplierProfileDto findById(Long id) {
        return repository.findById(id).map(this::mapToDto)
                .orElseThrow(() -> {
                    log.warn("Supplier profile not found for user [{}]", id);
                    return new ProfileNotFoundException("Supplier profile not found");
                });
    }

    public CreateSupplierProfileDto create(CreateSupplierProfileDto dto) {
        if (dto.id() != null && repository.existsById(dto.id())) {
            log.warn("Attempt to create duplicate supplier profile for user [{}]", dto.id());
            throw new ProfileAlreadyExistsException("Supplier profile already exists");
        }
        CreateSupplierProfileDto saved = persist(dto);
        log.info("Created supplier profile for user [{}]", saved.id());
        return saved;
    }

    public CreateSupplierProfileDto update(CreateSupplierProfileDto dto) {
        if (!repository.existsById(dto.id())) {
            log.warn("Attempt to update non-existent supplier profile for user [{}]", dto.id());
            throw new ProfileNotFoundException("Supplier profile not found");
        }
        CreateSupplierProfileDto saved = persist(dto);
        log.info("Updated supplier profile for user [{}]", saved.id());
        return saved;
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            log.warn("Attempt to delete non-existent supplier profile for user [{}]", id);
            throw new ProfileNotFoundException("Supplier profile not found");
        }
        repository.deleteById(id);
        log.info("Deleted supplier profile for user [{}]", id);
    }

    private CreateSupplierProfileDto persist(CreateSupplierProfileDto dto) {
        SupplierProfile entity = repository.findById(dto.id() != null ? dto.id() : -1L).orElse(new SupplierProfile());

        entity.setId(dto.id());
        entity.setCompanyName(dto.companyName());

        return mapToDto(repository.save(entity));
    }

    private CreateSupplierProfileDto mapToDto(SupplierProfile entity) {
        return new CreateSupplierProfileDto(
            entity.getId(),
            entity.getCompanyName()
        );
    }
}
