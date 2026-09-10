package pl.visa.labmanager.errors;


import lombok.extern.slf4j.Slf4j;
import org.openscience.cdk.exception.CDKException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> dataIntegrViolation(DataIntegrityViolationException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(CDKException.class)
    public ResponseEntity<ErrorResponse> cdkeException(CDKException cdke) {
        ErrorResponse errorResponse = new ErrorResponse("Błąd CDKException." + cdke.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(InvalidSubstanceDescriptorException.class)
    public ResponseEntity<ErrorResponse> isdeException(InvalidSubstanceDescriptorException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        log.error(ex.getMessage());
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(SdsToDeleteNotFoundException.class)
    public ResponseEntity<ErrorResponse> sdsDeleteExcption(SdsToDeleteNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

}
