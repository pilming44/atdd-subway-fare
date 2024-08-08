package nextstep.subway.path.application;

import lombok.RequiredArgsConstructor;
import nextstep.subway.exception.IllegalPathException;
import nextstep.subway.exception.NoSuchStationException;
import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.LineRepository;
import nextstep.subway.path.application.dto.PathRequest;
import nextstep.subway.path.application.dto.PathResponse;
import nextstep.subway.path.domain.DijkstraShortestPathFinder;
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

    public PathResponse getPathOrThrow(PathRequest pathRequest) {
        if (pathRequest.getSource() == null || pathRequest.getTarget() == null) {
            throw new IllegalPathException("경로를 찾을수 없습니다.");
        }

        Station sourceStation = getStation(pathRequest.getSource());
        Station targetStation = getStation(pathRequest.getTarget());

        List<Line> allLines = lineRepository.findAll();

        PathFinderBuilder pathFinderBuilder = DijkstraShortestPathFinder.searchBuilder();

        allLines.forEach(l -> pathFinderBuilder
                        .addPath(l, pathRequest.getType())
                );

        return pathFinderBuilder
                .setSource(sourceStation)
                .setTarget(targetStation)
                .find();
    }

    private Station getStation(Long stationId) {
        return stationRepository.findById(stationId)
                .orElseThrow(() -> new NoSuchStationException());
    }
}
