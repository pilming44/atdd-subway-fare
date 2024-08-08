package nextstep.cucumber.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java8.En;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.cucumber.AcceptanceContext;
import nextstep.subway.station.application.dto.StationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nextstep.subway.utils.AcceptanceTestUtil.역_목록_조회;
import static nextstep.subway.utils.AcceptanceTestUtil.역_생성;
import static org.assertj.core.api.Assertions.assertThat;

public class StationStepDef implements En {
    @Autowired
    private AcceptanceContext context;
    ExtractableResponse<Response> response;

    @Given("지하철역들을 생성 요청하고")
    public void 지하철_역들_생성_요청(DataTable table) {
        List<Map<String, String>> maps = table.asMaps();
        maps.stream()
                .forEach(params -> {
                    ExtractableResponse<Response> response = 역_생성(params.get("name"));
                    context.store.put(params.get("name")
                            , (new ObjectMapper()).convertValue(response.jsonPath().get(), StationResponse.class));
                });
    }

    @When("지하철역을 생성하면")
    public void 지하철역_생성() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "강남역");
        response = 역_생성(params.get("name"));
    }

    @Then("지하철역이 생성된다")
    public void 지하철역_생성됨() {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Then("지하철역 목록 조회 시 생성한 역을 찾을 수 있다")
    public void 지하철역_목록_조회() {
        List<String> stationNames = 역_목록_조회().jsonPath().getList("name", String.class);
        assertThat(stationNames).containsAnyOf("강남역");
    }

}
