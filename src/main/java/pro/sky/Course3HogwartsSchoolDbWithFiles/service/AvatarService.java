package pro.sky.Course3HogwartsSchoolDbWithFiles.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AvatarService {

    void uploadAvatar(Long sudentId, MultipartFile avatarFile) throws IOException;
}
