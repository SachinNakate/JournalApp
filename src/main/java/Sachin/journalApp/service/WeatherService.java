package Sachin.journalApp.service;

import Sachin.journalApp.api.reponse.WeatherResponse;
import Sachin.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    @Value("${weather.api.key}") // yml final mein key save ki hai
    private String APIKEY ;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

//    @Autowired
//    private WeatherResponse weatherResponse;

    @Autowired
    private WeatherService weatherService;
    public WeatherResponse getWeather(String city){
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if(weatherResponse !=null){
            return weatherResponse;
        }else {
            String finalAPI = appCache.APP_CACHE.get("weather_api").replace("<city>", city).replace("<APIKEY>", APIKEY);
            ResponseEntity<WeatherResponse> response= restTemplate.exchange(finalAPI, HttpMethod.GET,null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            if(body != null){
                redisService.set("weather_of_" + city, body,300l );
            }
            return body;

        }

    }

}
