package com.github.sats17.service;

import com.github.sats17.entity.Location;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class LocationDBService {

    public List<Location> getAll() {
        return Location.listAll();
    }

    public Location create(Location location) {
        location.persist();
        return location;
    }
}
