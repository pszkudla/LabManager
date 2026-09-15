package pl.visa.labmanager.location.lab;

import org.springframework.stereotype.Service;
import pl.visa.labmanager.errors.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LabService {
    private final LabRepository labRepository;

    public LabService(LabRepository labRepository) {
        this.labRepository = labRepository;
    }

    public LaboratoryDTO addLaboratory(Laboratory inputLab) {
        Laboratory lab = new Laboratory();
        lab.setLaboratoryName(inputLab.getLaboratoryName());
        lab.setRoomNumber(inputLab.getRoomNumber());
        return labRepository.save(lab).getLabDTO();
    }

    public Laboratory getLabFromUuid(UUID uuid) {
        Laboratory lab =  labRepository.getLabFromUuid(uuid).orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono laboratorium o UUID = %s.".formatted(uuid)));
        return lab;
    }


    public LaboratoryDTO getLabDtoFromUuid(UUID uuid)  {
        Laboratory lab =  labRepository.getLabFromUuid(uuid).orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono laboratorium o UUID = %s.".formatted(uuid)));
        LaboratoryDTO dto = lab.getLabDTO();
        return dto;
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

    public Laboratory updateLab(LaboratoryDTO lab) {
        Laboratory labToEdit = labRepository
                .getLabFromUuid(UUID.fromString(lab.getUuid())).orElseThrow(()
                        -> new ResourceNotFoundException("Nie udało się znaleźć laboratorium o UUID = %s przy próbie jego edycji.".formatted(lab.getUuid())));
        labToEdit.setRoomNumber(lab.getRoomNumber());
        labToEdit.setLaboratoryName(lab.getLaboratoryName());
        return labRepository.save(labToEdit);
    }




}
