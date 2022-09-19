package com.example.taskservice.exceptionHandling;

import java.time.LocalDateTime;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Data;

@Data
public class Error {
  @Min(400)
  @Max(600)
  private Integer code;
  private String message;
  private LocalDateTime time;
}
