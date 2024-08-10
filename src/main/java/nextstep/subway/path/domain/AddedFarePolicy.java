package nextstep.subway.path.domain;

import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.Section;

import java.util.List;
import java.util.stream.Collectors;

public class AddedFarePolicy implements FarePolicy {
    @Override
    public Long apply(PathFinderResult pathFinderResult) {
        List<Line> lines = extractLineInfo(pathFinderResult);

        return lines.stream()
                .mapToLong(Line::getAddedFare)
                .max()
                .orElse(0L);
    }

    private List<Line> extractLineInfo(PathFinderResult pathFinderResult) {
        return pathFinderResult.getSections().getSectionList().stream()
                .map(Section::getLine)
                .distinct()
                .collect(Collectors.toList());
    }
}
