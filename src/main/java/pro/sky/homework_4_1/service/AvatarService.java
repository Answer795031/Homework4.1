package pro.sky.homework_4_1.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.homework_4_1.model.Avatar;

import java.io.IOException;
import java.util.List;

public interface AvatarService {
    ResponseEntity<String> uploadAvatar(Long id, MultipartFile avatar) throws IOException;

    Avatar findAvatar(Long id);

    List<Avatar> getAvatars(int page, int size);
}
