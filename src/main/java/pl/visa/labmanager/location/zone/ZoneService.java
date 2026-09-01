package pl.visa.labmanager.location.zone;

import org.springframework.stereotype.Service;
import pl.visa.labmanager.location.shelves.Shelf;
import pl.visa.labmanager.location.shelves.ShelvesRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ZoneService {
    private final ZoneRepository zoneRepository;
    private final ShelvesRepository shelvesRepository;

    public ZoneService(ZoneRepository zoneRepository, ShelvesRepository shelvesRepository) {
        this.shelvesRepository = shelvesRepository;
        this.zoneRepository = zoneRepository;
    }

    public Optional<Zone> getZoneByUuid(UUID uuid) {
        return zoneRepository.findByUuid(uuid);
    }

    public void addZone(Zone zone) {
        zoneRepository.save(zone);
    }

    public List<Zone> getAllZones() {
        return zoneRepository.findAll();
    }





}
