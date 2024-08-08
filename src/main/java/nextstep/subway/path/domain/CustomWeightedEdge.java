package nextstep.subway.path.domain;

import lombok.Getter;
import nextstep.subway.path.application.dto.PathSearchType;
import org.jgrapht.graph.DefaultWeightedEdge;

@Getter
public class CustomWeightedEdge extends DefaultWeightedEdge {
    private PathSearchType type;
    private Long distance;
    private Long duration;

    public CustomWeightedEdge(PathSearchType type, Long distance, Long duration) {
        this.type = type;
        this.distance = distance;
        this.duration = duration;
    }

    @Override
    protected double getWeight() {
        if (type.isDuration()) {
            return duration.doubleValue();
        }
        return distance.doubleValue();
    }
}
