package com.example.taskservice.model;

import com.example.taskservice.converter.Convertible;
import com.example.taskservice.domen.Status;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Convertible {
  @Nullable
  private Integer id;
  @NotNull
  private String name;
  @NotNull
  private String lastName;
  @NotNull
  private Integer age;
  @NotNull
  private String email;

  private Status status;

  private String phoneNumber;
}
