package com.example.taskservice.batch;


import com.example.taskservice.domen.Task;
import com.example.taskservice.service.TaskRepository;
import java.util.List;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatchWriter implements ItemWriter<Task> {

  @Autowired
  private final TaskRepository taskRepository;

  public BatchWriter(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  @Override
  public void write(List<? extends Task> list) {

    taskRepository.saveAllAndFlush(list);

  }
}
