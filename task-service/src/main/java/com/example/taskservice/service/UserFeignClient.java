package com.example.taskservice.service;

import com.example.taskservice.model.UserDto;
import java.util.Optional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "users-service")
public interface UserFeignClient {

  String addressUri = "/api/v1/user";

  @GetMapping(addressUri + "/{id}")
  Optional<UserDto> findById(@PathVariable Integer id);

}
