package com.cooksys.server.controllers;

import com.cooksys.server.dtos.TeamRequestDto;
import com.cooksys.server.dtos.TeamResponseDto;
import com.cooksys.server.services.TeamService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/company/{companyId}")
    public List<TeamResponseDto> getCompanyTeams(@PathVariable long companyId) {
        return teamService.getCompanyTeams(companyId);
    }

    @GetMapping("/user/{userId}")
    public List<TeamResponseDto> getUserTeams(@PathVariable long userId) {
        return teamService.getUserTeams(userId);
    }

    @PostMapping("/{companyId}")
	@ResponseStatus(HttpStatus.CREATED)
    public TeamResponseDto createTeam(@PathVariable long companyId, @RequestBody TeamRequestDto teamRequestDto) {
        return teamService.createTeam(companyId, teamRequestDto);
    }
}
