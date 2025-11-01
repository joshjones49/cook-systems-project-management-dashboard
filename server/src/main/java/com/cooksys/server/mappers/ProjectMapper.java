package com.cooksys.server.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cooksys.server.dtos.ProjectRequestDto;
import com.cooksys.server.dtos.ProjectResponseDto;
import com.cooksys.server.entities.Project;

@Mapper(componentModel = "spring", uses = {TeamMapper.class})
public interface ProjectMapper {

  List<ProjectResponseDto> entitiesToDtos(List<Project> projects);
  
  Project dtoToEntity(ProjectRequestDto projectRequestDto);

  ProjectResponseDto entityToDto(Project project);
}
