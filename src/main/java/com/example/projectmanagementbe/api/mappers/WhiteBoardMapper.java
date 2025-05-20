package com.example.projectmanagementbe.api.mappers;

import com.example.projectmanagementbe.api.models.WhiteBoard;
import com.example.projectmanagementbe.api.models.dto.requests.WhiteBoard.CreateWhiteBoard;
import com.example.projectmanagementbe.api.models.dto.requests.WhiteBoard.UpdateWhiteBoard;
import com.example.projectmanagementbe.api.models.dto.responses.WhiteBoardResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WhiteBoardMapper {

  @Mapping(source = "employee.id", target = "employeeId")
  WhiteBoardResponse mapWhiteBoardResponse(WhiteBoard whiteBoard);

  @Mapping(target = "employee", ignore = true)
  WhiteBoard map(CreateWhiteBoard request);

  @Mapping(target = "employee", ignore = true)
  @Mapping(target = "id", ignore = true)
  void update(UpdateWhiteBoard dto, @MappingTarget WhiteBoard entity);
}