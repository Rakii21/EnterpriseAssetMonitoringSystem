package com.example.EnterpriseAssetMonitoringSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("\"" + ex.getMessage() + "\"");  // Wrap message in quotes
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("\"Invalid input: " + ex.getMessage() + "\"");
    }
    @ExceptionHandler(MaintenanceException.class)
    public ResponseEntity<String> handleMaintenanceException(MaintenanceException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("\""+ex.getMessage()+"\"");
    }
    @ExceptionHandler(AssetException.class)
    public ResponseEntity<String> handleAssetException(AssetException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("\""+ex.getMessage()+"\"");
    }
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<String> handleObjectNotFoundException(ObjectNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("\""+ex.getMessage()+"\"");
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("\""+ex.getMessage()+"\"");
    }
    @ExceptionHandler(UserInvalidException.class)
    public ResponseEntity<String> handleUserInvalidException(UserInvalidException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("\""+ex.getMessage()+"\"");
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("\""+ex.getMessage()+"\"");
    }

}
