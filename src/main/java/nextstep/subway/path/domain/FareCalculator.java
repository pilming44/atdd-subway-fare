package nextstep.subway.path.domain;

import java.util.ArrayList;
import java.util.List;

public class FareCalculator {
    private List<FarePolicy> policies = new ArrayList<>();

    public FareCalculator() {
        policies.add(new DefaultFarePolicy());  // 기본 운임 정책
        policies.add(new OverDistanceFarePolicy());  // 거리 초과 정책
        policies.add(new AddedFarePolicy());  // 노선별 추가요금 정책
    }

    public Long getFare(PathFinderResult pathFinderResult) {
        Long fare = 0L;
        for (FarePolicy policy : policies) {
            fare += policy.apply(pathFinderResult);
        }
        return fare;
    }
}
