package nextstep.subway.path.application;

import lombok.RequiredArgsConstructor;
import nextstep.subway.exception.IllegalPathException;
import nextstep.subway.exception.NoSuchStationException;
import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.LineRepository;
import nextstep.subway.path.application.dto.PathRequest;
import nextstep.subway.path.application.dto.PathResponse;
import nextstep.subway.path.domain.DijkstraShortestPathFinder;
import nextstep.subway.path.domain.FareCalculator;
import nextstep.subway.path.domain.PathFinderBuilder;
import nextstep.subway.station.domain.Station;
import nextstep.subway.station.domain.StationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PathService {
    private final LineRepository lineRepository;
    private final StationRepository stationRepository;
    private final FareCalculator fareCalculator = new FareCalculator();

    public PathResponse getPathOrThrow(PathRequest pathRequest) {
        if (pathRequest.getSource() == null || pathRequest.getTarget() == null) {
            throw new IllegalPathException("경로를 찾을수 없습니다.");
        }

        Station sourceStation = getStation(pathRequest.getSource());
        Station targetStation = getStation(pathRequest.getTarget());

        List<Line> allLines = lineRepository.findAll();

        PathResponse pathResponse = DijkstraShortestPathFinder.searchBuilder()
                .addAllPath(allLines, pathRequest.getType())
                .setSource(sourceStation)
                .setTarget(targetStation)
                .find();

        pathResponse.setFare(fareCalculator.getFare(pathResponse.getDistance()));

        return pathResponse;
    }

    private Station getStation(Long stationId) {
        return stationRepository.findById(stationId)
                .orElseThrow(() -> new NoSuchStationException());
    }
}
