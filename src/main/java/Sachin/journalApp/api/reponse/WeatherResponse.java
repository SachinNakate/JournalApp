package Sachin.journalApp.api.reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;


@Data
@Getter
@Setter
public class WeatherResponse {

    private current Current;

    @Data
    @Getter
    @Setter
    public class current{
        private int temperature;


        private List<String> weather_descriptions;

        private int feelslike;

    }


}




