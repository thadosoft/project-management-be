package com.example.projectmanagementbe.api.mappers;

import com.example.projectmanagementbe.api.models.WhiteBoard;
import com.example.projectmanagementbe.api.models.dto.requests.WhiteBoard.CreateWhiteBoard;
import com.example.projectmanagementbe.api.models.dto.requests.WhiteBoard.UpdateWhiteBoard;
import com.example.projectmanagementbe.api.models.dto.responses.WhiteBoardResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WhiteBoardMapper {

  WhiteBoardResponse mapWhiteBoardResponse(WhiteBoard employee);

  WhiteBoard map(CreateWhiteBoard request);

  void update(UpdateWhiteBoard dto, @MappingTarget WhiteBoard entity);

  WhiteBoard mapByID(Long id);
}
