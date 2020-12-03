package com.hackathon.baggage.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CheckInInfo.
 */
@Entity
@Table(name = "check_in_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CheckInInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "pnr_number")
    private String pnrNumber;

    @Column(name = "check_in_status")
    private String checkInStatus;

    @OneToMany(mappedBy = "checkInInfo", fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Segment> segments = new HashSet<>();

    @OneToMany(mappedBy = "checkInInfo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<BaggageData> baggageData = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemberName() {
        return memberName;
    }

    public CheckInInfo memberName(String memberName) {
        this.memberName = memberName;
        return this;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getPnrNumber() {
        return pnrNumber;
    }

    public CheckInInfo pnrNumber(String pnrNumber) {
        this.pnrNumber = pnrNumber;
        return this;
    }

    public void setPnrNumber(String pnrNumber) {
        this.pnrNumber = pnrNumber;
    }

    public String getCheckInStatus() {
        return checkInStatus;
    }

    public CheckInInfo checkInStatus(String checkInStatus) {
        this.checkInStatus = checkInStatus;
        return this;
    }

    public void setCheckInStatus(String checkInStatus) {
        this.checkInStatus = checkInStatus;
    }

    public Set<Segment> getSegments() {
        return segments;
    }

    public CheckInInfo segments(Set<Segment> segments) {
        this.segments = segments;
        return this;
    }

    public CheckInInfo addSegment(Segment segment) {
        this.segments.add(segment);
        segment.setCheckInInfo(this);
        return this;
    }

    public CheckInInfo removeSegment(Segment segment) {
        this.segments.remove(segment);
        segment.setCheckInInfo(null);
        return this;
    }

    public void setSegments(Set<Segment> segments) {
        this.segments = segments;
    }

    public Set<BaggageData> getBaggageData() {
        return baggageData;
    }

    public CheckInInfo baggageData(Set<BaggageData> baggageData) {
        this.baggageData = baggageData;
        return this;
    }

    public CheckInInfo addBaggageData(BaggageData baggageData) {
        this.baggageData.add(baggageData);
        baggageData.setCheckInInfo(this);
        return this;
    }

    public CheckInInfo removeBaggageData(BaggageData baggageData) {
        this.baggageData.remove(baggageData);
        baggageData.setCheckInInfo(null);
        return this;
    }

    public void setBaggageData(Set<BaggageData> baggageData) {
        this.baggageData = baggageData;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckInInfo)) {
            return false;
        }
        return id != null && id.equals(((CheckInInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckInInfo{" +
            "id=" + getId() +
            ", memberName='" + getMemberName() + "'" +
            ", pnrNumber='" + getPnrNumber() + "'" +
            ", checkInStatus='" + getCheckInStatus() + "'" +
            "}";
    }
}
