package pro.sky.Course3HogwartsSchoolDbWithFiles.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Avatar;
import pro.sky.Course3HogwartsSchoolDbWithFiles.service.impl.AvatarServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("avatar")
@Tag(name = "API  для работы с аватаром")
public class AvatarController {
    private final AvatarServiceImpl service;

    public AvatarController(AvatarServiceImpl service) {
        this.service = service;
    }

    @PostMapping(value = "/{student_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка в базу данных файла")
    public ResponseEntity<String> uploadAvatar(@PathVariable Long student_id,
                                               @RequestParam MultipartFile avatar) throws IOException {
        //вызов метода загрузки аватара
        service.uploadAvatar(student_id, avatar);
        //Проверка размера файла из аргумента
        if (avatar.getSize() > 1024 * 500) {
            return ResponseEntity.badRequest().body("Размер файла превышает ограничения");
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/from-db/{id}")
    @Operation(summary = "Получение фотографии из базы данных")
    public ResponseEntity<byte[]> downloagAvatarFromDb(@PathVariable Long id) {
        Avatar avatar = service.findAvatar(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping("/from-file/{id}")
    @Operation(summary = "Получение фотографии из файла")
    public void downloadAvatarFromFile(@PathVariable Long id,
                                       HttpServletResponse response) throws IOException {
        Avatar avatar = service.findAvatar(id);
        Path path = Path.of(avatar.getFilePath());
        try (
                InputStream is = Files.newInputStream(path);
                OutputStream os = response.getOutputStream();
        ) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

    @GetMapping("/avatars-get-page")
    @Operation(summary = "Постраничный вывод")
    public ResponseEntity<List<Avatar>> getPageAvatar(@RequestParam("page") Integer pageNumber,
                                                      @RequestParam("size") Integer size) {
        List<Avatar> avatars = service.getAvatarsPage(pageNumber, size);
        return ResponseEntity.ok(avatars);
    }
}
