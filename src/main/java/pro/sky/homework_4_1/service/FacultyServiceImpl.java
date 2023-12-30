package pro.sky.homework_4_1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.homework_4_1.exception.StudentNotFoundException;
import pro.sky.homework_4_1.model.Faculty;
import pro.sky.homework_4_1.model.Student;
import pro.sky.homework_4_1.repository.FacultyRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService{
    private final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty addFaculty(String name, String color) { // добавление записи факультета
        logger.info("Was invoked method for create faculty");   // вывод сообщения с уровнем INFO
        Faculty faculty = new Faculty(name, color);
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getFaculty(Long id) { // получение записи факультета
        logger.info("Was invoked method for get faculty");   // вывод сообщения с уровнем INFO
        if (facultyRepository.findById(id).isEmpty()) {
            logger.error("There is not faculty with id = " + id);
            throw new StudentNotFoundException("Ошибка! Факультет не найден!");
        }
        return facultyRepository.findById(id).get();
    }

    @Override
    public Faculty updateFaculty(Long id, String name, String color) { // изменение записи факультета
        logger.info("Was invoked method for update faculty");   // вывод сообщения с уровнем INFO
        if (facultyRepository.findById(id).isEmpty()) {
            logger.error("There is not faculty with id = " + id + ". Current faculty was created with id = " + id);
            return facultyRepository.save(new Faculty(name, color));
        }
        Faculty existingFaculty = facultyRepository.findById(id).get();
        existingFaculty.setName(name);
        existingFaculty.setColor(color);
        return facultyRepository.save(existingFaculty);
    }

    @Override
    public Faculty removeFaculty(Long id) { // удаление записи факультета
        logger.info("Was invoked method for remove faculty");   // вывод сообщения с уровнем INFO
        if (facultyRepository.findById(id).isEmpty()) {
            logger.error("There is not faculty with id = " + id);
            return null;
        }
        facultyRepository.deleteById(id);
        return facultyRepository.findById(id).get();
    }

    @Override
    public List<Faculty> facultyByColor(String color) { // получение всех факультетов по цвету
        logger.info("Was invoked method for find all faculties by color");   // вывод сообщения с уровнем INFO
        return facultyRepository.findAll().stream()
                .filter(student -> student.getColor().equals(color))
                .collect(Collectors.toList());
    }

    @Override
    public Faculty findByName(String name) {
        logger.info("Was invoked method for find faculty by name");   // вывод сообщения с уровнем INFO
        return facultyRepository.findByNameIgnoreCase(name);
    }

    @Override
    public Faculty findByColor(String color) {
        logger.info("Was invoked method for find faculty by color");   // вывод сообщения с уровнем INFO
        return facultyRepository.findByColorIgnoreCase(color);
    }

    @Override
    public Collection<Student> getStudents(Long id) {
        logger.info("Was invoked method for get students with current faculty");   // вывод сообщения с уровнем INFO
        if (facultyRepository.findById(id).isEmpty()) {
            logger.error("There is not faculty with id = " + id);
            return null;
        }
        Faculty faculty = facultyRepository.findById(id).get();
        return faculty.students;
    }

}
