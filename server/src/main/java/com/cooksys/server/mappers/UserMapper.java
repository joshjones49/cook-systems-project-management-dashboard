package com.cooksys.server.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cooksys.server.dtos.UserRequestDto;
import com.cooksys.server.dtos.UserResponseDto;
import com.cooksys.server.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	@Mapping(target = "username", source = "credentials.username")
	List<UserResponseDto> entitiesToDtos(List<User> user);

	User requestDtoToEntity(UserRequestDto userRequestDto);

	@Mapping(target = "username", source = "credentials.username")
	UserResponseDto entityToDto(User user);

}