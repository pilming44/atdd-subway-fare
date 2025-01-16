package nextstep.subway.path.application;

import lombok.RequiredArgsConstructor;
import nextstep.subway.auth.application.User;
import nextstep.subway.exception.IllegalPathException;
import nextstep.subway.exception.NoSuchStationException;
import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.LineRepository;
import nextstep.subway.line.domain.Section;
import nextstep.subway.member.application.MemberService;
import nextstep.subway.member.domain.AgeGroup;
import nextstep.subway.member.domain.Member;
import nextstep.subway.path.application.dto.PathRequest;
import nextstep.subway.path.application.dto.PathResponse;
import nextstep.subway.path.domain.*;
import nextstep.subway.station.application.dto.StationResponse;
import nextstep.subway.station.domain.Station;
import nextstep.subway.station.domain.StationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PathService {
    private final LineRepository lineRepository;
    private final StationRepository stationRepository;
    private final MemberService memberService;
    private final FareCalculator fareCalculator = new FareCalculator();

    public PathResponse getPathOrThrow(User user, PathRequest pathRequest) {
        if (pathRequest.getSource() == null || pathRequest.getTarget() == null) {
            throw new IllegalPathException("경로를 찾을수 없습니다.");
        }

        Station sourceStation = getStation(pathRequest.getSource());
        Station targetStation = getStation(pathRequest.getTarget());

        List<Line> allLines = lineRepository.findAll();
        PathFinderResult pathFinderResult = null;
        if (pathRequest.getType() == PathSearchType.ARRIVAL_TIME) {
            pathFinderResult =JgraphPathFinder.searchBuilder().addAllPath(allLines, pathRequest.getType())
                    .setSource(sourceStation)
                    .setTarget(targetStation)
                    .find();
        } else {
            pathFinderResult = DijkstraShortestPathFinder.searchBuilder()
                    .addAllPath(allLines, pathRequest.getType())
                    .setSource(sourceStation)
                    .setTarget(targetStation)
                    .find();
        }


        List<StationResponse> stations = pathFinderResult.getStations();

        FareCondition fareCondition = makeFareCondition(pathFinderResult, user);

        Long totalDuration = pathFinderResult.getSections().getTotalDuration();

        Long fare = fareCalculator.calculate(fareCondition);

        return PathResponse.of(stations, fareCondition, totalDuration, fare);
    }

    private FareCondition makeFareCondition(PathFinderResult pathFinderResult, User user) {
        Long totalDistance = pathFinderResult.getSections().getTotalDistance();

        List<Line> lines = extractLineInfo(pathFinderResult);
        AgeGroup userAgeGroup = getUserAgeGroup(user);

        return new FareCondition(lines, totalDistance, userAgeGroup);
    }


    private Station getStation(Long stationId) {
        return stationRepository.findById(stationId)
                .orElseThrow(() -> new NoSuchStationException());
    }

    private AgeGroup getUserAgeGroup(User user) {
        if (user.getEmail().isPresent()) {
            Member member = memberService.findMemberByEmailOrThrow(user.getEmail().get());
            return member.getAgeGroup();
        }
        return AgeGroup.NON_AGED;
    }

    private List<Line> extractLineInfo(PathFinderResult pathFinderResult) {
        return pathFinderResult.getSections().getSectionList().stream()
                .map(Section::getLine)
                .distinct()
                .collect(Collectors.toList());
    }
}
