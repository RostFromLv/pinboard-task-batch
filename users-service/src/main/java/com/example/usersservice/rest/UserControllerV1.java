package com.example.usersservice.rest;

import com.example.usersservice.model.UserDto;
import com.example.usersservice.service.UserService;
import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserControllerV1 {

  private  final UserService userService ;

  @Autowired
  public UserControllerV1(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Optional<UserDto> findById(@PathVariable Integer id){
    return userService.findById(id);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Collection<UserDto> findAll(){
    return userService.findAll();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserDto create(@RequestBody UserDto userDto){
    return userService.create(userDto);
  }

  @PutMapping
  @ResponseStatus(HttpStatus.OK)
  public UserDto update(@RequestBody UserDto userDto){
    return userService.update(userDto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable Integer id){
     userService.deleteById(id);
  }

  @DeleteMapping("/all")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteAll(){
    userService.deleteAll();
  }
}
