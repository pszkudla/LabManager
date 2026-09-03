package pl.visa.labmanager.location.cabinet;

import org.springframework.stereotype.Service;
import pl.visa.labmanager.errors.ResourceNotFoundException;
import pl.visa.labmanager.location.lab.LabRepository;
import pl.visa.labmanager.location.lab.Laboratory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CabinetService {
    private final CabinetRepository cabinetRepository;
    private final LabRepository labRepository;

    public CabinetService(CabinetRepository cabinetRepository, LabRepository labRepository) {

        this.cabinetRepository = cabinetRepository;
        this.labRepository = labRepository;
    }

    public List<Cabinet> getAllCabinets() {
        return this.cabinetRepository.findAll();
    }

    public Optional<Cabinet> findCabinetByUuid(UUID uuid) {
        return cabinetRepository.getCabinetByUuid(uuid);
    };

    public void addCabinet(Cabinet cabinet) {
        Cabinet cabinetToAdd = new Cabinet();
        cabinetToAdd.setCabinetName(cabinet.getCabinetName());
        cabinetToAdd.setLaboratory(cabinet.getLaboratory());
        cabinetRepository.save(cabinetToAdd);
    }

    public Optional<CabinetDtoOut> getDtoByUuid(UUID uuid) {
        return cabinetRepository.getCabinetByUuid(uuid).map(Cabinet::getDtoOut);
    }

    public Optional<Cabinet> deleteByUuid(UUID uuid) {
        Optional<Cabinet> cabinet = cabinetRepository.getCabinetByUuid(uuid);
        if (cabinet.isPresent()) {
            cabinetRepository.delete(cabinet.get());
        }
        return cabinet;
    }

    public CabinetDtoOut updateCabinet(CabinetDtoIn dtoIn) {
        Cabinet cabinetToEdit = cabinetRepository
                .getCabinetByUuid(dtoIn.getUuid())
                .orElseThrow(() ->
                    new ResourceNotFoundException("Nie znaleziono półki o UUID równym %s podczas próby edycji szafy.".formatted(dtoIn.getUuid()))
                );

        Laboratory lab = labRepository
                .getLabFromUuid(dtoIn.getLabUuid())
                .orElseThrow(() ->
                    new ResourceNotFoundException("Nie znaleziono laboratorium o podanym UUID równym %s podczas próby edycji szafy.".formatted(dtoIn.getLabUuid()))
        );

        cabinetToEdit.setLaboratory(lab);
        cabinetToEdit.setCabinetName(dtoIn.getCabinetName());
        return cabinetRepository.save(cabinetToEdit).getDtoOut();
    }


}
