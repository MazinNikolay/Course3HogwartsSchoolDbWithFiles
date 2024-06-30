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
//Сервис для работы с сущностями аватары
@Service
//Создаем прокси класс
@Transactional
public class AvatarServiceImpl implements AvatarService {
    //Инжектим репозитории
    private final AvatarRepository avatarRepository;
    private final StudentService studentService;
    private Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);
    //Переменная пути хранения файлов из properties
    @Value("${path.to.avatars.folders}")
    private String avatarsDir;
    //Конструктор
    public AvatarServiceImpl(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }
    //Метод загрузки аватара
    @Override
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("Was invoked method for upload Avatar to DB");
        //Получение студента по аргументу Id
        Student student = studentService.getStudent(studentId);
        //Задаем полное имя файла с именем студента и расширением передаваемой в аргументе картинки
        Path filePath = Path.of(avatarsDir, student + "."
                + getExtentions(avatarFile.getOriginalFilename()));
        //Создаем структуру папок
        Files.createDirectories(filePath.getParent());
        //Удаление уже существующих файлов
        Files.deleteIfExists(filePath);
        //Создание входных и выходных потоков
        try (
                //Входной поток из файла из аргумента
                InputStream is = avatarFile.getInputStream();
                //Новый выходной поток в создаваемый файл
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            //Буферизированная запись файла из аргумента в создаваемый файл
            bis.transferTo(bos);
        }
        //Получение или создание сущности аватар для студента из аргумента
        Avatar avatar = findAvatar(studentId);
        //Присвоение значений полям сущности
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        //Запись преобразованного изображения аватара в поле
        avatar.setData(generateDataForDb(filePath));
        //Сохранение измененной или созданной сущности в БД
        avatarRepository.save(avatar);
    }
    //Метод преобразования данных аватара для БД
    private byte[] generateDataForDb(Path filePath) throws IOException {
        logger.info("Was invoked method for convert image Avatar");
        //Создание входных и выходных потоков
        try (
                //Входной поток из файла из аргумента (редактируемый/созданный файл)
                InputStream is = Files.newInputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                //Выходной байтовый поток для записи картинки в таблицу
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ) {
            //создаем изображение из входного стрима, хранящееся в памяти
            BufferedImage image = ImageIO.read(bis);
            //Уменьшаем высоту рисунка
            int height = image.getHeight() / (image.getHeight() / 100);
            //Создаем изображение с измененными размерами
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            //Создаем объект для графики, редактируем и переносим наисходный объект
            Graphics2D graphics2D = preview.createGraphics();
            graphics2D.drawImage(image, 0, 0, 100, height, null);
            graphics2D.dispose();
            //Запись измененного изображения в байтовый стрим с указанием формата названия
            ImageIO.write(preview, getExtentions(filePath.getFileName().toString()), baos);
            //Возвращаем байтовый массив из выходного потока
            return baos.toByteArray();
        }
    }
    //Поиск илисоздание нвой сущности аватара по Id студента
    @Override
    public Avatar findAvatar(Long studentId) {
        logger.info("Was invoked method for find Avatar or create");
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }
    //Получение расширения файла
    private String getExtentions(String fileName) {
        logger.info("Was invoked method for get file name");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
    //Постраничный вывод сущностей аватаров
    @Override
    public List<Avatar> getAvatarsPage(Integer pageNumber, Integer size) {
        logger.info("Was invoked method for get Avatars from DB page present");
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, size);
        return avatarRepository.findAll(pageRequest).getContent();
    }
}