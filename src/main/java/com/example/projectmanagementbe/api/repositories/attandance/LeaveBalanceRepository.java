package com.example.projectmanagementbe.api.repositories.attandance;

import com.example.projectmanagementbe.api.models.LeaveBalance;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {

  Optional<LeaveBalance> findByEmployeeIdAndYear(Long employeeId, Integer year);

  List<LeaveBalance> findByYear(Integer year);

  List<LeaveBalance> findByEmployeeId(Long employeeId);
}
