package net.engineeringdigest.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
public class WeatherResponse {
    private Current current;

    @Getter
    @Setter
    public class Current {
        @JsonProperty("weather_description")
        private List<String> weatherDescription;
        private int temperature;
        private int feelslike;
    }
}