package nextstep.subway.path.domain;

import java.util.ArrayList;
import java.util.List;

public class FareCalculator {
    private List<FarePolicy> policies = new ArrayList<>();

    public FareCalculator() {
        policies.add(new DefaultFarePolicy());  // 기본 운임 정책
        policies.add(new OverDistanceFarePolicy());  // 거리 초과 정책
    }

    public Long getFare(Long distance) {
        Long fare = 0L;
        for (FarePolicy policy : policies) {
            fare += policy.apply(distance);
        }
        return fare;
    }
}
