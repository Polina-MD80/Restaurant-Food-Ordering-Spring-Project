package softuni.restaurant.service;

import org.springframework.web.multipart.MultipartFile;
import softuni.restaurant.model.service.PictureServiceModel;

import java.io.IOException;

public interface PictureService {
    PictureServiceModel savePicture(MultipartFile multipartFile) throws IOException;



    void deletePicture(String tempPublicId, Long picId);
}
