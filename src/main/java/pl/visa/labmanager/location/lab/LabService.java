package pl.visa.labmanager.location.lab;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class LabService {
    private final LabRepository labRepository;

    public LabService(LabRepository labRepository) {
        this.labRepository = labRepository;
    }

    public void addLaboratory(String name) {
        Laboratory lab = new Laboratory();
        lab.setLaboratoryName(name);
        labRepository.save(lab);
    }

    public Optional<Laboratory> getLabFromUuid(UUID uuid)  {
        return labRepository.getLabFromUuid(uuid);
    }


}
