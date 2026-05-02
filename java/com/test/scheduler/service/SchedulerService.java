package com.test.scheduler.service;

import com.test.scheduler.model.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class SchedulerService {

    private final String TOKEN = "your_token"; // ⚠️ keep updating if expired

    private final String DEPOT_URL = "http://20.207.122.201/evaluation-service/depots";
    private final String VEHICLE_URL = "http://20.207.122.201/evaluation-service/vehicles";

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public List<Depot> fetchDepots() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(getHeaders());

        ResponseEntity<DepotResponse> response =
                restTemplate.exchange(DEPOT_URL, HttpMethod.GET, entity, DepotResponse.class);

        return response.getBody().getDepots();
    }

    public List<Vehicle> fetchVehicles() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(getHeaders());

        ResponseEntity<VehicleResponse> response =
                restTemplate.exchange(VEHICLE_URL, HttpMethod.GET, entity, VehicleResponse.class);

        return response.getBody().getVehicles();
    }

    public List<Map<String, Object>> generateSchedule() {

        List<Depot> depots = fetchDepots();
        List<Vehicle> vehicles = fetchVehicles();

        List<Map<String, Object>> result = new ArrayList<>();

        for (Depot depot : depots) {

            int depotId = depot.getId();
            int capacity = depot.getMechanicHours();

            List<Vehicle> selected = getBestVehicles(vehicles, capacity);

            Map<String, Object> map = new HashMap<>();
            map.put("depotId", depotId);
            map.put("vehicles", selected);

            result.add(map);
        }

        return result;
    }

    public List<Vehicle> getBestVehicles(List<Vehicle> vehicles, int capacity) {

        int n = vehicles.size();
        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {
            Vehicle v = vehicles.get(i - 1);

            for (int w = 0; w <= capacity; w++) {
                if (v.getDuration() <= w) {
                    dp[i][w] = Math.max(
                            v.getImpact() + dp[i - 1][w - v.getDuration()],
                            dp[i - 1][w]
                    );
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        List<Vehicle> result = new ArrayList<>();
        int w = capacity;

        for (int i = n; i > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                Vehicle v = vehicles.get(i - 1);
                result.add(v);
                w -= v.getDuration();
            }
        }

        Collections.reverse(result);

        return result;
    }
}