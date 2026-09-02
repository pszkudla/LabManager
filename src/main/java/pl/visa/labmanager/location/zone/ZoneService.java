package pl.visa.labmanager.location.zone;

import org.springframework.stereotype.Service;
import pl.visa.labmanager.errors.ResourceNotFoundException;
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

    public ZoneDtoOut getZoneDtoById(UUID uuid) {
        Zone zone = zoneRepository.findByUuid(uuid).orElseThrow(() -> {
            throw new ResourceNotFoundException("Strefa o podanym UUID nie istnieje.");
        });
        return zone.getDto();
    }

    public void addZone(Zone zone) {
        zoneRepository.save(zone);
    }

    public List<Zone> getAllZones() {
        return zoneRepository.findAll();
    }


    public void deleteZoneByUuid(UUID uuid) {
        Zone zone = zoneRepository.findByUuid(uuid).orElseThrow(() -> {
            throw new ResourceNotFoundException("Nie znaleziono strefy o UUID równym %s przy próbie usuwania.".formatted(uuid));
        });
        zoneRepository.delete(zone);
    }

    public Zone editZone(ZoneDtoIn dto) {
        Shelf shelf = shelvesRepository.getShelfByUuid(dto.getShelfUuid()).orElseThrow(() -> {
            throw new ResourceNotFoundException("Nie znaleziono półki o podanym UUID podczas próby edycji strefy.");
        });

        Zone zone = zoneRepository.findByUuid(dto.getUuid()).orElseThrow(() -> {
            throw new ResourceNotFoundException("Nie odnaleziono strefy o podany UUID podczas próby edycji strefy.");
        });

        zone.setShelf(shelf);
        zone.setZoneName(dto.getZoneName());
        zoneRepository.save(zone);
        return zone;

    }






}
