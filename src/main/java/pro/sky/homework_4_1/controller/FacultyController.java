package pro.sky.homework_4_1.controller;

import org.springframework.web.bind.annotation.*;
import pro.sky.homework_4_1.model.Faculty;
import pro.sky.homework_4_1.model.Student;
import pro.sky.homework_4_1.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @RequestMapping(value = "/{id}/get", method = RequestMethod.GET)
    public Faculty getFaculty(@PathVariable Long id) {
        return facultyService.getFaculty(id);
    }

    @RequestMapping(value = "/post/{name}/{color}", method = RequestMethod.POST)
    public Faculty addFaculty(@PathVariable String name,
                              @PathVariable String color) {
        return facultyService.addFaculty(name, color);
    }

    @RequestMapping(value = "/{id}/update/{name}/{color}", method = RequestMethod.PUT)
    public Faculty updateFaculty(@PathVariable Long id,
                                 @PathVariable String name,
                                 @PathVariable String color) {
        return facultyService.updateFaculty(id, name, color);
    }

    @RequestMapping(value = "/{id}/remove", method = RequestMethod.DELETE)
    public Faculty removeFaculty(@PathVariable Long id) {
        return facultyService.removeFaculty(id);
    }

    @GetMapping("/color/{color}")
    public Collection<Faculty> facultyByColor(@PathVariable String color) {
        return facultyService.facultyByColor(color);
    }

    @GetMapping("/name-or-color")
    public Faculty findByNameOrColor(@RequestParam(required = false) String name,
                                               @RequestParam(required = false) String color) {
        if (name != null && !name.isBlank()) {
            return facultyService.findByName(name);
        }
        if (color != null && !color.isBlank()) {
            return facultyService.findByColor(color);
        }
        return null;
    }

    @GetMapping("/student/{id}")
    public Collection<Student> getStudents(@PathVariable Long id) {
        return facultyService.getStudents(id);
    }

    @GetMapping("/longest-faculty-name")
    public String getLongestFacultyName() {
        return facultyService.getLongestFacultyName();
    }
}
