package com.example.usersservice.service;

import com.example.usersservice.model.UserDto;
import java.util.Collection;
import java.util.Optional;

public interface UserService {

  UserDto create(UserDto userDto);

  Collection<UserDto> findAll();

  Optional<UserDto> findById(Integer id);

  void deleteById(Integer id);

  void deleteAll();

  UserDto update(UserDto userDto);

  boolean containsById(Integer id);


}
