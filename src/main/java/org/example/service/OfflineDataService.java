package org.example.service;

import org.example.model.StandingResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class OfflineDataService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Optional<StandingResponse> getOfflineData(String country, String league, String team) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("offline-data.json")) {
            if (inputStream == null) {
                System.out.println("Offline data file not found!");
                return Optional.empty();
            }

            List<StandingResponse> data = objectMapper.readValue(inputStream, new TypeReference<List<StandingResponse>>() {});

            return data.stream().filter(entry -> entry.getCountryName().equalsIgnoreCase(country)
                    && entry.getLeagueName().equalsIgnoreCase(league)
                    && entry.getTeamName().equalsIgnoreCase(team)).findFirst();
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}

