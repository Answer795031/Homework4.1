package pro.sky.homework_4_1.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.homework_4_1.model.Avatar;
import pro.sky.homework_4_1.model.Student;
import pro.sky.homework_4_1.repository.AvatarRepository;
import pro.sky.homework_4_1.repository.StudentRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarServiceImpl implements AvatarService {

    private final String avatarsDir;
    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;

    public AvatarServiceImpl(AvatarRepository avatarRepository, StudentRepository studentRepository, @Value("${path.to.avatars.folder}") String avatarsDir) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
        this.avatarsDir = avatarsDir;
    }

    @Override
    public ResponseEntity<String> uploadAvatar(Long id, MultipartFile file) throws IOException {
        Student student = studentRepository.findById(id).get();

        Path filePath = Path.of(avatarsDir, student + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = new Avatar();
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());
        avatarRepository.save(avatar);
        return ResponseEntity.ok().build();
    }

    private byte[] generateImagePreview(Path filePath) throws IOException{
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

    public Avatar findAvatar(Long id) {
        return avatarRepository.findById(id).orElse(new Avatar());
    }

    private String getExtension(String originalFilename) {
        return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    }

    public List<Avatar> getAvatars(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return avatarRepository.findAll(pageable).getContent();
    }

}
