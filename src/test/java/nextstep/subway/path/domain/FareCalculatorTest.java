package nextstep.subway.path.domain;

import nextstep.subway.member.domain.AgeGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("지하철 요금 계산")
class FareCalculatorTest extends FareContext {

    @DisplayName("비회원 10km 이하 요금조회")
    @Test
    void 비회원_기본_요금() {
        FareCalculator fareCalculator = new FareCalculator();

        FareCondition 비회원_교대_판교_요금_조회_조건 = 요금_조회_조건(비회원_교대_판교_최단거리_조회_결과, AgeGroup.NON_AGED);

        // when
        Long calFare = fareCalculator.calculate(비회원_교대_판교_요금_조회_조건);

        // then
        assertThat(calFare).isEqualTo(1250L);
    }

    @DisplayName("비회원 10km초과 50km 이하 요금조회")
    @Test
    void 비회원_10km_초과_50km_이하_요금() {
        FareCalculator fareCalculator = new FareCalculator();

        FareCondition 비회원_교대_양재_요금_조회_조건 = 요금_조회_조건(비회원_교대_양재_최소시간_조회_결과, AgeGroup.NON_AGED);

        // when
        Long calFare = fareCalculator.calculate(비회원_교대_양재_요금_조회_조건);

        // then
        assertThat(calFare).isEqualTo(1450L);
    }

    @DisplayName("비회원 50km초과 요금조회")
    @Test
    void 비회원_50km_초과_요금() {
        FareCalculator fareCalculator = new FareCalculator();

        FareCondition 비회원_교대_천당_요금_조회_조건 = 요금_조회_조건(비회원_교대_천당_최단거리_조회_결과, AgeGroup.NON_AGED);

        // when
        Long calFare = fareCalculator.calculate(비회원_교대_천당_요금_조회_조건);

        // then
        assertThat(calFare).isEqualTo(4650L);
    }

    @DisplayName("청소년 요금 조회")
    @Test
    void 청소년_요금_조회() {
        FareCalculator fareCalculator = new FareCalculator();

        FareCondition 청소년_교대_천당_요금_조회_조건 = 요금_조회_조건(회원_교대_천당_최단거리_조회_결과, AgeGroup.TEENAGER);

        // when
        Long calFare = fareCalculator.calculate(청소년_교대_천당_요금_조회_조건);

        // then
        assertThat(calFare).isEqualTo(3440L);
    }

    @DisplayName("어린이 요금 조회")
    @Test
    void 어린이_요금_조회() {
        FareCalculator fareCalculator = new FareCalculator();

        FareCondition 어린이_교대_판교_요금_조회_조건 = 요금_조회_조건(회원_교대_판교_최단거리_조회_결과, AgeGroup.CHILD);

        // when
        Long calFare = fareCalculator.calculate(어린이_교대_판교_요금_조회_조건);

        // then
        assertThat(calFare).isEqualTo(450L);
    }

    @DisplayName("성인 요금 조회")
    @Test
    void 성인_요금_조회() {
        FareCalculator fareCalculator = new FareCalculator();

        FareCondition 성인_교대_양재_요금_조회_조건 = 요금_조회_조건(회원_교대_양재_최단거리_조회_결과, AgeGroup.ADULT);

        // when
        Long calFare = fareCalculator.calculate(성인_교대_양재_요금_조회_조건);

        // then
        assertThat(calFare).isEqualTo(1250L);
    }
}
