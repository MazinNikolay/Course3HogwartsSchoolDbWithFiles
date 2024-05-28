package pro.sky.Course3HogwartsSchoolDbWithFiles.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("info")
@Tag(name = "API для получения информации о порте")
public class InfoController {
    @Value("${server.port}")
    private String port;

    @GetMapping
    @Operation(summary = "Метод для получения порта")
    public String getPort() {
        return port;
    }
}
