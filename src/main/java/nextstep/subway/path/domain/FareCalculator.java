package nextstep.subway.path.domain;

import nextstep.subway.path.domain.policy.*;

import java.util.ArrayList;
import java.util.List;

public class FareCalculator {
    private List<FarePolicy> addPolicies = new ArrayList<>();
    private List<FarePolicy> discountPolicies = new ArrayList<>();

    public FareCalculator() {
        addPolicies.add(new DefaultFarePolicy());  // 기본 운임 정책
        addPolicies.add(new OverDistanceFarePolicy());  // 거리 초과 정책
        addPolicies.add(new AddedFarePolicy());  // 노선별 추가요금 정책

        discountPolicies.add(new AgeGroupFarePolicy());// 연령별 할인 정책
    }

    public Long calculate(FareCondition fareCondition) {
        Long fare = 0L;
        for (FarePolicy policy : addPolicies) {
            fare += policy.apply(fareCondition, fare);
        }

        for (FarePolicy policy : discountPolicies) {
            fare = policy.apply(fareCondition, fare);
        }
        return fare;
    }
}
