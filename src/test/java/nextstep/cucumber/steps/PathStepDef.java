package nextstep.cucumber.steps;

import io.cucumber.java.en.Given;
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
}
