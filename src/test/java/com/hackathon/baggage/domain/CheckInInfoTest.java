package com.hackathon.baggage.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hackathon.baggage.web.rest.TestUtil;

public class CheckInInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckInInfo.class);
        CheckInInfo checkInInfo1 = new CheckInInfo();
        checkInInfo1.setId(1L);
        CheckInInfo checkInInfo2 = new CheckInInfo();
        checkInInfo2.setId(checkInInfo1.getId());
        assertThat(checkInInfo1).isEqualTo(checkInInfo2);
        checkInInfo2.setId(2L);
        assertThat(checkInInfo1).isNotEqualTo(checkInInfo2);
        checkInInfo1.setId(null);
        assertThat(checkInInfo1).isNotEqualTo(checkInInfo2);
    }
}
