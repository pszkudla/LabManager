package pl.visa.labmanager.location.shelves;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import pl.visa.labmanager.errors.ResourceNotFoundException;
import pl.visa.labmanager.location.cabinet.Cabinet;
import pl.visa.labmanager.location.cabinet.CabinetRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShelvesService {
    private final ShelvesRepository shelvesRepository;
    private final CabinetRepository cabinetRepository;

    public ShelvesService(ShelvesRepository shelvesRepository, CabinetRepository cabinetRepository) {
        this.shelvesRepository = shelvesRepository;
        this.cabinetRepository = cabinetRepository;
    }

    public List<Shelf> getAllShelves() {
        return shelvesRepository.findAll();
    }

    public List<ShelfDtoOut> getAllShelveDTOS() {
        return shelvesRepository.findAll().stream()
                .map(Shelf::getShelfDTO)
                .toList();
    }

    public Optional<Shelf> findShelfByUuid(UUID uuid) {
        return shelvesRepository.getShelfByUuid(uuid);
    }

    public Optional<ShelfDtoOut> findShelfDtoByUuid(UUID uuid) {
        Optional<Shelf> optShelf = shelvesRepository.getShelfByUuid(uuid);
        if (optShelf.isPresent()) {
            return Optional.of(optShelf.get().getShelfDTO());
        } else {
            throw  new ResourceNotFoundException("Nie znaleziono półki o UUID = %s".formatted(uuid));
        }
    }

    public void addShelf(Shelf shelf) {
        shelvesRepository.save(shelf);
    }

    public ShelfDtoOut updateShelf(ShelfDtoIn shelfPostDto) {
        Shelf shelf = shelvesRepository
                .getShelfByUuid(shelfPostDto.getUuid())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Nie odnalziono półki o UUID równym %s.".formatted(shelfPostDto.getUuid()))
                );
        Cabinet cabinet = cabinetRepository
                .getCabinetByUuid(shelfPostDto.getCabinetUuid())
                .orElseThrow(() ->
                    new ResourceNotFoundException("Nie znaleziono szafki o UUID = %s.".formatted(shelfPostDto.getCabinetUuid()))
                );
        shelf.setCabinet(cabinet);
        shelf.setShelfName(shelfPostDto.getShelfName());
        shelvesRepository.save(shelf);
        return shelf.getShelfDTO();
    }

    public Optional<Shelf> deleteShelfByUuid(UUID uuid) {
        Optional<Shelf> shelfToRemove = shelvesRepository.getShelfByUuid(uuid);
        if (shelfToRemove.isPresent()) {
            try {
                shelvesRepository.delete(shelfToRemove.get());
                return shelfToRemove;
            } catch (DataIntegrityViolationException dive) {
                dive.printStackTrace();
                return Optional.empty();
            }
        }
        return shelfToRemove;
    }
}
