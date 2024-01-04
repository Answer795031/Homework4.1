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

    @Override
    public List<String> getNamesStartsWithA() {
        logger.info("Was invoked method for get all students with name starts by A");   // вывод сообщения с уровнем INFO
        String symbol = "A";
        return studentRepository.findAll().stream()
                .map(student -> student.getName().toUpperCase())
                .filter(name -> name.startsWith(symbol))
                .collect(Collectors.toList());
    }

    @Override
    public double getAverageAgeStream() {
        logger.info("Was invoked method for get average students age");   // вывод сообщения с уровнем INFO
        return studentRepository.findAll().stream()
                .mapToDouble(Student::getAge)
                .average()
                .orElse(-1);
    }

    public void printStudentsParallel() {
        List<Student> students = studentRepository.findAll();

        // вывод первых двух студентов в main-потоке
        printStudent(students.get(0));
        printStudent(students.get(1));

        // вывод 3 и 4 студента в параллельном потоке №1
        Thread thread1 = new Thread(() -> {
            printStudent(students.get(2));
            printStudent(students.get(3));
        });
        thread1.start();

        // вывод 5 и 6 студентов в параллельном потоке №2
        Thread thread2 = new Thread(() -> {
            printStudent(students.get(4));
            printStudent(students.get(5));
        });
        thread2.start();
    }

    public void printStudentsSynchronized() {
        List<Student> students = studentRepository.findAll();

        // вывод первых двух студентов в main-потоке
        printStudentSync(students.get(0));
        printStudentSync(students.get(1));

        // вывод 3 и 4 студента в параллельном потоке №1
        Thread thread1 = new Thread(() -> {
            printStudentSync(students.get(2));
            printStudentSync(students.get(3));
        });
        thread1.start();

        // вывод 5 и 6 студентов в параллельном потоке №2
        Thread thread2 = new Thread(() -> {
            printStudentSync(students.get(4));
            printStudentSync(students.get(5));
        });
        thread2.start();
    }

    // метод для параллельного вывода
    private void printStudent(Student student) {
        logger.info("Thread: {}. Student: {}", Thread.currentThread(), student);
    }

    // метод для синхронизированного вывода
    private synchronized void printStudentSync(Student student) {
        printStudent(student);
    }
}
