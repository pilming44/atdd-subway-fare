package nextstep.cucumber.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java8.En;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.cucumber.AcceptanceContext;
import nextstep.subway.line.application.dto.LineResponse;
import nextstep.subway.station.application.dto.StationResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static nextstep.subway.utils.AcceptanceTestUtil.*;

public class LineStepDef implements En {
    @Autowired
    private AcceptanceContext context;

    @Given("지하철 노선들을 생성 요청하고")
    public void 지하철_노선들_생성(DataTable table) {
        List<Map<String, String>> maps = table.asMaps();
        maps.stream()
                .forEach(it -> {
                    Long upStationId = ((StationResponse) context.store.get(it.get("upStation"))).getId();
                    Long downStationId = ((StationResponse) context.store.get(it.get("downStation"))).getId();

                    Map<String, Object> params = 노선_생성_매개변수(it.get("name")
                            , it.get("color")
                            , upStationId
                            , downStationId
                            , Long.parseLong(it.get("distance"))
                            , Long.parseLong(it.get("duration"))
                    );

                    ExtractableResponse<Response> response = 노선_생성_Extract(params);

                    context.store.put(params.get("name").toString(), (new ObjectMapper()).convertValue(response.jsonPath().get(), LineResponse.class));
                });
    }

    @Given("지하철 구간을 등록 요청하고")
    public void 지하철_구간_등록_요청(DataTable table) {
        List<Map<String, String>> maps = table.asMaps();
        maps.stream()
                .forEach(it -> {
                    String lineName = it.get("lineName");

                    Long upStationId = ((StationResponse) context.store.get(it.get("upStation"))).getId();
                    Long downStationId = ((StationResponse) context.store.get(it.get("downStation"))).getId();

                    Map<String, Object> params = 구간_생성_매개변수(upStationId
                            , downStationId
                            , Long.parseLong(it.get("distance"))
                            , Long.parseLong(it.get("duration"))
                    );

                    LineResponse line = (LineResponse) context.store.get(lineName);

                    노선에_새로운_구간_추가_Extract(params, line.getId());
                });
    }
}
