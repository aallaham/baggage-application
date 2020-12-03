package com.hackathon.baggage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Segment.
 */
@Entity
@Table(name = "segment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Segment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seat_number")
    private String seatNumber;

    @Column(name = "departure_date")
    private Instant departureDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checkInInfo_id")
    private CheckInInfo checkInInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public Segment seatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
        return this;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Instant getDepartureDate() {
        return departureDate;
    }

    public Segment departureDate(Instant departureDate) {
        this.departureDate = departureDate;
        return this;
    }

    public void setDepartureDate(Instant departureDate) {
        this.departureDate = departureDate;
    }

    public CheckInInfo getCheckInInfo() {
        return checkInInfo;
    }

    public Segment checkInInfo(CheckInInfo checkInInfo) {
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
        if (!(o instanceof Segment)) {
            return false;
        }
        return id != null && id.equals(((Segment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Segment{" +
            "id=" + getId() +
            ", seatNumber='" + getSeatNumber() + "'" +
            ", departureDate='" + getDepartureDate() + "'" +
            "}";
    }
}
