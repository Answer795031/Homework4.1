package pro.sky.homework_4_1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.homework_4_1.exception.StudentNotFoundException;
import pro.sky.homework_4_1.model.Faculty;
import pro.sky.homework_4_1.model.Student;
import pro.sky.homework_4_1.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService{
    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(String name, Integer age) { // добавление записи студента
        logger.info("Was invoked method for create student");   // вывод сообщения с уровнем INFO
        Student student = new Student(name, age);
        studentRepository.save(student);
        return student;
    }

    @Override
    public Student getStudent(Long id) {
        logger.info("Was invoked method for get student");   // вывод сообщения с уровнем INFO
        if (studentRepository.findById(id).isEmpty()) { // получение записи студента
            logger.error("There is not student with id = " + id);
            throw new StudentNotFoundException("Ошибка! Студент не найден!");
        }
        return studentRepository.findById(id).get();
    }

    @Override
    public Student updateStudent(Long id, String name, Integer age) { // изменение записи студента
        logger.info("Was invoked method for update student");   // вывод сообщения с уровнем INFO
        if (studentRepository.findById(id).isEmpty()) {
            logger.error("There is not student with id = " + id + ". Current student was created with id = " + id);
            return studentRepository.save(new Student(name, age));
        }
        Student existingStudent = studentRepository.findById(id).get();
        existingStudent.setName(name);
        existingStudent.setAge(age);
        return studentRepository.save(existingStudent);
    }

    @Override
    public Student removeStudent(Long id) { // удаление записи студента
        logger.info("Was invoked method for remove student");   // вывод сообщения с уровнем INFO
        if (studentRepository.findById(id).isEmpty()) {
            logger.error("There is not student with id = " + id);
            return null;
        }
        studentRepository.deleteById(id);
        return studentRepository.findById(id).get();
    }

    @Override
    public List<Student> studentsByAge(Integer age) { // получение всех студентов по возрасту
        logger.info("Was invoked method for find all students by age");   // вывод сообщения с уровнем INFO
        return studentRepository.findAll().stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> findAllByAgeBetween(Integer min, Integer max) {
        logger.info("Was invoked method for find all students depending on age");   // вывод сообщения с уровнем INFO
        return studentRepository.findAllByAgeBetween(min, max);
    }

    @Override
    public Faculty getFaculty(Long id) {
        logger.info("Was invoked method for get faculty of current student");   // вывод сообщения с уровнем INFO
        if (studentRepository.findById(id).isEmpty()) {
            logger.error("There is not student with id = " + id);
            return null;
        }
        Student student = studentRepository.findById(id).get();
        return student.getFaculty();
    }

    @Override
    public int getTotalStudents() {
        logger.info("Was invoked method for get total counter of students");   // вывод сообщения с уровнем INFO
        return studentRepository.getTotalStudents();
    }

    @Override
    public double getAverageAge() {
        logger.info("Was invoked method for get average students age");   // вывод сообщения с уровнем INFO
        return studentRepository.getAverageAge();
    }

    @Override
    public List<Student> getFiveLastStudents() {
        logger.info("Was invoked method for get five last students in DB");   // вывод сообщения с уровнем INFO
        return studentRepository.getFiveLastStudents();
    }
}
