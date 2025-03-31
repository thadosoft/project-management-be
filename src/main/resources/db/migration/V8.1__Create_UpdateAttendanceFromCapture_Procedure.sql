CREATE DEFINER=`root`@`localhost` PROCEDURE `UpdateAttendanceFromCapture`(
    IN year_param INT,
    IN month_param INT
)
BEGIN
WITH RECURSIVE date_list AS (
    SELECT DATE(CONCAT(year_param, '-', month_param, '-01')) AS work_date
UNION ALL
SELECT DATE_ADD(work_date, INTERVAL 1 DAY)
FROM date_list
WHERE work_date < LAST_DAY(CONCAT(year_param, '-', month_param, '-01'))
    )

SELECT
    e.employee_code,
    e.full_name,
    d.work_date,
    CASE
        WHEN f.morning_checkin IS NOT NULL AND f.afternoon_checkout IS NOT NULL THEN 'CN'
        WHEN f.morning_checkin IS NOT NULL THEN 'S'
        WHEN f.afternoon_checkout IS NOT NULL THEN 'C'
        ELSE ''
        END AS shift_name,
    CASE
        WHEN f.morning_checkin IS NOT NULL AND f.afternoon_checkout IS NOT NULL THEN 1.0
        WHEN f.morning_checkin IS NOT NULL OR f.afternoon_checkout IS NOT NULL THEN 0.5
        ELSE 0
        END AS total_shifts,
    COALESCE(f.morning_checkin, '') AS morning_checkin,
    COALESCE(f.afternoon_checkout, '') AS afternoon_checkout,
    COALESCE(f.other_checkins, '') AS other_checkins
FROM employee e
         CROSS JOIN date_list d
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
     ) AS morning_checkin,
     MAX(
             CASE
                 WHEN TIME(STR_TO_DATE(c.time, '%Y-%m-%d %H:%i:%s.%f')) BETWEEN '13:00:00' AND '18:00:00'
             THEN TIME(STR_TO_DATE(c.time, '%Y-%m-%d %H:%i:%s.%f'))
             ELSE NULL
             END
     ) AS afternoon_checkout,
     GROUP_CONCAT(
             CASE
                 WHEN TIME(STR_TO_DATE(c.time, '%Y-%m-%d %H:%i:%s.%f')) NOT BETWEEN '06:00:00' AND '18:00:00'
                 THEN TIME(STR_TO_DATE(c.time, '%Y-%m-%d %H:%i:%s.%f'))
                 ELSE NULL
                 END
                 ORDER BY c.time SEPARATOR ', '
     ) AS other_checkins
    FROM capture_data c
WHERE YEAR(STR_TO_DATE(c.time, '%Y-%m-%d %H:%i:%s.%f')) = year_param
  AND MONTH(STR_TO_DATE(c.time, '%Y-%m-%d %H:%i:%s.%f')) = month_param
GROUP BY c.person_id, work_date
    ) f ON f.person_id = e.employee_code AND f.work_date = d.work_date
ORDER BY e.full_name, d.work_date;
END