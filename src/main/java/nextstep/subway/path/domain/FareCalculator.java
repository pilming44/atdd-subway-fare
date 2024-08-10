package nextstep.subway.path.domain;

import nextstep.subway.member.domain.AgeGroup;

import java.util.ArrayList;
import java.util.List;

public class FareCalculator {
    private List<FarePolicy> policies = new ArrayList<>();

    public FareCalculator() {
        policies.add(new DefaultFarePolicy());  // 기본 운임 정책
        policies.add(new OverDistanceFarePolicy());  // 거리 초과 정책
        policies.add(new AddedFarePolicy());  // 노선별 추가요금 정책
        policies.add(new AgeGroupFarePolicy());  // 연령별 추가요금 정책
    }

    public Long getFare(FareCondition fareCondition) {
        for (FarePolicy policy : policies) {
            policy.apply(fareCondition);
        }
        return fareCondition.getFare();
    }
}
