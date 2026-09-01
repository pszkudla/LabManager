package pl.visa.labmanager.container;

import org.springframework.stereotype.Service;
import pl.visa.labmanager.location.zone.ZoneRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContainerService {
    private final ContainerRepository containerRepository;

    public ContainerService(ContainerRepository containerRepository, ZoneRepository zoneRepository) {
        this.containerRepository = containerRepository;
    }

    public List<Container> getAllContainers() {
        return containerRepository.findAll();
    }

    public Optional<Container> getContainerByUuid(String uuid) {
        Optional<Container> optContainer = containerRepository.findContainerByUuid(UUID.fromString(uuid));
        return optContainer;
    }


    public void addContainer(Container container) {
        Container addedContainer = new Container();
        addedContainer.setCapacity(container.getCapacity());
        addedContainer.setNotes(container.getNotes());
        addedContainer.setSubstance(container.getSubstance());
        addedContainer.setZone(container.getZone());
        containerRepository.save(addedContainer);
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







}
