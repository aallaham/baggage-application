package com.hackathon.baggage.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hackathon.baggage.web.rest.TestUtil;

public class BaggageDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BaggageData.class);
        BaggageData baggageData1 = new BaggageData();
        baggageData1.setId(1L);
        BaggageData baggageData2 = new BaggageData();
        baggageData2.setId(baggageData1.getId());
        assertThat(baggageData1).isEqualTo(baggageData2);
        baggageData2.setId(2L);
        assertThat(baggageData1).isNotEqualTo(baggageData2);
        baggageData1.setId(null);
        assertThat(baggageData1).isNotEqualTo(baggageData2);
    }
}
