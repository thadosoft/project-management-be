package com.example.projectmanagementbe.api.repositories;

import com.example.projectmanagementbe.api.models.Period;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PeriodRepository extends JpaRepository<Period, Long>, JpaSpecificationExecutor<Period> {

  Period findByMonthAndYear(int month, int year);
}