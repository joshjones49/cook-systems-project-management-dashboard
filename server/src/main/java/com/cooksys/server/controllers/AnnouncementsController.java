package com.cooksys.server.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.server.dtos.AnnouncementRequestDto;
import com.cooksys.server.dtos.AnnouncementResponseDto;
import com.cooksys.server.services.AnnouncementService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/announcements")
public class AnnouncementsController {
  private final AnnouncementService announcementService;

  @PostMapping("/{username}/{companyId}")
  @ResponseStatus(HttpStatus.CREATED)
  public AnnouncementResponseDto createAnnouncement(@PathVariable String username, @PathVariable Long companyId, @RequestBody AnnouncementRequestDto announcementRequestDto) {
    return announcementService.createAnnouncement(username, companyId, announcementRequestDto);
  }

  @GetMapping("/{companyId}")
  public List<AnnouncementResponseDto> getAllAnnouncements(@PathVariable Long companyId) {
    return announcementService.getAllAnnouncements(companyId);
  }

}
