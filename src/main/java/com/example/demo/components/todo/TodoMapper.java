package com.example.demo.components.todo;

import com.example.demo.components.todo.dto.TodoCreateDTO;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;


@Mapper
public interface TodoMapper {
    TodoMapper INSTANCE = Mappers.getMapper(TodoMapper.class);

    Todo dtoCreateToEntity(TodoCreateDTO todoCreateDTO);

    Todo dtoUpdateToEntity(TodoCreateDTO todoCreateDTO);
}
