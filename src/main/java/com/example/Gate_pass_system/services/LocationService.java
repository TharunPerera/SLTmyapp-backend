package com.example.Gate_pass_system.services;

import com.example.Gate_pass_system.DTO.LocationDTO;
import com.example.Gate_pass_system.entity.Location;
import com.example.Gate_pass_system.repo.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;

    public LocationDTO createLocation(LocationDTO dto) {
        if (locationRepository.existsByLocationName(dto.getLocationName())) {
            throw new RuntimeException("Location already exists: " + dto.getLocationName());
        }
        Location location = new Location();
        location.setLocationName(dto.getLocationName());
        Location savedLocation = locationRepository.save(location);
        dto.setLocationId(savedLocation.getLocationId());
        return dto;
    }

    public List<LocationDTO> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(location -> {
                    LocationDTO dto = new LocationDTO();
                    dto.setLocationId(location.getLocationId());
                    dto.setLocationName(location.getLocationName());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public LocationDTO getLocationById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with ID: " + id));
        LocationDTO dto = new LocationDTO();
        dto.setLocationId(location.getLocationId());
        dto.setLocationName(location.getLocationName());
        return dto;
    }

    public LocationDTO updateLocation(Long id, LocationDTO dto) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found with ID: " + id));
        location.setLocationName(dto.getLocationName());
        Location updatedLocation = locationRepository.save(location);
        dto.setLocationId(updatedLocation.getLocationId());
        return dto;
    }

    public void deleteLocation(Long id) {
        if (!locationRepository.existsById(id)) {
            throw new RuntimeException("Location not found with ID: " + id);
        }
        locationRepository.deleteById(id);
    }
}