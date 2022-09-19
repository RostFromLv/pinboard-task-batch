package com.example.taskservice.model;

import com.example.taskservice.converter.Convertible;
import com.example.taskservice.domen.Status;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO implements Convertible {
  @Nullable
  private Integer id;

  @NotNull
  private String title;

  @NotNull
  private String text;

  @NotNull
  private Status status;

  @NotNull
  private Integer userId;

  @NotNull
  private String notificationText;

  public StringBuilder toReportString() {

    return new StringBuilder(
        this.getTitle() + ","
            + this.getText() + ","
            + this.getStatus() + "," + "\n");
  }


}
