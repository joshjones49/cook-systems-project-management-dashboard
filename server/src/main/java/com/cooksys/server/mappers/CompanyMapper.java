package com.cooksys.server.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cooksys.server.dtos.CompanyRequestDto;
import com.cooksys.server.dtos.CompanyResponseDto;
import com.cooksys.server.entities.Company;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

  List<CompanyResponseDto> entitiesToDtos(List<Company> companys);
  
  Company dtoToEntity(CompanyRequestDto companyRequestDto);

  CompanyResponseDto entityToDto(Company company);
}
