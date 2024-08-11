package nextstep.subway.path.domain;

import nextstep.subway.member.domain.AgeGroup;
import nextstep.subway.path.domain.policy.AddedFarePolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("노선별 추가요금 정책 관련")
class AddedFarePolicyTest extends FareContext {
    @DisplayName("추가요금이 없는 노선 이용 시 추가요금 0원")
    @Test
    void 추가요금_없는_노선() {
        //given
        AddedFarePolicy addedFarePolicy = new AddedFarePolicy();

        FareCondition 비회원_교대_양재_요금_조회_조건 = 요금_조회_조건(비회원_교대_양재_최단거리_조회_결과, AgeGroup.NON_AGED);
        FareCondition 비회원_교대_판교_요금_조회_조건 = 요금_조회_조건(비회원_교대_판교_최단거리_조회_결과, AgeGroup.NON_AGED);

        // when
        Long 비회원_교대_양재_요금 = addedFarePolicy.apply(비회원_교대_양재_요금_조회_조건, 0L);

        Long 비회원_교대_판교_요금 = addedFarePolicy.apply(비회원_교대_판교_요금_조회_조건, 0L);

        // then
        assertThat(비회원_교대_양재_요금).isEqualTo(0L);
        assertThat(비회원_교대_판교_요금).isEqualTo(0L);
    }

    @DisplayName("추가요금 노선 이용 시 추가요금 부과")
    @Test
    void 추가요금_노선() {
        //given
        AddedFarePolicy addedFarePolicy = new AddedFarePolicy();

        FareCondition 비회원_교대_판교_최소시간_요금_조회_조건 = 요금_조회_조건(비회원_교대_판교_최소시간_조회_결과, AgeGroup.NON_AGED);
        FareCondition 비회원_교대_천당_최소시간_요금_조회_조건 = 요금_조회_조건(비회원_교대_천당_최소시간_조회_결과, AgeGroup.NON_AGED);

        // when
        Long 비회원_교대_판교_최소시간_요금 = addedFarePolicy.apply(비회원_교대_판교_최소시간_요금_조회_조건, 0L);

        Long 회원_교대_천당_최소시간_요금 = addedFarePolicy.apply(비회원_교대_천당_최소시간_요금_조회_조건, 0L);

        // then
        assertThat(비회원_교대_판교_최소시간_요금).isEqualTo(900L);
        assertThat(회원_교대_천당_최소시간_요금).isEqualTo(2000L);
    }
}