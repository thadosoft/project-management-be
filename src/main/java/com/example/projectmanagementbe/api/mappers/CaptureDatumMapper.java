package com.example.projectmanagementbe.api.mappers;

import com.example.projectmanagementbe.api.models.dto.responses.dashboard.LateStaff;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.CaptureDatumResponse;
import com.example.projectmanagementbe.api.models.employee.CaptureDatum;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CaptureDatumMapper {

  CaptureDatumResponse map(CaptureDatum captureDatum);
  List<LateStaff> map(List<CaptureDatum> captureDatumList);
}
