package pro.sky.homework_4_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.homework_4_1.model.Faculty;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    public Faculty findByNameIgnoreCase(String name);

    public Faculty findByColorIgnoreCase(String color);
}
