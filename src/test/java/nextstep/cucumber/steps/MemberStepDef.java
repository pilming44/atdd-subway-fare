package nextstep.cucumber.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.cucumber.AcceptanceContext;
import nextstep.subway.member.application.dto.MemberResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static nextstep.subway.utils.MemberSteps.*;

public class MemberStepDef {
    @Autowired
    private AcceptanceContext context;

    @Given("회원을 생성하고")
    public void 회원_생성(DataTable table) {
        List<Map<String, String>> maps = table.asMaps();
        maps.forEach(it -> {
            String email = it.get("email").toString();
            String password = it.get("password").toString();
            int age = Integer.parseInt(it.get("age"));

            ExtractableResponse<Response> 회원_생성_응답 = 회원_생성_요청(email, password, age);

            ExtractableResponse<Response> 회원_정보_조회_응답 = 회원_정보_조회_요청(회원_생성_응답);

            context.store.put(email, (new ObjectMapper()).convertValue(회원_정보_조회_응답.jsonPath().get(), MemberResponse.class));
        });
    }

    @Given("이메일 {string}과 비밀번호 {string}로 로그인하고")
    public void 회원_생성(String email, String password) {
        String 로그인_토큰 = 로그인_토큰_생성(email, password);
        context.store.put("accessToken", 로그인_토큰);
    }
}
