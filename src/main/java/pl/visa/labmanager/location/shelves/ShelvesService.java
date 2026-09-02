package pl.visa.labmanager.location.shelves;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import pl.visa.labmanager.errors.CabinetNotFoundError;
import pl.visa.labmanager.errors.ShelfNotFoundError;
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
            throw  new ShelfNotFoundError("Nie znaleziono półki o UUID = %s".formatted(uuid));
        }
    }

    public void addShelf(Shelf shelf) {
        shelvesRepository.save(shelf);
    }

    public ShelfDtoOut updateShelf(ShelfDtoIn shelfPostDto) {
        Optional<Shelf> optShelfToEdit = shelvesRepository.getShelfByUuid(shelfPostDto.getUuid());
        Optional<Cabinet> optCabinet = cabinetRepository.getCabinetByUuid(shelfPostDto.getCabinetUuid());
        if (optCabinet.isEmpty()) {
            throw new CabinetNotFoundError("Nie znaleziono  szafki o uuid = %s.".formatted(shelfPostDto.getCabinetUuid()));
        } else if (optShelfToEdit.isEmpty()) {
            throw new ShelfNotFoundError("Nie znaleziono półki o id = %s.".formatted(shelfPostDto.getUuid()));
        } else {
            Cabinet cabinetToAdd = optCabinet.get();
            Shelf shelfToEdit = optShelfToEdit.get();
            shelfToEdit.setUuid(shelfPostDto.getUuid());
            shelfToEdit.setCabinet(cabinetToAdd);
            shelfToEdit.setShelfName(shelfPostDto.getShelfName());
            shelvesRepository.save(shelfToEdit);
            return shelfToEdit.getShelfDTO();
        }
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
