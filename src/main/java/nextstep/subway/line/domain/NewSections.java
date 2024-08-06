package nextstep.subway.line.domain;

import nextstep.subway.exception.IllegalSectionException;
import nextstep.subway.station.domain.Station;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Embeddable
public class NewSections {
    private final int FIRST_OR_END_SECTION_INDEX = 0;
    private final int FRONT_SECTION_INDEX = 0;
    private final int BACK_SECTION_INDEX = 1;

    @OneToMany(mappedBy = "line", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Newsection> sectionList = new ArrayList<>();

    public List<Newsection> getSectionList() {
        return Collections.unmodifiableList(sectionList);
    }

    public int getSectionListSize() {
        return sectionList.size();
    }

    public List<Station> getStations() {
        List<Station> stations = new ArrayList<>();

        if (sectionList.isEmpty()) {
            return stations;
        }

        Newsection currentSection = sectionList.get(0);
        stations.add(currentSection.getUpStation());
        stations.add(currentSection.getDownStation());

        Optional<Newsection> nextSection = findNextSection(currentSection);

        while (nextSection.isPresent()) {
            currentSection = nextSection.get();
            stations.add(currentSection.getDownStation());
            nextSection = findNextSection(currentSection);
        }

        return stations;
    }

    public boolean addableSection(Newsection newSection) {
        validateStationDuplication(newSection);
        validateLinkableSection(newSection);
        validateMidAddableDistance(newSection);
        return true;
    }

    public void addSection(Newsection section) {
        if (sectionList.isEmpty()) {
            sectionList.add(section);
            return;
        }
        Station newUpStation = section.getUpStation();
        Station newDownStation = section.getDownStation();

        Station firstUpStation = sectionList.get(0).getUpStation();
        Station lastDownStation = sectionList.get(sectionList.size() - 1).getDownStation();

        if (isFirstSection(newDownStation, firstUpStation)) {
            addSectionToFront(section);
            return;
        }

        if (isLastSection(newUpStation, lastDownStation)) {
            addSectionToEnd(section);
            return;
        }

        if (isMiddleSection(newUpStation)) {
            addSectionToMiddle(section);
            return;
        }
    }

    public void removeSectionByStation(Station station) {
        validateDeleteEmpty();
        validateDeleteOnlyOne();
        validateLineStation(station);

        removeSection(station);
    }

    private void removeSection(Station station) {
        List<Newsection> collectSection = findSectionsContainingStation(station);

        if (collectSection == null || collectSection.isEmpty()) {
            throw new IllegalSectionException("노선에 해당 역을 포한한 구간이 없습니다.");
        }

        if (collectSection.size() == 1) {
            removeSingleSection(collectSection.get(FIRST_OR_END_SECTION_INDEX));
            return;
        }

        if (collectSection.size() == 2) {
            combineFrontAndBackSection(collectSection.get(FRONT_SECTION_INDEX), collectSection.get(BACK_SECTION_INDEX));
            removeSingleSection(collectSection.get(BACK_SECTION_INDEX));
            return;
        }
    }

    private List<Newsection> findSectionsContainingStation(Station station) {
        return sectionList.stream()
                .filter(s -> s.containsStation(station))
                .collect(Collectors.toList());
    }

    private void combineFrontAndBackSection(Newsection frontSection, Newsection backSection) {
        Long distanceSum = frontSection.getDistance() + backSection.getDistance();
        Long durationSum = frontSection.getDuration() + backSection.getDuration();
        Newsection combinedSection = new Newsection(frontSection.getLine(),
                frontSection.getUpStation(),
                backSection.getDownStation(),
                distanceSum,
                durationSum);

        int frontSectionIndex = sectionList.indexOf(frontSection);

        sectionList.set(frontSectionIndex, combinedSection);
    }

    private void removeSingleSection(Newsection section) {
        sectionList.remove(section);
    }

    private boolean isFirstSection(Station newDownStation, Station firstUpStation) {
        return firstUpStation.getId() == newDownStation.getId();
    }

    private boolean isLastSection(Station newUpStation, Station lastDownStation) {
        return lastDownStation.getId() == newUpStation.getId();
    }

    private void addSectionToFront(Newsection section) {
        List<Newsection> sections = new ArrayList<>(sectionList);
        sectionList.clear();
        sectionList.add(section);
        for (Newsection s : sections) {
            sectionList.add(new Newsection(s.getLine(),
                    s.getUpStation(),
                    s.getDownStation(),
                    s.getDistance(),
                    s.getDuration()));
        }
    }

    private void addSectionToEnd(Newsection section) {
        sectionList.add(section);
    }

    private void addSectionToMiddle(Newsection section) {
        Station newUpStation = section.getUpStation();
        Station newDownStation = section.getDownStation();
        Long newDistance = section.getDistance();
        Long newDuration = section.getDuration();

        Newsection sectionByUpStation = findSectionByUpStation(section.getUpStation())
                .orElseThrow(() -> new IllegalSectionException("구간을 추가할 수 없습니다."));

        int oldIndex = sectionList.indexOf(sectionByUpStation);

        Newsection rightSection = new Newsection(section.getLine(),
                newDownStation,
                sectionByUpStation.getDownStation(),
                sectionByUpStation.getDistance() - newDistance,
                sectionByUpStation.getDuration() - newDuration);
        sectionList.set(oldIndex, rightSection);

        Newsection leftSection = new Newsection(section.getLine(),
                newUpStation,
                newDownStation,
                newDistance,
                newDuration);
        sectionList.add(oldIndex, leftSection);
    }

    private boolean isMiddleSection(Station newSectionUpStation) {
        return sectionList.stream()
                .anyMatch(sec -> sec.getUpStation().getId() == newSectionUpStation.getId());
    }

    private Optional<Newsection> findNextSection(Newsection tempSection) {
        return sectionList.stream()
                .filter(section -> section.getUpStation().equals(tempSection.getDownStation()))
                .findFirst();
    }

    private Optional<Newsection> findSectionByUpStation(Station station) {
        return sectionList.stream()
                .filter(s -> s.getUpStation().equals(station))
                .findFirst();
    }

    private void validateLineStation(Station station) {
        boolean isContaionStation = sectionList.stream()
                .anyMatch(s -> s.containsStation(station));

        if (!isContaionStation) {
            throw new IllegalSectionException("노선에 해당 역을 포한한 구간이 없습니다.");
        }
    }

    private void validateDeleteOnlyOne() {
        if (sectionList.size() == 1) {
            throw new IllegalSectionException("노선에 구간이 하나뿐이면 삭제할수없습니다.");
        }
    }

    private void validateDeleteEmpty() {
        if (sectionList.isEmpty()) {
            throw new IllegalSectionException("노선에 삭제 할 구간이 없습니다.");
        }
    }

    private void validateStationDuplication(Newsection section) {
        List<Station> stations = getStations();
        if (stations.contains(section.getUpStation()) && stations.contains(section.getDownStation())) {
            throw new IllegalSectionException("이미 등록되어 있는 역은 노선에 추가할 수 없습니다.");
        }
    }

    private void validateLinkableSection(Newsection section) {
        List<Station> stations = getStations();
        if (stations.size() != 0 && isUnlinkedSection(section)) {
            throw new IllegalSectionException("노선의 구간과 연결되지 않습니다.");
        }
    }

    private boolean isUnlinkedSection(Newsection section) {
        List<Station> stations = getStations();
        return !stations.contains(section.getUpStation()) && !stations.contains(section.getDownStation());
    }

    private void validateMidAddableDistance(Newsection section) {
        Optional<Newsection> OptionalOldSection = findSectionByUpStation(section.getUpStation());
        if (!OptionalOldSection.isPresent()) {
            return;
        }
        Newsection oldSection = OptionalOldSection.get();
        Long oldSectionDistance = oldSection.getDistance();
        Long newSectionDistance = section.getDistance();

        if (newSectionDistance <= 0 || oldSectionDistance <= newSectionDistance) {
            throw new IllegalSectionException("신규 구간 거리가 올바르지 않습니다.");
        }
    }
}
