package com.wagle.wagleserver.service;

import com.wagle.wagleserver.entity.Location;
import com.wagle.wagleserver.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Optional<Location> getLocation(Long locationId) {
        return locationRepository.findById(locationId);
    }

    public void deleteLocation(Long locationId) {
        locationRepository.deleteById(locationId);
    }
}