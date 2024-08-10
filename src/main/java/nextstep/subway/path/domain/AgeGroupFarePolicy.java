package nextstep.subway.path.domain;

import nextstep.subway.member.domain.AgeGroup;

public class AgeGroupFarePolicy implements FarePolicy {

    @Override
    public void apply(FareCondition fareCondition) {
        AgeGroup ageGroup = fareCondition.getAgeGroup();

        Long fare = fareCondition.getFare();

        if (ageGroup.isChild() || ageGroup.isTeenager()) {
            fareCondition.discountFare(ageGroup.applyDiscount(fare));
        }
    }
}
