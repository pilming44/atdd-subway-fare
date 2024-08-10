package nextstep.subway.path.domain;

import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.Section;

import java.util.List;
import java.util.stream.Collectors;

public class AddedFarePolicy implements FarePolicy {
    @Override
    public void apply(FareCondition fareCondition) {
        List<Line> lines = extractLineInfo(fareCondition.getPathFinderResult());

        long addedFare = lines.stream()
                .mapToLong(Line::getAddedFare)
                .max()
                .orElse(0L);

        fareCondition.addFare(addedFare);
    }

    private List<Line> extractLineInfo(PathFinderResult pathFinderResult) {
        return pathFinderResult.getSections().getSectionList().stream()
                .map(Section::getLine)
                .distinct()
                .collect(Collectors.toList());
    }
}
