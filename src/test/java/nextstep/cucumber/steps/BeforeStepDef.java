package nextstep.cucumber.steps;

import io.cucumber.java.Before;
import io.cucumber.java8.En;
import nextstep.subway.utils.DatabaseCleanup;
import org.springframework.beans.factory.annotation.Autowired;

public class BeforeStepDef implements En {
    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Before
    public void before() {
        databaseCleanup.execute();
    }
}
