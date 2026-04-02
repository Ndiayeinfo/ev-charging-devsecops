package com.groupeisi.evcharging.session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class StationClient {

    private final RestTemplate restTemplate;
    private final String stationBaseUrl;

    public StationClient(RestTemplate restTemplate,
                         @Value("${station-ms.base-url:http://localhost:8081}") String stationBaseUrl) {
        this.restTemplate = restTemplate;
        this.stationBaseUrl = stationBaseUrl;
    }

    public boolean lockCharger(Long stationId, Long chargerId) {
        String url = stationBaseUrl + "/api/stations/" + stationId + "/chargers/" + chargerId + "/lock";
        ResponseEntity<Void> response = restTemplate.postForEntity(url, null, Void.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    public boolean unlockCharger(Long stationId, Long chargerId) {
        String url = stationBaseUrl + "/api/stations/" + stationId + "/chargers/" + chargerId + "/unlock";
        ResponseEntity<Void> response = restTemplate.postForEntity(url, null, Void.class);
        return response.getStatusCode().is2xxSuccessful();
    }
}

