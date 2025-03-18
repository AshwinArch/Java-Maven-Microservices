package org.example.service;

import org.example.model.StandingResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class StandingService {

    @Value("${football-api.url}")
    private String apiUrl;

    @Value("${football-api.key}")
    private String apiKey;

    @Value("${offline-mode}")
    private boolean offlineMode;

    private final OfflineDataService offlineDataService;
    private final RestTemplate restTemplate = new RestTemplate();

    public StandingService(OfflineDataService offlineDataService) {
        this.offlineDataService = offlineDataService;
    }

    @Cacheable("standings")
    public Optional<StandingResponse> getStandings(String country, String league, String team) {
        if (offlineMode) {
            return offlineDataService.getOfflineData(country, league, team);
        }

        try {
            String url = String.format("%s?APIkey=%s&action=get_standings&country=%s&league=%s&team=%s",
                    apiUrl, apiKey, country, league, team);

            StandingResponse[] response = restTemplate.getForObject(url, StandingResponse[].class);

            if (response != null && response.length > 0) {
                return Optional.of(response[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return offlineDataService.getOfflineData(country, league, team);
    }
}
