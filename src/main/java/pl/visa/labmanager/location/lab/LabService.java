package pl.visa.labmanager.location.lab;

import org.springframework.stereotype.Service;
import pl.visa.labmanager.errors.LabNotFoundExcepition;

import java.util.List;
import java.util.Map;
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

    public List<Laboratory> findAllLabs() { return labRepository.findAll(); }

    public boolean deleteLab(UUID uuid) {
        Optional<Laboratory> lab = labRepository.getLabFromUuid(uuid);
        if (lab.isPresent()) {
            try {
                labRepository.delete(lab.get());
                return true;
            } catch (Error e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public Optional<Laboratory> updateLaboratory(LaboratoryDTO lab) {
        Optional<Laboratory> optLab = labRepository.getLabFromUuid(UUID.fromString(lab.getUuid()));
        if (optLab.isPresent()) {
            Laboratory modifiedLab = optLab.get();
            modifiedLab.setLaboratoryName(lab.getLaboratoryName());
            modifiedLab.setRoomNumber(lab.getRoomNumber());
            modifiedLab.setUuid(UUID.fromString(lab.getUuid()));
            labRepository.save(modifiedLab);
            return Optional.of(modifiedLab);
        }
        else {
            return Optional.ofNullable(null);
        }
    }




}
