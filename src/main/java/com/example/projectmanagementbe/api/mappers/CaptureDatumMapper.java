package com.example.projectmanagementbe.api.mappers;

import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.CaptureDatumResponse;
import com.example.projectmanagementbe.api.models.employee.CaptureDatum;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CaptureDatumMapper {

  CaptureDatumResponse map(CaptureDatum captureDatum);
}
