package com.cooksys.server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.server.entities.Announcement;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
  List<Announcement> findAllByCompanyIdOrderByDateDesc(Long companyId);
}
