package com.example.projectmanagementbe.api.repositories.attandance;

import com.example.projectmanagementbe.api.models.Attendance;
import com.example.projectmanagementbe.api.models.Period;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>, JpaSpecificationExecutor<Attendance> {

  @Query("SELECT COUNT(a) FROM Attendance a WHERE YEAR(a.workDate) = :year AND MONTH(a.workDate) = :month")
  long countByYearAndMonth(@Param("year") int year, @Param("month") int month);

  @Procedure(procedureName = "UpdateAttendanceFromCapture")
  List<Object[]> getAttendanceByDate(@Param("year_param") int year, @Param("month_param") int month);

  List<Attendance> findAllByPeriodId(Period period);
}