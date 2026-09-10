package pl.visa.labmanager.errors;

public class SdsToDeleteNotFoundException extends RuntimeException {
    public SdsToDeleteNotFoundException(String sdsUuid) {
        super("Nie udało się znaleźć SDS o UUID = %s podczas próby usuwania. Przerywam operację usuwania.".formatted(sdsUuid));
    }
}
