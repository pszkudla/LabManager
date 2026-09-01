package pl.visa.labmanager.location.shelves;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShelvesService {
    private final ShelvesRepository shelvesRepository;

    public ShelvesService(ShelvesRepository shelvesRepository) {
        this.shelvesRepository = shelvesRepository;
    }

    public List<Shelf> getAllShelves() {
        return shelvesRepository.findAll();
    }

    public Optional<Shelf> findShelfByUuid(UUID uuid) {
        return shelvesRepository.getShelfByUuid(uuid);
    }


    public void addShelf(Shelf shelf) {
        shelvesRepository.save(shelf);
    }


}
