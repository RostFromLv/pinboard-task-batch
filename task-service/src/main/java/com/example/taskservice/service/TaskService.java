package com.example.taskservice.service;


import com.example.taskservice.model.TaskDTO;
import java.util.Collection;
import java.util.List;

public interface TaskService {
  TaskDTO create(TaskDTO resource) throws Exception;

  List<TaskDTO> createAll(List<TaskDTO> taskDTOList) throws Exception;

  TaskDTO findById(Integer id);

  Collection<TaskDTO> findAll();

  TaskDTO update(TaskDTO resource);

  void deleteById(Integer id);

  void deleteAll();

  boolean existById(Integer id);

   void sendReport();
}
