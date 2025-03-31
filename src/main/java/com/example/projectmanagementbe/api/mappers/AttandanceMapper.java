package com.example.projectmanagementbe.api.mappers;

import com.example.projectmanagementbe.api.models.Attendance;
import com.example.projectmanagementbe.api.models.dto.responses.timekeeping.UpdateDailyAttendance;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AttandanceMapper {
  void update(UpdateDailyAttendance dto, @MappingTarget Attendance entity);

}
