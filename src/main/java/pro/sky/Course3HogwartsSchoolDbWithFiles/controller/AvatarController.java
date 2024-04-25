package pro.sky.Course3HogwartsSchoolDbWithFiles.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.Course3HogwartsSchoolDbWithFiles.service.impl.AvatarServiceImpl;

import java.io.IOException;

@RestController
@RequestMapping("avatar")
@Tag(name = "API  для раюоты с аватаром")
public class AvatarController {
    private final AvatarServiceImpl service;

    public AvatarController(AvatarServiceImpl service) {
        this.service = service;
    }

    @PostMapping(value = "/{student_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка в базу данных файла")
    public ResponseEntity<String> uploadAvatar(@PathVariable Long student_id,
                                               @RequestParam MultipartFile avatar) throws IOException {
        service.uploadAvatar(student_id, avatar);
        if (avatar.getSize() > 1024 * 500) {
            return ResponseEntity.badRequest().body("Размер файла превышает ограничения");
        }
        return ResponseEntity.ok().build();
    }
}
