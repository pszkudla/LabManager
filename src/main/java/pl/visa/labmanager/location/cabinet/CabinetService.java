package pl.visa.labmanager.location.cabinet;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CabinetService {
    private final CabinetRepository cabinetRepository;

    public CabinetService(CabinetRepository cabinetRepository) {
        this.cabinetRepository = cabinetRepository;
    }

    public List<Cabinet> getAllCabinets() {
        return this.cabinetRepository.findAll();
    }

    public void addCabinet(Cabinet cabinet) {
        Cabinet cabinetToAdd = new Cabinet();
        cabinetToAdd.setCabinetName(cabinet.getCabinetName());
        cabinetToAdd.setLaboratory(cabinet.getLaboratory());
        cabinetRepository.save(cabinetToAdd);
    }

    public Optional<Cabinet> getByUuid(UUID uuid) {
        return cabinetRepository.getCabinetByUuid(uuid);
    }
}
