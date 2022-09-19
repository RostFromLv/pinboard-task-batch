package com.example.taskservice.domen;

import com.example.taskservice.converter.Convertible;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "tasks")
@Entity(name = "task")
@AllArgsConstructor
@NoArgsConstructor
public class Task implements Convertible {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column
  private String title;

  @Column
  private String text;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private Status status;

  @Column
  private Integer userId;

  @Column
  private String notificationText;
}
