package nextstep.cucumber.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java8.En;
import nextstep.cucumber.AcceptanceContext;
import nextstep.subway.path.application.dto.PathRequest;
import nextstep.subway.path.domain.PathSearchType;
import nextstep.subway.station.application.dto.StationResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static nextstep.subway.utils.AcceptanceTestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;

public class PathStepDef implements En {
    @Autowired
    private AcceptanceContext context;

    @When("비회원이 {string}과 {string}의 최단 거리 기준으로 경로를 조회하면")
    public void 비회원_두_역의_최단_거리_경로_조회(String source, String target) {
        Long sourceId = ((StationResponse) context.store.get(source)).getId();
        Long targetId = ((StationResponse) context.store.get(target)).getId();

        PathRequest pathRequest = new PathRequest(sourceId, targetId, PathSearchType.DISTANCE);

        String accessToken = getAccessToken();

        context.response = 노선_경로_조회("", pathRequest);
    }

    @When("비회원이 {string}과 {string}의 최소 시간 기준으로 경로를 조회하면")
    public void 비회원_두_역의_최소_시간_경로_조회(String source, String target) {
        Long sourceId = ((StationResponse) context.store.get(source)).getId();
        Long targetId = ((StationResponse) context.store.get(target)).getId();
        PathRequest pathRequest = new PathRequest(sourceId, targetId, PathSearchType.DURATION);

        String accessToken = getAccessToken();

        context.response = 노선_경로_조회("", pathRequest);
    }

    @When("회원이 {string}과 {string}의 최단 거리 기준으로 경로를 조회하면")
    public void 회원_두_역의_최단_거리_경로_조회(String source, String target) {
        Long sourceId = ((StationResponse) context.store.get(source)).getId();
        Long targetId = ((StationResponse) context.store.get(target)).getId();

        PathRequest pathRequest = new PathRequest(sourceId, targetId, PathSearchType.DISTANCE);

        String accessToken = getAccessToken();

        context.response = 노선_경로_조회(accessToken, pathRequest);
    }

    @When("회원이 {string}과 {string}의 최소 시간 기준으로 경로를 조회하면")
    public void 회원_두_역의_최소_시간_경로_조회(String source, String target) {
        Long sourceId = ((StationResponse) context.store.get(source)).getId();
        Long targetId = ((StationResponse) context.store.get(target)).getId();
        PathRequest pathRequest = new PathRequest(sourceId, targetId, PathSearchType.DURATION);

        String accessToken = getAccessToken();

        context.response = 노선_경로_조회(accessToken, pathRequest);
    }

    @When("회원이 {string}에 {string}과 {string}의 가장 빠른 도착 경로를 조회하면")
    public void 회원_두_역의_가장_빠른_도착_경로_조회(String time, String source, String target) {
        Long sourceId = ((StationResponse) context.store.get(source)).getId();
        Long targetId = ((StationResponse) context.store.get(target)).getId();
        PathRequest pathRequest = new PathRequest(time, sourceId, targetId, PathSearchType.ARRIVAL_TIME);

        String accessToken = getAccessToken();

        context.response = 노선_경로_조회(accessToken, pathRequest);
    }

    @Then("{string} 경로가 조회된다")
    public void 콤마로_구분된_경로_조회(String pathString) {
        List<String> split = List.of(pathString.split(","));
        assertThat(context.response.jsonPath().getList("stations.name", String.class)).containsExactly(split.toArray(new String[0]));
    }

    @Then("총 거리 {long} 응답한다")
    public void 총_거리_응답(long expectedDistance) {
        long distance = context.response.jsonPath().getLong("distance");
        assertThat(distance).isEqualTo(expectedDistance);
    }

    @Then("소요 시간 {long} 응답한다")
    public void 소요_시간_응답(long expectedDuration) {
        long duration = context.response.jsonPath().getLong("duration");
        assertThat(duration).isEqualTo(expectedDuration);
    }

    @Then("이용 요금 {long} 응답한다")
    public void 이용_요금_응답(long expectedFare) {
        long fare = context.response.jsonPath().getLong("fare");
        assertThat(fare).isEqualTo(expectedFare);
    }

    private String getAccessToken() {
        if (context.store.get("accessToken") != null) {
            return (String) context.store.get("accessToken");
        }
        return "";
    }
}
