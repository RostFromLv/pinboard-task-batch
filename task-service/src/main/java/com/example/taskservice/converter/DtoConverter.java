package com.example.taskservice.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DtoConverter {

  private final ModelMapper modelMapper;

  @Autowired
  public DtoConverter(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public <E extends Convertible, D extends Convertible> D convertToDto(final E entity,
                                                                       final Class<D> dto) {
    return modelMapper.map(entity, dto);
  }

  public <D extends Convertible, E extends Convertible> E convertToEntity(final D dto,
                                                                          final Class<E> entity) {
    return modelMapper.map(dto, entity);
  }

  public <D extends Convertible,E extends Convertible> void update(final D dto,final E entity){
    modelMapper.map(dto,entity);
  }
}
