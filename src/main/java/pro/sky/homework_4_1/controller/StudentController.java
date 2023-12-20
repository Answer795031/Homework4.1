package pro.sky.homework_4_1.controller;

import org.springframework.web.bind.annotation.*;
import pro.sky.homework_4_1.model.Faculty;
import pro.sky.homework_4_1.model.Student;
import pro.sky.homework_4_1.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @RequestMapping(value = "/{id}/get", method = RequestMethod.GET)
    public Student getStudent(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

    @RequestMapping(value = "/post/{name}/{age}", method = RequestMethod.POST)
    public Student addStudent(@PathVariable String name,
                              @PathVariable Integer age) {
        return studentService.addStudent(name, age);
    }

    @RequestMapping(value = "/{id}/update/{name}/{age}", method = RequestMethod.PUT)
    public Student updateStudent(@PathVariable Long id,
                                 @PathVariable String name,
                                 @PathVariable Integer age) {
        return studentService.updateStudent(id, name, age);
    }

    @RequestMapping(value = "/{id}/remove", method = RequestMethod.DELETE)
    public Student removeStudent(@PathVariable Long id) {
        return studentService.removeStudent(id);
    }

    @GetMapping("/by-age/{age}")
    public Collection<Student> studentsByAge(@PathVariable Integer age) {
        return studentService.studentsByAge(age);
    }

    @GetMapping("/age-between")
    public List<Student> findAllByAgeBetween(@RequestParam Integer min, @RequestParam Integer max) {
        return studentService.findAllByAgeBetween(min, max);
    }

    @GetMapping("/faculty/{id}")
    public Faculty getFaculty(@PathVariable Long id) {
        return studentService.getFaculty(id);
    }

    @GetMapping("/total")
    public int getTotalStudents() {
        return studentService.getTotalStudents();
    }

    @GetMapping("/average-age")
    public double getAverageAge() {
        return studentService.getAverageAge();
    }

    @GetMapping("/five-last")
    public List<Student> getFiveLastStudents() {
        return studentService.getFiveLastStudents();
    }
}
