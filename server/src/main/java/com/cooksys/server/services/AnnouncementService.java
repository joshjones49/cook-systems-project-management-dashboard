package com.cooksys.server.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.server.dtos.AnnouncementRequestDto;
import com.cooksys.server.dtos.AnnouncementResponseDto;

@Service
public interface AnnouncementService {

  AnnouncementResponseDto createAnnouncement(String username, Long companyId, AnnouncementRequestDto announcementRequestDto);
  
  List<AnnouncementResponseDto> getAllAnnouncements(Long companyId);
}
