package com.example.usersservice.exceptionHandling;

import lombok.Data;

@Data
public class ValidationFieldError {
  private String field;
  private String error;

}
