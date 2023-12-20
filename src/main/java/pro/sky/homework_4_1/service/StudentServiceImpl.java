package pro.sky.homework_4_1.service;

import org.springframework.stereotype.Service;
import pro.sky.homework_4_1.exception.StudentNotFoundException;
import pro.sky.homework_4_1.model.Faculty;
import pro.sky.homework_4_1.model.Student;
import pro.sky.homework_4_1.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService{
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(String name, Integer age) { // добавление записи студента
        Student student = new Student(name, age);
        studentRepository.save(student);
        return student;
    }

    @Override
    public Student getStudent(Long id) {
        if (studentRepository.findById(id).isEmpty()) { // получение записи студента
            throw new StudentNotFoundException("Ошибка! Студент не найден!");
        }
        return studentRepository.findById(id).get();
    }

    @Override
    public Student updateStudent(Long id, String name, Integer age) { // изменение записи студента
        if (studentRepository.findById(id).isEmpty()) {
            return studentRepository.save(new Student(name, age));
        }
        Student existingStudent = studentRepository.findById(id).get();
        existingStudent.setName(name);
        existingStudent.setAge(age);
        return studentRepository.save(existingStudent);
    }

    @Override
    public Student removeStudent(Long id) { // удаление записи студента
        if (studentRepository.findById(id).isEmpty()) {
            return null;
        }
        studentRepository.deleteById(id);
        return studentRepository.findById(id).get();
    }

    @Override
    public List<Student> studentsByAge(Integer age) { // получение всех студентов по возрасту
        return studentRepository.findAll().stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> findAllByAgeBetween(Integer min, Integer max) {
        return studentRepository.findAllByAgeBetween(min, max);
    }

    @Override
    public Faculty getFaculty(Long id) {
        if (studentRepository.findById(id).isEmpty()) {
            return null;
        }
        Student student = studentRepository.findById(id).get();
        return student.getFaculty();
    }

    @Override
    public int getTotalStudents() {
        return studentRepository.getTotalStudents();
    }

    @Override
    public double getAverageAge() {
        return studentRepository.getAverageAge();
    }

    @Override
    public List<Student> getFiveLastStudents() {
        return studentRepository.getFiveLastStudents();
    }
}
