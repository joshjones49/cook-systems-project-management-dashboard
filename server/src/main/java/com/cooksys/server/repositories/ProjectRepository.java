package com.cooksys.server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.server.entities.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

  List<Project> findAllByTeamId(Long teamId);
  
}
