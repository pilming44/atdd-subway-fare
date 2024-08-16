package nextstep.subway.path.domain;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.KShortestPaths;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.Multigraph;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class study {
    @Test
    public void getKShortestPaths() {
        String source = "v3";
        String target = "v1";

        Multigraph<String, DefaultWeightedEdge> graph = new Multigraph(DefaultWeightedEdge.class);
        graph.addVertex("v1");
        graph.addVertex("v2");
        graph.addVertex("v3");

        graph.addEdge("v1", "v2");
        graph.addEdge("v2", "v3");
        graph.addEdge("v1", "v3");

        List<GraphPath> paths = new KShortestPaths(graph, 1000).getPaths(source, target);

        assertThat(paths).hasSize(2);
        paths.forEach(it -> {
            assertThat(it.getVertexList()).startsWith(source);
            assertThat(it.getVertexList()).endsWith(target);
        });
    }
}
