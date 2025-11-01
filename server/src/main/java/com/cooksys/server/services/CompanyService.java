package com.cooksys.server.services;

import java.util.List;

import com.cooksys.server.dtos.CompanyResponseDto;

public interface CompanyService {

  List<CompanyResponseDto> getAllCompanies();

}
