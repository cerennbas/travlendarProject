package com.travlendar.springtravlendar.service;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.Duration;
import com.google.maps.model.TravelMode;
import org.joda.time.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service("googleMapsService")
public class GoogleMapsService {
    private String apiKey;

    public GoogleMapsService() {
        this.apiKey = "AIzaSyBpBLZwAN1NR4BZb5AtnUUqGzkwWLHdj2U";
    }

    public String travelTime(String origin, String destination) throws Exception {
        long minTravelTime = Long.MAX_VALUE;

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();

        DirectionsApiRequest directions = DirectionsApi
                .getDirections(context, origin, destination)
                .mode(TravelMode.DRIVING);

        DirectionsResult directionsResult = directions.await();

        DirectionsRoute[] routes = directionsResult.routes;

        return routes[0].legs[0].duration.humanReadable;
    }

    private DirectionsResult getDirections(String origin, String destination) throws Exception {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();

        DirectionsApiRequest directions = DirectionsApi
                .getDirections(context, origin, destination)
                .mode(TravelMode.DRIVING)
                .mode(TravelMode.BICYCLING);

        return directions.await();
    }
}
