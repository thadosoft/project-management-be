CREATE DEFINER=`root`@`localhost` PROCEDURE `RetriveTotalShiftDay`(
    IN work_date_param VARCHAR(50),
    IN emp_code_param VARCHAR(50)
)
BEGIN
SELECT
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
        COALESCE(DATE(STR_TO_DATE(c.time, '%Y-%m-%d %H:%i:%s.%f')), STR_TO_DATE(work_date_param, '%Y-%m-%d')) AS work_date,
        MIN(TIME(STR_TO_DATE(c.time, '%Y-%m-%d %H:%i:%s.%f'))) AS first_time
    FROM capture_data c
    WHERE DATE(STR_TO_DATE(c.time, '%Y-%m-%d %H:%i:%s.%f')) = STR_TO_DATE(work_date_param, '%Y-%m-%d')
GROUP BY c.person_id, work_date
    ) f ON f.person_id = e.employee_code
WHERE
    emp_code_param IS NULL OR e.employee_code = emp_code_param;
END