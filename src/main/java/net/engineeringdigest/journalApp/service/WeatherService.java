package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeatherResponse weatherResponse;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;
    
    public WeatherResponse getWeather(String city) {
        WeatherResponse weatherResponse1 = redisService.get("weather_of_" + city, WeatherResponse.class);
        if(weatherResponse1 != null) {
            return weatherResponse1;
        } else {
            String finalAPI = appCache.App_Cache.get(AppCache.keys.WEATHER_API.toString()).replace("<CITY>", city).replace("<API_KEY>", apiKey);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            if(body != null) {
                redisService.set("weather_of_" + city, body, 300l);
            }
            return body;
        }
    }
}
