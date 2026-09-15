package pl.visa.labmanager.location.cabinet;

import org.springframework.stereotype.Service;
import pl.visa.labmanager.errors.ResourceNotFoundException;
import pl.visa.labmanager.location.lab.LabRepository;
import pl.visa.labmanager.location.lab.Laboratory;

import java.util.List;
import java.util.Map;
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
        return cabinetRepository.findAll();
    }

    public List<CabinetDtoOut> getAllCabinetDtos() {
        return this.cabinetRepository.findAll().stream()
                .map(Cabinet::getDtoOut).toList();
    }

    public CabinetDtoOut getCabinetDtoByUuid(UUID uuid) {
        Cabinet cabinet = cabinetRepository.getCabinetByUuid(uuid).orElseThrow(() -> new ResourceNotFoundException("Nie udało się znaleźć szafki o UUID = %s przy próbie uruchomienia metody CabinetService.getCabinetDtoByUuid.".formatted(uuid)));
        return cabinet.getDtoOut();
    }

    public Optional<Cabinet> findCabinetByUuid(UUID uuid) {
        return cabinetRepository.getCabinetByUuid(uuid);
    };

    public CabinetDtoOut addCabinet(Map<String, String> map) {
        Cabinet cabinetToAdd = new Cabinet();
        Laboratory lab = labRepository
                .getLabFromUuid(UUID.fromString(map.get("labUuid")))
                .orElseThrow(() -> new ResourceNotFoundException("Nie udało się znaleźć laboratorium o UUID = %s przy próbie dodania szefy.".formatted(map.get("labUuid"))));
        cabinetToAdd.setLaboratory(lab);
        cabinetToAdd.setCabinetName(map.get("cabinetName"));
        Cabinet addedCabinet = cabinetRepository.save(cabinetToAdd);
        return addedCabinet.getDtoOut();
    }





    public void deleteByUuid(UUID uuid) {
        Cabinet cabinet = cabinetRepository.getCabinetByUuid(uuid).orElseThrow(() -> new ResourceNotFoundException("Nie udało się znaleźć szafy o UUID = %s podczas próby jej usuwania.".formatted(uuid)));

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


    public List<Cabinet> getAllCabinetsInLab(UUID labUuid) {
        Laboratory lab = labRepository.getLabFromUuid(labUuid).orElseThrow(() -> new ResourceNotFoundException("Nie odnaleziono laboratorium o UUID = %s przy próbie wylistowania wszystkich szaf w nim.".formatted(labUuid)));
        return cabinetRepository.getAllCabinetsInLab(lab);
    }



}
