package com.example.taskservice.exceptionHandling;


import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ValidationError extends Error {
  private Error error;
  private List<ValidationFieldError> validationFieldErrorList;
}
