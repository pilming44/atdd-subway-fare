package nextstep.subway.path.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nextstep.subway.member.domain.AgeGroup;

@Getter
public class FareCondition {
    private PathFinderResult pathFinderResult;
    private AgeGroup ageGroup;
    private Long fare = 0L;

    public FareCondition(PathFinderResult pathFinderResult, AgeGroup ageGroup) {
        this.pathFinderResult = pathFinderResult;
        this.ageGroup = ageGroup;
    }

    public void addFare(Long fare) {
        this.fare += fare;
    }

    public void discountFare(Long fare) {
        this.fare = fare;
    }
}
