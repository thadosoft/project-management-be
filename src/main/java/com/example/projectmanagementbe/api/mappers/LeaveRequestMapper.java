package com.example.projectmanagementbe.api.mappers;

import com.example.projectmanagementbe.api.models.LeaveRequests;
import com.example.projectmanagementbe.api.models.dto.requests.LeaveRequest.CreateLeaveRequest;
import com.example.projectmanagementbe.api.models.dto.requests.LeaveRequest.UpdateLeaveRequest;
import com.example.projectmanagementbe.api.models.dto.responses.LeaveResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LeaveRequestMapper {

  LeaveResponse mapLeaveResponse(LeaveRequests employee);

  LeaveRequests map(CreateLeaveRequest request);

  void update(UpdateLeaveRequest dto, @MappingTarget LeaveRequests entity);

  LeaveRequests mapByID(Long id);
}
