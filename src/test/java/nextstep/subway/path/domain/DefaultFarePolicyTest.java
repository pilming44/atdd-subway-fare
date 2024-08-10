package nextstep.subway.path.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("기본요금 정책 관련")
class DefaultFarePolicyTest extends FareContext {

    @DisplayName("기본요금 계산")
    @Test
    void 기본요금_계산() {
        //given
        DefaultFarePolicy defaultFarePolicy = new DefaultFarePolicy();

        // when
        defaultFarePolicy.apply(비회원_교대_양재_최단거리_조회_결과);
        defaultFarePolicy.apply(비회원_교대_판교_최단거리_조회_결과);
        defaultFarePolicy.apply(비회원_교대_천당_최단거리_조회_결과);

        // then
        Long 교대_양재 = 비회원_교대_양재_최단거리_조회_결과.getFare();
        Long 교대_판교 = 비회원_교대_판교_최단거리_조회_결과.getFare();
        Long 교대_천당 = 비회원_교대_천당_최단거리_조회_결과.getFare();
        assertThat(교대_양재).isEqualTo(DefaultFarePolicy.DEFAULT_FARE);
        assertThat(교대_판교).isEqualTo(DefaultFarePolicy.DEFAULT_FARE);
        assertThat(교대_천당).isEqualTo(DefaultFarePolicy.DEFAULT_FARE);
    }
}