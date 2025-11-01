package com.cooksys.server.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserResponseDtoForTeamPage {

    private Long id;
    private UserProfileDto profile;
    private String username;
    private boolean active;
    private boolean admin;
    private String status;
    private List<CompanyResponseDto> companies;
}
