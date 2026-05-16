package com.bido.profile.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Duration;

@Entity
@Table(name = "supplier_profiles")
public class SupplierProfile {

    @Id
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private Integer creditBalance = 0;

    @Column
    private Double minOrder = 0.0;

    @JdbcTypeCode(SqlTypes.INTERVAL_SECOND)
    @Column(columnDefinition = "interval")
    private Duration minTimePrepOrder = Duration.ofHours(1);

    @Column
    private Double avgRating;

    @Column(nullable = false)
    private Boolean acceptsOnlinePayments = false;

    @Column
    private Boolean hasLegalInfo = false;

    @Column(nullable = false)
    private Integer totalOffersWon = 0;

    @Column(nullable = false)
    private Integer totalDisputesLost = 0;

    @Column(nullable = false)
    private Integer totalOffersSubmitted = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(Integer creditBalance) {
        this.creditBalance = creditBalance;
    }

    public Double getMinOrder() {
        return minOrder;
    }

    public void setMinOrder(Double minOrder) {
        this.minOrder = minOrder;
    }

    public Duration getMinTimePrepOrder() {
        return minTimePrepOrder;
    }

    public void setMinTimePrepOrder(Duration minTimePrepOrder) {
        this.minTimePrepOrder = minTimePrepOrder;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    public Boolean getAcceptsOnlinePayments() {
        return acceptsOnlinePayments;
    }

    public void setAcceptsOnlinePayments(Boolean acceptsOnlinePayments) {
        this.acceptsOnlinePayments = acceptsOnlinePayments;
    }

    public Boolean getHasLegalInfo() {
        return hasLegalInfo;
    }

    public void setHasLegalInfo(Boolean hasLegalInfo) {
        this.hasLegalInfo = hasLegalInfo;
    }

    public Integer getTotalOffersWon() {
        return totalOffersWon;
    }

    public void setTotalOffersWon(Integer totalOffersWon) {
        this.totalOffersWon = totalOffersWon;
    }

    public Integer getTotalDisputesLost() {
        return totalDisputesLost;
    }

    public void setTotalDisputesLost(Integer totalDisputesLost) {
        this.totalDisputesLost = totalDisputesLost;
    }

    public Integer getTotalOffersSubmitted() {
        return totalOffersSubmitted;
    }

    public void setTotalOffersSubmitted(Integer totalOffersSubmitted) {
        this.totalOffersSubmitted = totalOffersSubmitted;
    }
}
