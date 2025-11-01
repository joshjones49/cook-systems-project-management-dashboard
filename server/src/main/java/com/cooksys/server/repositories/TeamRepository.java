package com.cooksys.server.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.server.entities.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

  Optional<Team> findById(Long id);

  List<Team> findAllByCompanyId(long companyId);
  
  List<Team> findAllByUsersId(Long userId);
}
