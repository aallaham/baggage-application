package com.hackathon.baggage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A BaggageData.
 */
@Entity
@Table(name = "baggage_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BaggageData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "baggage_id")
    private String baggageId;

    @Column(name = "weight")
    private Long weight;

    @Column(name = "status")
    private String status;

    @Column(name = "qr_code")
    private String qrCode;

    @ManyToOne
    @JsonIgnoreProperties(value = "baggageData", allowSetters = true)
    private CheckInInfo checkInInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBaggageId() {
        return baggageId;
    }

    public BaggageData baggageId(String baggageId) {
        this.baggageId = baggageId;
        return this;
    }

    public void setBaggageId(String baggageId) {
        this.baggageId = baggageId;
    }

    public Long getWeight() {
        return weight;
    }

    public BaggageData weight(Long weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }

    public BaggageData status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQrCode() {
        return qrCode;
    }

    public BaggageData qrCode(String qrCode) {
        this.qrCode = qrCode;
        return this;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public CheckInInfo getCheckInInfo() {
        return checkInInfo;
    }

    public BaggageData checkInInfo(CheckInInfo checkInInfo) {
        this.checkInInfo = checkInInfo;
        return this;
    }

    public void setCheckInInfo(CheckInInfo checkInInfo) {
        this.checkInInfo = checkInInfo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaggageData)) {
            return false;
        }
        return id != null && id.equals(((BaggageData) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BaggageData{" +
            "id=" + getId() +
            ", baggageId='" + getBaggageId() + "'" +
            ", weight=" + getWeight() +
            ", status='" + getStatus() + "'" +
            ", qrCode='" + getQrCode() + "'" +
            "}";
    }
}
