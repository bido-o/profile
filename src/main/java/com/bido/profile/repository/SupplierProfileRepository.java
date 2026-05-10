package com.bido.profile.repository;

import com.bido.profile.entity.SupplierProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierProfileRepository extends JpaRepository<SupplierProfile, Long> {
}

