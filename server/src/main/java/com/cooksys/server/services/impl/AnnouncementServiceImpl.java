package com.cooksys.server.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.server.dtos.AnnouncementRequestDto;
import com.cooksys.server.dtos.AnnouncementResponseDto;
import com.cooksys.server.entities.Announcement;
import com.cooksys.server.entities.Company;
import com.cooksys.server.entities.User;
import com.cooksys.server.exceptions.BadRequestException;
import com.cooksys.server.mappers.AnnouncementMapper;
import com.cooksys.server.repositories.AnnouncementRepository;
import com.cooksys.server.repositories.CompanyRepository;
import com.cooksys.server.repositories.UserRepository;
import com.cooksys.server.services.AnnouncementService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {
  private final AnnouncementMapper announcementMapper;
  private final UserRepository userRepository;
  private final CompanyRepository companyRepository;
  private final AnnouncementRepository announcementRepository;

  @Override
  public AnnouncementResponseDto createAnnouncement(String username, Long companyId, AnnouncementRequestDto announcementRequestDto) {
    if (username == null || companyId == null || announcementRequestDto == null) {
      throw new BadRequestException("Must provide proper request to create Announcement");
    }

    Optional<User> author = userRepository.findByCredentialsUsername(username);
    Optional<Company> company = companyRepository.findById(companyId);

    if (!author.isPresent()) {
      throw new BadRequestException("No user with the username: " + username + " exists.");
    }

    if (!company.isPresent()) {
      throw new BadRequestException("No company with the id: " + companyId + " exists.");
    }

    Announcement announcementToSave = announcementMapper.dtoToEntity(announcementRequestDto);

    announcementToSave.setAuthor(author.get());
    announcementToSave.setCompany(company.get());

    return announcementMapper.entityToDto(announcementRepository.saveAndFlush(announcementToSave));
  }

  @Override
  public List<AnnouncementResponseDto> getAllAnnouncements(Long companyId) {
    Optional<Company> company = companyRepository.findById(companyId);

    if (!company.isPresent()) {
      throw new BadRequestException("No company with the id: " + companyId + " exists");
    }

    List<Announcement> announcements = announcementRepository.findAllByCompanyIdOrderByDateDesc(companyId);

    return announcementMapper.entitiesToDtos(announcements);

  }
  
}
