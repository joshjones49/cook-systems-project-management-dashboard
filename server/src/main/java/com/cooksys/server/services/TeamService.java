package com.cooksys.server.services;

import java.util.List;

import com.cooksys.server.dtos.TeamRequestDto;
import com.cooksys.server.dtos.TeamResponseDto;

public interface TeamService {

    List<TeamResponseDto> getCompanyTeams(long companyId);

    List<TeamResponseDto> getUserTeams(long userId);

    TeamResponseDto createTeam(long companyId, TeamRequestDto teamRequestDto);
}
