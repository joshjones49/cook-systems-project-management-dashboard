package com.cooksys.server.controllers;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.server.dtos.ProjectRequestDto;
import com.cooksys.server.dtos.ProjectResponseDto;
import com.cooksys.server.services.ProjectService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {
  private final ProjectService projectService;

  @PostMapping("/{teamId}")
  @ResponseStatus(HttpStatus.CREATED)
  public ProjectResponseDto createProject(@PathVariable Long teamId, @RequestBody ProjectRequestDto projectRequest) {
    return projectService.createProject(teamId, projectRequest);
  }

  @GetMapping("/{teamId}")
  public List<ProjectResponseDto> getProjects(@PathVariable Long teamId) {
    return projectService.getProjects(teamId);
  }

  @PatchMapping("/{projectId}")
  public ProjectResponseDto updateProject(@PathVariable Long projectId, @RequestBody ProjectRequestDto projectRequest) {
    return projectService.updateProject(projectId, projectRequest);
  }


}
