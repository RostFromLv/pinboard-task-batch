package com.example.usersservice.domen;

import com.example.usersservice.converter.Convertible;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User implements Convertible {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column
  private String name;

  @Column
  private String lastName;

  @Column
  private Integer age;

  @Column(unique = true)
  private String email;

  @Column
  private String phoneNumber;


}
