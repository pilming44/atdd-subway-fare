package nextstep.subway.path.domain;

public class OverDistanceFarePolicy implements FarePolicy{
    private final Long BOUNDARY = 50L;

    @Override
    public void apply(FareCondition fareCondition) {
        PathFinderResult pathFinderResult = fareCondition.getPathFinderResult();
        Long totalDistance = pathFinderResult.getSections().getTotalDistance();

        if(totalDistance <= DEFAULT_DISTANCE) {
            return ;
        }

        if (totalDistance <= BOUNDARY) {
            fareCondition.addFare(calculateUnderBoundaryFare(totalDistance));
            return ;
        }

        long extraFare = calculateUnderBoundaryFare(BOUNDARY);
        extraFare += calculateOverBoundaryFare(totalDistance);

        fareCondition.addFare(extraFare);
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
