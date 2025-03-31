CREATE DEFINER=`root`@`localhost` PROCEDURE `RetriveTotalShiftDay`(
    IN work_date_param VARCHAR(50),
    IN emp_code_param VARCHAR(50)
)
BEGIN
SELECT
    COALESCE(SUM(
                     CASE
                         WHEN f.morning_time IS NOT NULL AND f.afternoon_time IS NOT NULL THEN 1.0
                         WHEN f.morning_time IS NOT NULL OR f.afternoon_time IS NOT NULL THEN 0.5
                         ELSE 0
                         END
             ), 0) AS total_shifts
FROM employee e
         LEFT JOIN (
    SELECT
        c.person_id,
        DATE(STR_TO_DATE(c.time, '%Y-%m-%d %H:%i:%s.%f')) AS work_date,
     MIN(
             CASE
                 WHEN TIME(STR_TO_DATE(c.time, '%Y-%m-%d %H:%i:%s.%f')) BETWEEN '06:00:00' AND '12:00:00'
             THEN TIME(STR_TO_DATE(c.time, '%Y-%m-%d %H:%i:%s.%f'))
             ELSE NULL
             END
     ) AS morning_time,
     MAX(
             CASE
                 WHEN TIME(STR_TO_DATE(c.time, '%Y-%m-%d %H:%i:%s.%f')) BETWEEN '13:00:00' AND '18:00:00'
             THEN TIME(STR_TO_DATE(c.time, '%Y-%m-%d %H:%i:%s.%f'))
             ELSE NULL
             END
     ) AS afternoon_time
    FROM capture_data c
WHERE DATE(STR_TO_DATE(c.time, '%Y-%m-%d %H:%i:%s.%f')) = STR_TO_DATE(work_date_param, '%Y-%m-%d')
GROUP BY c.person_id, work_date
    ) f ON f.person_id = e.employee_code
WHERE emp_code_param IS NULL OR e.employee_code = emp_code_param;
END