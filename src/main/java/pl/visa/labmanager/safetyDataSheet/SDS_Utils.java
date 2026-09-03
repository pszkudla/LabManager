package pl.visa.labmanager.safetyDataSheet;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pl.visa.labmanager.LabManagerApplication;
import pl.visa.labmanager.errors.SDS_Download_Exeption;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class SDS_Utils {

    public static Path downloadPdf(String link) {

        String downloadDir = LabManagerApplication.sdsPath;
        String tempFolderName = UUID.randomUUID().toString();
        Path tempDirectory = Paths.get(downloadDir, tempFolderName);
        try {
            Files.createDirectory(tempDirectory);
            Map<String, Object> prefs = new HashMap<>();

            prefs.put("download.default_directory", tempDirectory.toAbsolutePath().toString());
            prefs.put("plugins.always_open_pdf_externally", true);
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("prefs", prefs);

            WebDriver driver = new ChromeDriver(options);
            driver.get(link);


            String downloadedPdfName =  scanForPdfs(100, tempDirectory)
                    .orElseThrow(() -> new SDS_Download_Exeption(link));



            return Paths.get(tempDirectory.toString(), downloadedPdfName);


        } catch (IOException e) {
            System.out.println("Wystąpił błąd przy tworzeniu folderu tymczasowego.");
            e.printStackTrace();
        }
        return Path.of("");
    }

    public static Optional<String> scanForPdfs(int seconds, Path folderPath) {
        for (int i = 0; i<seconds; i++) {
            try {
                List<Path> filePaths = Files.list(folderPath).filter(f -> f.getFileName().toString().endsWith(".pdf")).toList();
                if (filePaths.size() > 0) {
                    System.out.println("Zapiszę plik jako '%s'.".formatted(filePaths.get(0).getFileName().toString()));
                    return  Optional.of(filePaths.get(0).getFileName().toString());

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        return Optional.empty();
    }
}
