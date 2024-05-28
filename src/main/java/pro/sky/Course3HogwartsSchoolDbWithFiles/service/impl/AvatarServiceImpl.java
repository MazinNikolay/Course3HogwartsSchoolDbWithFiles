package pro.sky.Course3HogwartsSchoolDbWithFiles.service.impl;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Avatar;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Student;
import pro.sky.Course3HogwartsSchoolDbWithFiles.repository.AvatarRepository;
import pro.sky.Course3HogwartsSchoolDbWithFiles.service.AvatarService;
import pro.sky.Course3HogwartsSchoolDbWithFiles.service.StudentService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {

    private final AvatarRepository avatarRepository;
    private final StudentService studentService;
    Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);

    @Value("${path.to.avatars.folders}")
    private String avatarsDir;

    public AvatarServiceImpl(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    @Override
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("Was invoked method for upload Avatar to DB");
        Student student = studentService.getStudent(studentId);
        Path filePath = Path.of(avatarsDir, student + "."
                + getExtentions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(generateDataForDb(filePath));
        avatarRepository.save(avatar);
    }

    private byte[] generateDataForDb(Path filePath) throws IOException {
        logger.info("Was invoked method for convert image Avatar");
        try (
                InputStream is = Files.newInputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ) {
            BufferedImage image = ImageIO.read(bis);
            int height = image.getHeight() / (image.getHeight() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics2D = preview.createGraphics();
            graphics2D.drawImage(image, 0, 0, 100, height, null);
            graphics2D.dispose();
            ImageIO.write(preview, getExtentions(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

    public Avatar findAvatar(Long studentId) {
        logger.info("Was invoked method for find Avatar or create");
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }

    private String getExtentions(String fileName) {
        logger.info("Was invoked method for get file name");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public List<Avatar> getAvatarsPage(Integer pageNumber, Integer size) {
        logger.info("Was invoked method for get Avatars from DB page present");
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, size);
        return avatarRepository.findAll(pageRequest).getContent();
    }
}