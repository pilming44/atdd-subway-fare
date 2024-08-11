package nextstep.subway.path.domain.policy;

import nextstep.subway.path.domain.FareCondition;

public class OverDistanceFarePolicy implements FarePolicy {
    private final Long BOUNDARY = 50L;

    @Override
    public Long apply(FareCondition fareCondition, Long fare) {
        Long totalDistance = fareCondition.getDistance();

        if (totalDistance <= DEFAULT_DISTANCE) {
            return 0L;
        }

        if (totalDistance <= BOUNDARY) {
            return calculateUnderBoundaryFare(totalDistance);
        }

        long extraFare = calculateUnderBoundaryFare(BOUNDARY);
        extraFare += calculateOverBoundaryFare(totalDistance);

        return extraFare;
    }

    private Long calculateUnderBoundaryFare(Long distance) {
        long extraDistance = distance - DEFAULT_DISTANCE;
        return (long) Math.ceil(extraDistance / 5.0) * 100;
    }

    private Long calculateOverBoundaryFare(Long distance) {
        long extraDistance = distance - BOUNDARY;
        return (long) Math.ceil(extraDistance / 8.0) * 100;
    }
}
