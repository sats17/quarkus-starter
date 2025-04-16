package com.github.sats17.providers;

import com.github.sats17.entity.Location;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface LocationProvider extends PanacheRepository<Location> {
}
