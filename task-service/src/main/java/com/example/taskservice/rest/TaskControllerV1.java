package com.example.taskservice.rest;

import com.example.taskservice.model.TaskDTO;
import com.example.taskservice.service.TaskService;
import java.util.Collection;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/task")
public class TaskControllerV1 {

  private final TaskService taskService;

  private final JobLauncher jobLauncher;

  private final Job job;

  @Autowired
  public TaskControllerV1(TaskService taskService,
                          JobLauncher jobLauncher, Job job) {
    this.taskService = taskService;
    this.jobLauncher = jobLauncher;
    this.job = job;
  }

  @GetMapping("/{id}")
  public TaskDTO findById(@PathVariable Integer id){
    return  taskService.findById(id);
  }
  @GetMapping
  public Collection<TaskDTO> findAll(){
    return taskService.findAll();
  }

  @PostMapping
  public TaskDTO create(@RequestBody TaskDTO taskDTO) throws Exception {
    return  taskService.create(taskDTO);
  }

  @PutMapping
  public TaskDTO update(@RequestBody TaskDTO taskDTO){
    return taskService.update(taskDTO);
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable Integer id){
     taskService.deleteById(id);
  }

  @DeleteMapping("/all")
  public void deleteById(){
    taskService.deleteAll();
  }

  @PostMapping("/execute")
  public BatchStatus triggerBatch()
      throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
      JobParametersInvalidException, JobRestartException {
    JobParameters jobParameters = new JobParametersBuilder().addLong("time",System.currentTimeMillis()).toJobParameters();
      JobExecution jobExecution = jobLauncher.run(job,jobParameters);
    return jobExecution.getStatus();
  }

  @PostMapping("/send")
  public void sendMessage(){
    taskService.sendReport();
  }



}
