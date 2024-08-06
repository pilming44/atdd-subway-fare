package nextstep.cucumber.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java8.En;
import io.restassured.RestAssured;
import nextstep.cucumber.AcceptanceContext;
import nextstep.subway.station.application.dto.StationResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PathStepDef implements En {
    public static final String SEARCH_TYPE_DURATION = "DURATION";
    public static final String SEARCH_TYPE_DISTANCE = "DISTANCE";

    @Autowired
    private AcceptanceContext context;

    @When("{string}과 {string}의 경로를 조회하면")
    public void 두_역의_경로_조회(String source, String target) {
        Long sourceId = ((StationResponse) context.store.get(source)).getId();
        Long targetId = ((StationResponse) context.store.get(target)).getId();
        context.response = RestAssured.given().log().all()
                .queryParam("source", sourceId)
                .queryParam("target", targetId)
                .when().get("/paths")
                .then().log().all()
                .extract();
    }

    @Then("{string} 경로가 조회된다")
    public void 콤마로_구분된_경로_조회(String pathString) {
        List<String> split = List.of(pathString.split(","));
        assertThat(context.response.jsonPath().getList("stations.name", String.class)).containsExactly(split.toArray(new String[0]));
    }

    @When("{string}과 {string}의 최단 거리 기준으로 경로를 조회하면")
    public void 두_역의_최단_거리_경로_조회(String source, String target) {
        Long sourceId = ((StationResponse) context.store.get(source)).getId();
        Long targetId = ((StationResponse) context.store.get(target)).getId();
        context.response = RestAssured.given().log().all()
                .queryParam("source", sourceId)
                .queryParam("target", targetId)
                .queryParam("type", SEARCH_TYPE_DISTANCE)
                .when().get("/paths/distance")
                .then().log().all()
                .extract();
    }

    @When("{string}과 {string}의 최소 시간 기준으로 경로를 조회하면")
    public void 두_역의_최소_시간_경로_조회(String source, String target) {
        Long sourceId = ((StationResponse) context.store.get(source)).getId();
        Long targetId = ((StationResponse) context.store.get(target)).getId();
        context.response = RestAssured.given().log().all()
                .queryParam("source", sourceId)
                .queryParam("target", targetId)
                .queryParam("type", SEARCH_TYPE_DURATION)
                .when().get("/paths/duration")
                .then().log().all()
                .extract();
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
}
