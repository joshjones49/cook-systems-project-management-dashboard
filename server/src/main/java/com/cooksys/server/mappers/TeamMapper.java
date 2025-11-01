package com.cooksys.server.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cooksys.server.dtos.TeamRequestDto;
import com.cooksys.server.dtos.TeamResponseDto;
import com.cooksys.server.entities.Team;

@Mapper(componentModel = "spring", uses = { UserMapper.class, ProjectMapper.class })
public interface TeamMapper {

	List<TeamResponseDto> entitiesToDtos(List<Team> teams);

	Team dtoToEntity(TeamRequestDto teamRequestDto);

	TeamResponseDto entityToDto(Team team);
}
