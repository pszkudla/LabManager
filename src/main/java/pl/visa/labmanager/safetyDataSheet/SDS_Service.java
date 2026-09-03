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

    public SDS_Service(SDS_Repository sdsRepository, SubstanceRepository substanceRepository) {
        this.sdsRepository = sdsRepository;
        this.substanceRepository = substanceRepository;
    }

    public SafetyDataSheet addSDS(SDS_DTO_in dtoIn) {
        System.out.println(dtoIn.getSubstanceUuid());
        Substance substance = substanceRepository.findByUuid(dtoIn.getSubstanceUuid()).orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono substancji o UUID = %s przy próbie dodania SDS.".formatted(dtoIn.getSubstanceUuid())));
        String sdsLink = dtoIn.getOriginalSourceLink();
        SafetyDataSheet createdDataSheet = new SafetyDataSheet();
        createdDataSheet.setLanguage(dtoIn.getLanguage());
        createdDataSheet.setSubstance(substance);
        createdDataSheet.setSupplier(dtoIn.getSupplier());
        UUID createdUuid = UUID.randomUUID();
        createdDataSheet.setUuid(createdUuid);


        createdDataSheet.setOriginalSourceLink(dtoIn.getOriginalSourceLink());
        SDS_Utils.downloadPdf(sdsLink);
        //SDS_Utils.downloadSdsPdfFromLink(sdsLink, createdUuid.toString());
        Path dowloadedFilePath = SDS_Utils.downloadPdf(sdsLink);

        Path pdfTempFolder = dowloadedFilePath.getParent();
        String initialFilName = dowloadedFilePath.getFileName().toString();
        createdDataSheet.setOriginalFileName(initialFilName);

        Path finalPdfPath = Paths.get(LabManagerApplication.sdsPath, createdUuid.toString() + ".pdf");
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


}
