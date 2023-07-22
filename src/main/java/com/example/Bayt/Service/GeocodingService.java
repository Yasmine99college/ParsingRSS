package com.example.Bayt.Service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import org.springframework.stereotype.Service;

@Service
public class GeocodingService {

    private final GeoApiContext geoApiContext;

    public GeocodingService() {
        // Initialize the GeoApiContext with your Google Geocoding API key
        geoApiContext = new GeoApiContext.Builder()
                .apiKey("AIzaSyAPq3I83iYEmb4OAL223HrNcV0POxj1e3Q")
                .build();
    }

    public GeocodingResult[] geocode(String location) {
        try {
            return GeocodingApi.geocode(geoApiContext, location).await();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
