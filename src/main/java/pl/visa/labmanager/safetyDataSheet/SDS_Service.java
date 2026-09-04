package pl.visa.labmanager.safetyDataSheet;

import org.springframework.stereotype.Service;
import pl.visa.labmanager.LabManagerApplication;
import pl.visa.labmanager.errors.ResourceNotFoundException;
import pl.visa.labmanager.substance.Substance;
import pl.visa.labmanager.substance.SubstanceRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class SDS_Service {
    private final SDS_Repository sdsRepository;
    private final SubstanceRepository substanceRepository;

    public static String sdsPath = LabManagerApplication.dotenv.get("sds_path");

    public SDS_Service(SDS_Repository sdsRepository, SubstanceRepository substanceRepository) {
        this.sdsRepository = sdsRepository;
        this.substanceRepository = substanceRepository;
    }

    public SafetyDataSheet addSDS(SDS_DTO_in dtoIn) {
        Substance substance = substanceRepository.findByUuid(dtoIn.getSubstanceUuid()).orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono substancji o UUID = %s przy próbie dodania SDS.".formatted(dtoIn.getSubstanceUuid())));
        String sdsLink = dtoIn.getOriginalSourceLink();
        SafetyDataSheet createdDataSheet = new SafetyDataSheet();
        createdDataSheet.setLanguage(dtoIn.getLanguage());
        createdDataSheet.setSubstance(substance);
        createdDataSheet.setSupplier(dtoIn.getSupplier());
        UUID createdUuid = UUID.randomUUID();
        createdDataSheet.setUuid(createdUuid);


        createdDataSheet.setOriginalSourceLink(dtoIn.getOriginalSourceLink());

        Path dowloadedFilePath = SDS_Utils.downloadPdf(sdsLink, sdsPath);

        Path pdfTempFolder = dowloadedFilePath.getParent();
        String initialFilName = dowloadedFilePath.getFileName().toString();
        createdDataSheet.setOriginalFileName(initialFilName);

        Path finalPdfPath = Paths.get(sdsPath, createdUuid.toString() + ".pdf");
        try {
            Files.move(dowloadedFilePath, finalPdfPath);
            Files.delete(pdfTempFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }



        sdsRepository.save(createdDataSheet);
        return createdDataSheet;
    }

    public List<SafetyDataSheet> getAllSds() {
        return sdsRepository.findAll();
    }

    public SafetyDataSheet findByUuid(UUID uuid) {
        return sdsRepository.findSafetyDataSheetByUuid(uuid).orElseThrow(() ->
                new ResourceNotFoundException("Nie odnaleziono SDS o UUID równym %s.".formatted(uuid))
        );
    }

    public void deleteByUuid(UUID uuid) {
        SafetyDataSheet sdsToRemove = sdsRepository.findSafetyDataSheetByUuid(uuid).orElseThrow(() -> new ResourceNotFoundException("Nie odnaleziono karty charakterystyki o SDS = %s przy próbie jej usunięcia.".formatted(uuid.toString())));
        sdsRepository.delete(sdsToRemove);
    }

    public List<SafetyDataSheet> findAllSdsOfSubstance(String substanceUuid) {
        return sdsRepository.findAllSdsBySubstanceUuid(substanceUuid);
    }



}
