package com.example.demo.components.user;

import com.example.demo.components.user.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userDTOToUser(UserDTO user);

    UserDTO userToUserDTO(User user);

    Collection<UserDTO> userEntitiesToUserDTOs(Iterable<User> user);
}
