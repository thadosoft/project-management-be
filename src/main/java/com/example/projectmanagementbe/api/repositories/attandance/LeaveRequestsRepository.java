package com.example.projectmanagementbe.api.repositories.attandance;

import com.example.projectmanagementbe.api.models.LeaveRequests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LeaveRequestsRepository extends JpaRepository<LeaveRequests, Long>, JpaSpecificationExecutor<LeaveRequests> {

}