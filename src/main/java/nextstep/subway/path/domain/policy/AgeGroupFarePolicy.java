package nextstep.subway.path.domain.policy;

import nextstep.subway.member.domain.AgeGroup;
import nextstep.subway.path.domain.FareCondition;

public class AgeGroupFarePolicy implements FarePolicy {

    @Override
    public Long apply(FareCondition fareCondition, Long fare) {
        AgeGroup ageGroup = fareCondition.getAgeGroup();

        if (ageGroup.isChild() || ageGroup.isTeenager()) {
            return ageGroup.applyDiscount(fare);
        }

        return fare;
    }
}
