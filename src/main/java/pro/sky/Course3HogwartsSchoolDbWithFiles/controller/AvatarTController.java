package pro.sky.Course3HogwartsSchoolDbWithFiles.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Avatar;
import pro.sky.Course3HogwartsSchoolDbWithFiles.service.impl.AvatarServiceImpl;

import java.util.List;

@RestController
@Tag(name = "API  для постраничного вывода аватарок")
@RequestMapping("avatars-get-page")
public class AvatarTController {
    private final AvatarServiceImpl service;

    public AvatarTController(AvatarServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Постраничный вывод")
    public ResponseEntity<List<Avatar>> getPageAvatar(@RequestParam("page") Integer pageNumber,
                                                      @RequestParam("size") Integer size) {
        List<Avatar> avatars = service.getAvatarsPage(pageNumber, size);
        return ResponseEntity.ok(avatars);
    }
}
