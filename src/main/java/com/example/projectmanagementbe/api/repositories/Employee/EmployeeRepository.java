package com.example.projectmanagementbe.api.repositories.Employee;

import com.example.projectmanagementbe.api.models.employee.Employee;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  List<Employee> findAllByOrderByEmployeeCode();

  Optional<Employee> findByUserId(String userId);

  @Query("SELECT r FROM Employee r " +
      "WHERE (:keyword IS NULL OR LOWER(r.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
      " AND (:career IS NULL OR LOWER(r.career) LIKE LOWER(CONCAT('%', :career, '%')))")
  Page<Employee> searchByNameAndDate(
      @Param("keyword") String keyword,
      @Param("career") String career,
      Pageable pageable
  );
}