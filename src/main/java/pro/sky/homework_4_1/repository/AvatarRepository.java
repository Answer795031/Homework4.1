package pro.sky.homework_4_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.homework_4_1.model.Avatar;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {

}
