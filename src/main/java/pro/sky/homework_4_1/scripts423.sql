SELECT s."name", s."age", f."name"
FROM student s
JOIN faculty f on f.id = s.faculty_id;

SELECT *
FROM student s
JOIN avatar a ON s.id = a.student_id;