package com.example.taskservice.domen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailTextObject {
  private String receiver;
  private String subject;
  private String text;
}
