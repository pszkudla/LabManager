package pl.visa.labmanager.container;

import org.springframework.stereotype.Service;
import pl.visa.labmanager.errors.ResourceNotFoundException;
import pl.visa.labmanager.location.zone.Zone;
import pl.visa.labmanager.location.zone.ZoneRepository;
import pl.visa.labmanager.location.zone.ZoneService;
import pl.visa.labmanager.substance.Substance;
import pl.visa.labmanager.substance.SubstanceRepository;
import pl.visa.labmanager.substance.SubstanceService;

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

    public void deleteByUuid(UUID uuid) {

        Container containerToDelete = containerRepository
                .findContainerByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono pojemnika o UUID równym %s.".formatted(uuid)));
        containerRepository.delete(containerToDelete);
    }

    public ContainerDtoOut createContainer(ContainerDtoIn containerToCreate) {
        Zone zone = zoneService.getZoneByUuid(containerToCreate.getZoneUuid())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Nie znaleziono strefy o UUID równym %s.".formatted(containerToCreate.getZoneUuid()))
                );
        Substance substance = substanceRepository.findByUuid(containerToCreate.getSubstanceUuid())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Nie znaleziono substancji o UUID równym %s.".formatted(containerToCreate.getSubstanceUuid()))
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
            throw  new ResourceNotFoundException("Nie znaleziono pojemnika o UUID równym %s.".formatted(uuid));
        });
        return container.getDtoOut();
    }

    public ContainerDtoOut editContainer(ContainerDtoIn dtoIn) {
        Container container = containerRepository
                .findContainerByUuid(dtoIn.getUuid())
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono pojemnika o UUID = %s.".formatted(dtoIn.getUuid())));
        Substance substance = substanceRepository
                .findByUuid(dtoIn.getSubstanceUuid())
                .orElseThrow(() -> new ResourceNotFoundException("Nie znalazłem substancji o UUID równym %s.".formatted(dtoIn.getSubstanceUuid())));
        Zone zone = zoneService
                .getZoneByUuid(dtoIn.getZoneUuid())
                .orElseThrow(() -> new ResourceNotFoundException("Nie odnaleziono strefy o UUID równym %s.".formatted(dtoIn.getZoneUuid())));
        container.setCapacity(dtoIn.getCapacity());
        container.setNotes(dtoIn.getNotes());
        container.setSubstance(substance);
        container.setZone(zone);
        containerRepository.save(container);
        return container.getDtoOut();
    }







}
