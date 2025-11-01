package com.cooksys.server.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cooksys.server.dtos.AnnouncementRequestDto;
import com.cooksys.server.dtos.AnnouncementResponseDto;
import com.cooksys.server.entities.Announcement;

@Mapper(componentModel = "spring")
public interface AnnouncementMapper {

  List<AnnouncementResponseDto> entitiesToDtos(List<Announcement> announcements);
  
  Announcement dtoToEntity(AnnouncementRequestDto AnnouncementDto);

  @Mapping(target = "author.username", source = "author.credentials.username")
  AnnouncementResponseDto entityToDto(Announcement Announcement);
}

