package nextstep.subway.path.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nextstep.subway.line.domain.Section;
import nextstep.subway.path.domain.strategy.PathWeightStrategy;
import org.jgrapht.graph.DefaultWeightedEdge;

@Getter
@AllArgsConstructor
public class CustomWeightedEdge extends DefaultWeightedEdge {
    private Section section;
    private PathWeightStrategy strategy;

    @Override
    protected double getWeight() {
        return strategy.getWeight(section);
    }
}
