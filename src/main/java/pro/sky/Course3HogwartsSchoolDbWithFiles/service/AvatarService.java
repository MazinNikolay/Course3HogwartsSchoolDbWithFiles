package pro.sky.Course3HogwartsSchoolDbWithFiles.service;

import org.springframework.web.multipart.MultipartFile;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Avatar;

import java.io.IOException;
import java.util.List;

public interface AvatarService {

    void uploadAvatar(Long sudentId, MultipartFile avatarFile) throws IOException;

    Avatar findAvatar(Long studentId);

    List<Avatar> getAvatarsPage(Integer pageNumber, Integer size);

}
