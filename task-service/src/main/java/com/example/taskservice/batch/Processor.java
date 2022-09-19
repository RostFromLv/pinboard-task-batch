package com.example.taskservice.batch;

import com.example.taskservice.domen.Status;
import com.example.taskservice.domen.Task;
import com.example.taskservice.service.UserFeignClient;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Processor implements ItemProcessor<Task,Task> {

  private final UserFeignClient userFeignClient;

  @Autowired
  public Processor(UserFeignClient userFeignClient) {
    this.userFeignClient = userFeignClient;
  }

  @Override
  public Task process(Task task) throws Exception {

    if (task.getStatus().equals(Status.NOT_STARTED)){
      task.setStatus(Status.IN_WORK);
    }


    return task;
  }
}
