package pro.sky.homework_4_1.service;

import pro.sky.homework_4_1.model.Faculty;
import pro.sky.homework_4_1.model.Student;

import java.util.Collection;
import java.util.List;

public interface FacultyService {
    Faculty addFaculty(String name, String color);

    Faculty getFaculty(Long id);

    Faculty updateFaculty(Long id, String name, String color);

    Faculty removeFaculty(Long id);

    List<Faculty> facultyByColor(String color);

    Faculty findByName(String name);

    Faculty findByColor(String color);

    Collection<Student> getStudents(Long id);

    String getLongestFacultyName();
}
