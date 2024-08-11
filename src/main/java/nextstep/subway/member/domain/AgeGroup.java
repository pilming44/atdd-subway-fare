package nextstep.subway.member.domain;

import java.util.Arrays;
import java.util.function.Function;

public enum AgeGroup {
    NON_AGED(-1, -1, fare -> fare),
    BABY(0, 5, fare -> fare),
    CHILD(6, 12, fare -> (fare - 350L) * 50 / 100),
    TEENAGER(13, 18, fare -> (fare - 350L) * 80 / 100),
    ADULT(19, Integer.MAX_VALUE, fare -> fare);

    private final int minAge;
    private final int maxAge;
    private final Function<Long, Long> discountCalculator;

    AgeGroup(int minAge, int maxAge, Function<Long, Long> discountCalculator) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.discountCalculator = discountCalculator;
    }

    public static AgeGroup fromAge(int age) {
        return Arrays.stream(AgeGroup.values())
                .filter(group -> age >= group.minAge && age <= group.maxAge)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("나이값 오류 나이: " + age));
    }

    public Long applyDiscount(Long fare) {
        return discountCalculator.apply(fare);
    }

    public boolean isChild() {
        return this == CHILD;
    }

    public boolean isTeenager() {
        return this == TEENAGER;
    }
}
