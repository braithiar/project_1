package com.revature.exceptions.advisors;

import com.revature.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionAdvisor extends ResponseEntityExceptionHandler {
  @ExceptionHandler(EmptyDescriptionException.class)
  public ResponseEntity<?> handleEmptyDescriptionException(
    EmptyDescriptionException ede, WebRequest wr) {

    Map<String, Object> body = new HashMap<>();

    body.put("timestamp", LocalDateTime.now());
    body.put("message", "Reimbursement description was empty");

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(FailedReimbursementException.class)
  public ResponseEntity<?> handleFailedReimbursementException(
    FailedReimbursementException fre, WebRequest wr) {
    Map<String, Object> body = new HashMap<>();

    body.put("timestamp", LocalDateTime.now());
    body.put("message", "Creation of a new reimbursement failed");

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NonCompliantAmountException.class)
  public ResponseEntity<?> handleNonCompliantAmountException(
    NonCompliantAmountException ncae, WebRequest wr) {
    Map<String, Object> body = new HashMap<>();

    body.put("timestamp", LocalDateTime.now());
    body.put("message", "The reimbursement amount was not an acceptable value");

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(StatusDoesNotExistException.class)
  public ResponseEntity<?> handleStatusDNEException(
    StatusDoesNotExistException sdnee, WebRequest wr) {
    Map<String, Object> body = new HashMap<>();

    body.put("timestamp", LocalDateTime.now());
    body.put("message", "Status code does not exist");

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UserDoesNotExistException.class)
  public ResponseEntity<?> handleUserDNEException(
    UserDoesNotExistException udnee, WebRequest wr) {
    Map<String, Object> body = new HashMap<>();

    body.put("timestamp", LocalDateTime.now());
    body.put("message", "User does not exist");

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(RoleDoesNotExistException.class)
  public ResponseEntity<?> handleRoleDNEException(
    RoleDoesNotExistException rdnee, WebRequest wr) {
    Map<String, Object> body = new HashMap<>();

    body.put("timestamp", LocalDateTime.now());
    body.put("message", "Role does not exist");

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ExpenseTypeDoesNotExistException.class)
  public ResponseEntity<?> handleTypeDNEException(
    ExpenseTypeDoesNotExistException rdnee, WebRequest wr) {
    Map<String, Object> body = new HashMap<>();

    body.put("timestamp", LocalDateTime.now());
    body.put("message", "Type does not exist");

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NoTokenException.class)
  public ResponseEntity<?> handleTypeDNEException(
    NoTokenException nte, WebRequest wr) {
    Map<String, Object> body = new HashMap<>();

    body.put("timestamp", LocalDateTime.now());
    body.put("message", "Token could not be retrieved");

    return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(FailedToProcessTicketException.class)
  public ResponseEntity<?> handleTypeDNEException(
    FailedToProcessTicketException fsue, WebRequest wr) {
    Map<String, Object> body = new HashMap<>();

    body.put("timestamp", LocalDateTime.now());
    body.put("message", "Could not update reimbursement status");

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(TicketAddException.class)
  public ResponseEntity<?> handleTypeDNEException(
    TicketAddException tae, WebRequest wr) {
    Map<String, Object> body = new HashMap<>();

    body.put("timestamp", LocalDateTime.now());
    body.put("message", "Could not add ticket to queue");

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }
}
