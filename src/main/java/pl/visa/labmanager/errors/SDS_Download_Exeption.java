package pl.visa.labmanager.errors;

public class SDS_Download_Exeption extends RuntimeException {
    public SDS_Download_Exeption(String link) {
        super("Wystąpił bład przy pobieraniu SDS z linku %s.".formatted(link));
    }
}
