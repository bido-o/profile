package com.bido.profile.service;

import com.bido.profile.dto.SupplierProfileDto;
import com.bido.profile.entity.SupplierProfile;
import com.bido.profile.exception.ProfileNotFoundException;
import com.bido.profile.repository.SupplierProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

@Service
@Transactional
public class SupplierProfileService {

    private final SupplierProfileRepository repository;

    public SupplierProfileService(SupplierProfileRepository repository) {
        this.repository = repository;
    }

    public List<SupplierProfileDto> findAll() {
        return repository.findAll().stream().map(this::mapToDto).toList();
    }

    public SupplierProfileDto findById(Long id) {
        return repository.findById(id).map(this::mapToDto)
                .orElseThrow(() -> new ProfileNotFoundException("Supplier profile not found"));
    }

    public SupplierProfileDto save(SupplierProfileDto dto) {
        SupplierProfile entity = repository.findById(dto.id()).orElse(new SupplierProfile());

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

    public void delete(Long id) {
        repository.deleteById(id);
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
