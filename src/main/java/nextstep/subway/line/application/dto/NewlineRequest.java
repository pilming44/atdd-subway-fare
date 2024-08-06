package nextstep.subway.line.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NewlineRequest {
    private String name;
    private String color;
    private Long upStationId;
    private Long downStationId;
    private Long distance;
    private Long duration;

    public NewlineRequest(String name, String color, Long upStationId, Long downStationId, Long distance) {
        this.name = name;
        this.upStationId = upStationId;
        this.color = color;
        this.downStationId = downStationId;
        this.distance = distance;
    }
}
