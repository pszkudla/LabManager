package pl.visa.labmanager.container;

import org.springframework.stereotype.Service;
import pl.visa.labmanager.errors.ContainerNotFoundError;
import pl.visa.labmanager.errors.SubstanceNotFoundError;
import pl.visa.labmanager.errors.ZoneNotFoundError;
import pl.visa.labmanager.location.zone.Zone;
import pl.visa.labmanager.location.zone.ZoneRepository;
import pl.visa.labmanager.location.zone.ZoneService;
import pl.visa.labmanager.substance.Substance;
import pl.visa.labmanager.substance.SubstanceRepository;
import pl.visa.labmanager.substance.SubstanceService;

import java.time.zone.ZoneRulesException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContainerService {
    private final ContainerRepository containerRepository;
    private final ZoneService zoneService;
    private final SubstanceRepository substanceRepository;
    private final SubstanceService substanceService;

    public ContainerService(ContainerRepository containerRepository, ZoneRepository zoneRepository, ZoneService zoneService, SubstanceRepository substanceRepository, SubstanceService substanceService) {
        this.containerRepository = containerRepository;
        this.zoneService = zoneService;
        this.substanceRepository = substanceRepository;
        this.substanceService = substanceService;
    }

    public List<Container> getAllContainers() {
        return containerRepository.findAll();
    }

    public Optional<Container> getContainerByUuid(String uuid) {
        Optional<Container> optContainer = containerRepository.findContainerByUuid(UUID.fromString(uuid));
        return optContainer;
    }




    public String deleteByUuid(String uuid) {
        UUID containerUuid = UUID.fromString(uuid);
        Optional<Container> containerToDelete = containerRepository.findContainerByUuid(containerUuid);
        if (containerToDelete.isPresent()) {
            containerRepository.delete(containerToDelete.get());
            return "Udało się usunąć pojemnik o uuid = %s.".formatted(uuid);
        } else {
            return "Pojemnik o podanym uuid (%s) nie istnieje.".formatted(uuid);
        }
    }

    public ContainerDtoOut createContainer(ContainerDtoIn containerToCreate) {
        Zone zone = zoneService.getZoneByUuid(containerToCreate.getZoneUuid())
                .orElseThrow(
                        () -> new ZoneNotFoundError("Nie znaleziono strefy o UUID równym %s.".formatted(containerToCreate.getZoneUuid()))
                );
        Substance substance = substanceRepository.findByUuid(containerToCreate.getSubstanceUuid())
                .orElseThrow(
                        () -> new SubstanceNotFoundError("Nie znaleziono substancji o UUID równym %s.".formatted(containerToCreate.getSubstanceUuid()))
                );
        Container container = new Container();
        container.setZone(zone);
        container.setSubstance(substance);
        container.setNotes(containerToCreate.getNotes());
        container.setCapacity(containerToCreate.getCapacity());

        return containerRepository.save(container).getDtoOut();
    }


    public ContainerDtoOut getContainerByUuid(UUID uuid) {
        Container container = containerRepository.findContainerByUuid(uuid).orElseThrow(() -> {
            throw  new ContainerNotFoundError("Nie znaleziono pojemnika o UUID równym %s.".formatted(uuid));
        });
        return container.getDtoOut();
    }







}
