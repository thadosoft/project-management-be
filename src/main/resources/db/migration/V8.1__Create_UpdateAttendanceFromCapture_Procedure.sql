CREATE DEFINER=`root`@`localhost` PROCEDURE `UpdateAttendanceFromCapture`(
    IN work_date_param VARCHAR(50),
    IN emp_code_param VARCHAR(50)
)
BEGIN
SELECT
    e.employee_code,
    e.full_name,
    work_date_param AS work_date,
    COALESCE(SUM(
                     CASE
                         WHEN f.first_time BETWEEN '06:00:00' AND '12:00:00' THEN 0.5
                         WHEN f.first_time BETWEEN '13:00:00' AND '18:00:00' THEN 0.5
                         ELSE 0
                         END
             ), 0) AS total_shifts
FROM employee e
         LEFT JOIN (
    SELECT
        c.person_id,
        DATE(STR_TO_DATE(c.time, '%Y-%m-%d %H:%i:%s.%f')) AS work_date,
     MIN(TIME(STR_TO_DATE(c.time, '%Y-%m-%d %H:%i:%s.%f'))) AS first_time
    FROM capture_data c
WHERE DATE(STR_TO_DATE(c.time, '%Y-%m-%d %H:%i:%s.%f')) = work_date_param
GROUP BY c.person_id, work_date
    ) f ON f.person_id = e.employee_code
WHERE (emp_code_param IS NULL OR e.employee_code = emp_code_param)
GROUP BY e.employee_code, e.full_name, work_date_param
ORDER BY e.full_name, work_date_param;
END