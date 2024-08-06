package nextstep.subway.path.application;

import nextstep.subway.exception.IllegalPathException;
import nextstep.subway.exception.NoSuchStationException;
import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.LineRepository;
import nextstep.subway.line.domain.Newline;
import nextstep.subway.line.domain.NewlineRepository;
import nextstep.subway.path.application.dto.NewPathRequest;
import nextstep.subway.path.application.dto.NewPathResponse;
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
public class PathService {
    private final LineRepository lineRepository;
    private final NewlineRepository newlineRepository;
    private final StationRepository stationRepository;

    public PathService(LineRepository lineRepository, NewlineRepository newlineRepository,StationRepository stationRepository) {
        this.lineRepository = lineRepository;
        this.newlineRepository = newlineRepository;
        this.stationRepository = stationRepository;
    }

    public PathResponse getPathOrThrow(PathRequest pathRequest) {
        if (pathRequest.getSource() == null || pathRequest.getTarget() == null) {
            throw new IllegalPathException("경로를 찾을수 없습니다.");
        }

        Station sourceStation = getStation(pathRequest.getSource());
        Station targetStation = getStation(pathRequest.getTarget());

        List<Line> allLines = lineRepository.findAll();

        PathFinderBuilder pathFinderBuilder = DijkstraShortestPathFinder.searchBuilder();

        allLines.stream()
                .forEach(l -> pathFinderBuilder
                        .addVertex(l.getStations())
                        .addEdgeWeight(l.getSections().getSectionList())
                );

        return pathFinderBuilder
                .setSource(sourceStation)
                .setTarget(targetStation)
                .find();
    }

    public NewPathResponse getDistancePathOrThrow(NewPathRequest pathRequest) {
        if (pathRequest.getSource() == null || pathRequest.getTarget() == null) {
            throw new IllegalPathException("경로를 찾을수 없습니다.");
        }

        Station sourceStation = getStation(pathRequest.getSource());
        Station targetStation = getStation(pathRequest.getTarget());

        List<Newline> allLines = newlineRepository.findAll();

        PathFinderBuilder pathFinderBuilder = DijkstraShortestPathFinder.searchBuilder();

        allLines.stream()
                .forEach(l -> pathFinderBuilder
                        .addNewVertex(l.getStations())
                        .addNewEdgeWeight(l.getSections().getSectionList(), pathRequest.getType())
                );

        return pathFinderBuilder
                .setSource(sourceStation)
                .setTarget(targetStation)
                .newfind();
    }

    public NewPathResponse getDurationPathOrThrow(NewPathRequest pathRequest) {
        if (pathRequest.getSource() == null || pathRequest.getTarget() == null) {
            throw new IllegalPathException("경로를 찾을수 없습니다.");
        }

        Station sourceStation = getStation(pathRequest.getSource());
        Station targetStation = getStation(pathRequest.getTarget());

        List<Newline> allLines = newlineRepository.findAll();

        PathFinderBuilder pathFinderBuilder = DijkstraShortestPathFinder.searchBuilder();

        allLines.stream()
                .forEach(l -> pathFinderBuilder
                        .addNewVertex(l.getStations())
                        .addNewEdgeWeight(l.getSections().getSectionList(), pathRequest.getType())
                );

        return pathFinderBuilder
                .setSource(sourceStation)
                .setTarget(targetStation)
                .newfind();
    }

    private Station getStation(Long stationId) {
        return stationRepository.findById(stationId)
                .orElseThrow(() -> new NoSuchStationException("존재하지 않는 역입니다."));
    }
}
