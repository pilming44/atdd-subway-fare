package nextstep.subway.path.domain;

import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.Section;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class PathSimulator {
    public static LocalDateTime getFastestDepart(LocalDateTime requestedTime, Section sourceSection) {
        Line line = sourceSection.getLine();
        List<Section> sectionList = line.getSections().getSectionList();

        Long durationTime = 0L;

        for (Section currentSection : sectionList) {
            if (currentSection.getUpStation().equals(sourceSection.getUpStation())) {
                break;
            }
            durationTime += currentSection.getDuration();
        }

        for (long i = 0; i < 1440L / line.getIntervalTime().toMinutes(); i++) {
            LocalTime calculatedLocalDate = line.getStartTime()
                    .plusMinutes((line.getIntervalTime().toMinutes() * i) + durationTime);

            LocalDateTime todayLastTrain = LocalDateTime.of(requestedTime.toLocalDate(), line.getEndTime());

            LocalDateTime calculatedLocalDateTime = LocalDateTime.of(requestedTime.toLocalDate(), calculatedLocalDate);

            if (calculatedLocalDateTime.isAfter(todayLastTrain)) {
                return LocalDateTime.of(requestedTime.toLocalDate().plusDays(1), line.getStartTime()).plusMinutes(durationTime);
            }

            if (calculatedLocalDateTime.isAfter(requestedTime) || calculatedLocalDateTime.isEqual(requestedTime)) {
                return calculatedLocalDateTime;
            }
        }
        //TODO 예외처리
        return null;
    }
}
