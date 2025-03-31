package com.example.projectmanagementbe.api.mappers;

import com.example.projectmanagementbe.api.models.Period;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.PeriodResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PeriodMapper {

  List<PeriodResponse> mapPeriodResponses(List<Period> periods);
}
