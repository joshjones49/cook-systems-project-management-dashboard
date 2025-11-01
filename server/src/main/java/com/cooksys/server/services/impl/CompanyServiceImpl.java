package com.cooksys.server.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.server.dtos.CompanyResponseDto;
import com.cooksys.server.entities.Company;
import com.cooksys.server.mappers.CompanyMapper;
import com.cooksys.server.repositories.CompanyRepository;
import com.cooksys.server.services.CompanyService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CompanyServiceImpl implements CompanyService {
  private final CompanyRepository companyRepository;
  private final CompanyMapper companyMapper;

  @Override
  public List<CompanyResponseDto> getAllCompanies() {
    return companyMapper.entitiesToDtos(companyRepository.findAll());
  }
  
}
