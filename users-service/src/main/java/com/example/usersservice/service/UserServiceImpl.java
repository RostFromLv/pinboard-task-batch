package com.example.usersservice.service;

import com.example.usersservice.converter.DtoConverter;
import com.example.usersservice.domen.User;
import com.example.usersservice.model.UserDto;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class UserServiceImpl implements UserService {

  private final DtoConverter<User, UserDto> dtoDtoConverter;
  private final UserRepository userRepository;

  public UserServiceImpl(
      DtoConverter<User, UserDto> dtoDtoConverter,
      UserRepository userRepository) {
    this.dtoDtoConverter = dtoDtoConverter;
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public UserDto create(final UserDto userDto) {
    Assert.notNull(userDto, "User dto cannot be null");

    User user = userRepository.save(dtoDtoConverter.convertToEntity(userDto, User.class));


    return dtoDtoConverter.convertToDto(user, userDto.getClass());
  }

  @Override
  @Transactional(readOnly = true)
  public Collection<UserDto> findAll() {
    return userRepository.findAll().stream()
        .map(entity -> this.dtoDtoConverter.convertToDto(entity, UserDto.class))
        .collect(Collectors.toUnmodifiableList());
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<UserDto> findById(final Integer id) {
    Assert.notNull(id, "Cannot find by null id");
    return userRepository.findById(id)
        .map(entity -> this.dtoDtoConverter.convertToDto(entity, UserDto.class));
  }

  @Override
  public void deleteById(final Integer id) {
    Assert.notNull(id, "Cannot delete by null id");
    userRepository.deleteById(id);
  }

  @Override
  public void deleteAll() {
    this.userRepository.deleteAll();
  }

  @Override
  public UserDto update(final UserDto userDto) {
    Assert.notNull(userDto, "User dto for updating cannot be null");
    Integer userDtoId = userDto.getId();
    Assert.notNull(userDtoId, "User id for updating dto is null");

    User userFromBD = userRepository.findById(userDtoId).orElseThrow(
        () -> new EntityNotFoundException(
            String.format("Entity with id %s does`t exist", userDtoId)));

    this.dtoDtoConverter.update(userDto, userFromBD);

    User updatedUser = userRepository.save(userFromBD);


    return this.dtoDtoConverter.convertToDto(updatedUser, UserDto.class);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean containsById(Integer id) {
    return this.userRepository.existsById(id);
  }
}
