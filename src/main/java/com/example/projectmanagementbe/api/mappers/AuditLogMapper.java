package com.example.projectmanagementbe.api.mappers;

import com.example.projectmanagementbe.api.models.AuditLog.responses.log.AuditTrailResponse;
import com.example.projectmanagementbe.api.models.AuditTrail;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AuditLogMapper {

    AuditTrailResponse mapAuditTrailResponses(AuditTrail auditTrails);
}
