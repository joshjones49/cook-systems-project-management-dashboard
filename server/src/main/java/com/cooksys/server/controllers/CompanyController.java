package com.cooksys.server.controllers;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.server.dtos.CompanyResponseDto;
import com.cooksys.server.services.CompanyService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {
  private final CompanyService companyService;

  @GetMapping
  public List<CompanyResponseDto> getAllCompanies() {
    return companyService.getAllCompanies();
  }

}
