package nextstep.subway.path.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultFarePolicyTest extends FareContext {

    @DisplayName("기본요금 계산")
    @Test
    void 기본요금_계산() {
        //given
        DefaultFarePolicy defaultFarePolicy = new DefaultFarePolicy();

        // when
        Long 교대_양재 = defaultFarePolicy.apply(교대_양재_최단거리_조회_결과);
        Long 교대_판교 = defaultFarePolicy.apply(교대_판교_최단거리_조회_결과);
        Long 교대_천당 = defaultFarePolicy.apply(교대_천당_최단거리_조회_결과);
        // then
        assertThat(교대_양재).isEqualTo(DefaultFarePolicy.DEFAULT_FARE);
        assertThat(교대_판교).isEqualTo(DefaultFarePolicy.DEFAULT_FARE);
        assertThat(교대_천당).isEqualTo(DefaultFarePolicy.DEFAULT_FARE);
    }
}