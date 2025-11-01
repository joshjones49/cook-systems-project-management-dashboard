package com.cooksys.server.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.server.dtos.ProjectRequestDto;
import com.cooksys.server.dtos.ProjectResponseDto;

@Service
public interface ProjectService {
  ProjectResponseDto createProject(Long teamId, ProjectRequestDto projectRequest);

  List<ProjectResponseDto> getProjects(Long teamId);

  ProjectResponseDto updateProject(Long projectId, ProjectRequestDto projectRequest);

}
