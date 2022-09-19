package com.example.taskservice.service;

import com.example.taskservice.converter.DtoConverter;
import com.example.taskservice.domen.EmailTextObject;
import com.example.taskservice.domen.Status;
import com.example.taskservice.domen.Task;
import com.example.taskservice.messageSender.EmailSenderService;
import com.example.taskservice.model.TaskDTO;
import com.example.taskservice.model.UserDto;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;
  private final DtoConverter dtoConverter;
  private final UserFeignClient userFeignClient;
  private final EmailSenderService emailSenderService;

  @Autowired
  public TaskServiceImpl(TaskRepository taskRepository,
                         DtoConverter dtoConverter,
                         UserFeignClient userFeignClient,
                         EmailSenderService emailSenderService) {
    this.taskRepository = taskRepository;
    this.dtoConverter = dtoConverter;
    this.userFeignClient = userFeignClient;
    this.emailSenderService = emailSenderService;
  }

  @Override
  @Transactional
  public TaskDTO create(TaskDTO resource) throws Exception {
    Assert.notNull(resource, "Creation task cannot be null");
    int userId = resource.getUserId();

    if (userFeignClient.findById(userId).isEmpty()) {
      throw new EntityNotFoundException(
          String.format("Entity user with id %s not found", resource.getUserId()));
    }
    Task task = taskRepository.save(dtoConverter.convertToEntity(resource, Task.class));

    return dtoConverter.convertToDto(task, TaskDTO.class);
  }

  @Override
  public List<TaskDTO> createAll(List<TaskDTO> taskDTOList) throws Exception {
    for (TaskDTO taskDTO : taskDTOList) {
      try {
        create(taskDTO);
      } catch (Exception e) {
        throw new Exception("Cant create entity :" + taskDTO);
      }
    }
    return taskDTOList;
  }

  @Override
  @Transactional(readOnly = true)
  public TaskDTO findById(Integer id) {
    Assert.notNull(id, "Null id for find by id");
    return taskRepository.findById(id)
        .map(entity -> dtoConverter.convertToDto(entity, TaskDTO.class))
        .orElseThrow(() -> new EntityNotFoundException(String.format("Wrong task id: %s", id)));
  }

  @Override
  @Transactional(readOnly = true)
  public Collection<TaskDTO> findAll() {
    return taskRepository.findAll().stream()
        .map(entity -> dtoConverter.convertToDto(entity, TaskDTO.class)).collect(
            Collectors.toSet());
  }

  @Override
  @Transactional
  public TaskDTO update(TaskDTO resource) {
    Assert.notNull(resource,
        "Resource for update cannot be null"); // asserting for id not null does check in groups in dto (controller layer)
    Assert.notNull(resource.getId(), "Resource for update cannot have id null");
    int taskId = resource.getId();

    Task taskBD = taskRepository.findById(taskId).orElseThrow(
        () -> new EntityNotFoundException(String.format("No exist entity with id: %s ", taskId)));

    dtoConverter.update(resource, taskBD);

    Task savedTask = taskRepository.save(taskBD);

    return dtoConverter.convertToDto(savedTask, TaskDTO.class);
  }

  @Override
  @Transactional
  public void deleteById(Integer id) {
    Assert.notNull(id, "Id for delete is null");
    taskRepository.deleteById(id);
  }

  @Override
  @Transactional
  public void deleteAll() {
    taskRepository.deleteAll();
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existById(Integer id) {
    Assert.notNull(id, "Id  is null");
    return taskRepository.existsById(id);
  }

  public void sendEmail(EmailTextObject emailTextObject) {
    emailSenderService.sendEmail(emailTextObject.getReceiver(), emailTextObject.getSubject(),
        emailTextObject.getText());
  }

  @Scheduled(fixedRate = 10_000)
  @EventListener(ApplicationReadyEvent.class)
  public void sendReport() {
    System.out.println("Execute sending");
    Collection<TaskDTO> taskDTOList = findAll();

    for (TaskDTO taskDTO : taskDTOList) {
      if (taskDTO.getId()==2){
        taskDTO.setStatus(Status.NOT_STARTED);
      }
      if (taskDTO.getStatus().equals(Status.NOT_STARTED)) {
        System.out.println("Exist not worked task");
        UserDto userDto = userFeignClient.findById(taskDTO.getUserId()).get();
        String text =
            "Dear " + userDto.getName() + ", report for your task:" + taskDTO.toReportString();
        EmailTextObject emailTextObject =
            new EmailTextObject(userDto.getEmail(), "Postponed task", text);
        sendEmail(emailTextObject);
        taskDTO.setStatus(Status.IN_WORK);
        update(taskDTO);
      System.err.println("Successfully sent");
      System.out.println(taskDTO);
      }
    }
  }
}
