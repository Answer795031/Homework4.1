package pro.sky.homework_4_1.service;

import pro.sky.homework_4_1.model.Faculty;
import pro.sky.homework_4_1.model.Student;

import java.util.List;

public interface StudentService {
    Student addStudent(String name, Integer age);

    Student getStudent(Long id);

    Student updateStudent(Long id, String name, Integer age);

    Student removeStudent(Long id);

    List<Student> studentsByAge(Integer age);

    public List<Student> findAllByAgeBetween(Integer min, Integer max);

    public Faculty getFaculty(Long id);

    public int getTotalStudents();

    public double getAverageAge();

    public List<Student> getFiveLastStudents();
}
