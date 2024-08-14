package nextstep.subway.path.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nextstep.subway.line.domain.Section;
import org.jgrapht.graph.DefaultWeightedEdge;

@Getter
@AllArgsConstructor
public class CustomWeightedEdge extends DefaultWeightedEdge {
    private PathSearchType type;
    private Section section;

    public Long getDuration() {
        return this.section.getDuration();
    }

    public Long getDistance() {
        return this.section.getDistance();
    }

    @Override
    protected double getWeight() {
        if (type.isDuration()) {
            return section.getDuration().doubleValue();
        }
        return section.getDistance().doubleValue();
    }
}
