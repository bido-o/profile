package com.bido.profile.service;

import com.bido.profile.dto.SupplierProfileDto;
import com.bido.profile.entity.SupplierProfile;
import com.bido.profile.exception.ProfileAlreadyExistsException;
import com.bido.profile.exception.ProfileNotFoundException;
import com.bido.profile.repository.SupplierProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

@Service
@Transactional
public class SupplierProfileService {

    private static final Logger log = LoggerFactory.getLogger(SupplierProfileService.class);

    private final SupplierProfileRepository repository;

    public SupplierProfileService(SupplierProfileRepository repository) {
        this.repository = repository;
    }

    public List<SupplierProfileDto> findAll() {
        List<SupplierProfileDto> profiles = repository.findAll().stream().map(this::mapToDto).toList();
        log.debug("Fetched {} supplier profiles", profiles.size());
        return profiles;
    }

    public SupplierProfileDto findById(Long id) {
        return repository.findById(id).map(this::mapToDto)
                .orElseThrow(() -> {
                    log.warn("Supplier profile not found for user [{}]", id);
                    return new ProfileNotFoundException("Supplier profile not found");
                });
    }

    public SupplierProfileDto create(SupplierProfileDto dto) {
        if (dto.id() != null && repository.existsById(dto.id())) {
            log.warn("Attempt to create duplicate supplier profile for user [{}]", dto.id());
            throw new ProfileAlreadyExistsException("Supplier profile already exists");
        }
        SupplierProfileDto saved = persist(dto);
        log.info("Created supplier profile for user [{}]", saved.id());
        return saved;
    }

    public SupplierProfileDto update(SupplierProfileDto dto) {
        if (!repository.existsById(dto.id())) {
            log.warn("Attempt to update non-existent supplier profile for user [{}]", dto.id());
            throw new ProfileNotFoundException("Supplier profile not found");
        }
        SupplierProfileDto saved = persist(dto);
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

    private SupplierProfileDto persist(SupplierProfileDto dto) {
        SupplierProfile entity = repository.findById(dto.id() != null ? dto.id() : -1L).orElse(new SupplierProfile());

        entity.setId(dto.id());
        entity.setCompanyName(dto.companyName());
        entity.setCreditBalance(dto.creditBalance() != null ? dto.creditBalance() : 0);
        entity.setMinOrder(dto.minOrder() != null ? dto.minOrder() : 0.0);
        entity.setMinTimePrepOrder(dto.minTimePrepOrder() != null ? dto.minTimePrepOrder() : Duration.ofHours(1));
        entity.setAvgRating(dto.avgRating());
        entity.setAcceptsOnlinePayments(dto.acceptsOnlinePayments() != null ? dto.acceptsOnlinePayments() : false);
        entity.setHasLegalInfo(dto.hasLegalInfo() != null ? dto.hasLegalInfo() : false);
        entity.setTotalOffersWon(dto.totalOffersWon() != null ? dto.totalOffersWon() : 0);
        entity.setTotalDisputesLost(dto.totalDisputesLost() != null ? dto.totalDisputesLost() : 0);
        entity.setTotalOffersSubmitted(dto.totalOffersSubmitted() != null ? dto.totalOffersSubmitted() : 0);

        return mapToDto(repository.save(entity));
    }

    private SupplierProfileDto mapToDto(SupplierProfile entity) {
        return new SupplierProfileDto(
            entity.getId(),
            entity.getCompanyName(),
            entity.getCreditBalance(),
            entity.getMinOrder(),
            entity.getMinTimePrepOrder(),
            entity.getAvgRating(),
            entity.getAcceptsOnlinePayments(),
            entity.getHasLegalInfo(),
            entity.getTotalOffersWon(),
            entity.getTotalDisputesLost(),
            entity.getTotalOffersSubmitted()
        );
    }
}
