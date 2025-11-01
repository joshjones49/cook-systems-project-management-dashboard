package com.cooksys.server.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.server.dtos.ProjectRequestDto;
import com.cooksys.server.dtos.ProjectResponseDto;
import com.cooksys.server.entities.Project;
import com.cooksys.server.entities.Team;
import com.cooksys.server.exceptions.BadRequestException;
import com.cooksys.server.exceptions.NotFoundException;
import com.cooksys.server.mappers.ProjectMapper;
import com.cooksys.server.repositories.ProjectRepository;
import com.cooksys.server.repositories.TeamRepository;
import com.cooksys.server.services.ProjectService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService{
  private final ProjectMapper projectMapper;
  private final TeamRepository teamRepository;
  private final ProjectRepository projectRepository;

  @Override
  public ProjectResponseDto createProject(Long teamId, ProjectRequestDto projectRequest) {
    if (projectRequest == null || projectRequest.getName() == null || projectRequest.getDescription() == null) {
      throw new BadRequestException("Must provide all fields for project request");
    }

    Project projectToSave = projectMapper.dtoToEntity(projectRequest);
    Optional<Team> teamToFind = teamRepository.findById(teamId);
    if(teamToFind.isEmpty()) {
    	throw new NotFoundException("No team found with id " + teamId);
    }
    
    Team team = teamToFind.get();
    
    projectToSave.setTeam(team);
    projectRepository.save(projectToSave);
    team.getProjects().add(projectToSave);
    teamRepository.save(team);

    return projectMapper.entityToDto(projectToSave);
  }

  @Override
  public List<ProjectResponseDto> getProjects(Long teamId) {
    return projectMapper.entitiesToDtos(projectRepository.findAllByTeamId(teamId));
  }

  @Override
  public ProjectResponseDto updateProject(Long projectId, ProjectRequestDto projectRequest) {
    Optional<Project> projectOptional = projectRepository.findById(projectId);

    if (!projectOptional.isPresent()) {
      throw new BadRequestException("Project with id: " + projectId + " does not exist");
    }

    Project projectToUpdate = projectOptional.get();

    if (projectRequest.getName() != null) {
        projectToUpdate.setName(projectRequest.getName());
    }
    if (projectRequest.getDescription() != null) {
        projectToUpdate.setDescription(projectRequest.getDescription());
    }
    if (projectToUpdate.isActive() != projectRequest.isActive()) {
      projectToUpdate.setActive(projectRequest.isActive());
    }

    return projectMapper.entityToDto(projectRepository.saveAndFlush(projectToUpdate));
  }
  
}
