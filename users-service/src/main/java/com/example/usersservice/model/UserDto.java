package com.example.usersservice.model;

import com.example.usersservice.converter.Convertible;
import com.sun.istack.NotNull;
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

  private String phoneNumber;
}
