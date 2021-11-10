package softuni.restaurant.Service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import softuni.restaurant.Repository.PictureRepository;
import softuni.restaurant.Service.PictureService;
import softuni.restaurant.Service.cloudinary.CloudinaryImage;
import softuni.restaurant.Service.cloudinary.CloudinaryService;
import softuni.restaurant.model.entity.PictureEntity;
import softuni.restaurant.model.service.PictureServiceModel;

import java.io.IOException;

@Service
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    public PictureServiceImpl(PictureRepository pictureRepository, CloudinaryService cloudinaryService, ModelMapper modelMapper) {
        this.pictureRepository = pictureRepository;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public PictureServiceModel savePicture(MultipartFile multipartFile) throws IOException {
        final CloudinaryImage uploaded = this.cloudinaryService.upload(multipartFile);


        PictureEntity pictureEntity = new PictureEntity().
                setPublicId(uploaded.getPublicId()).setUrl(uploaded.getUrl());

        pictureRepository.save(pictureEntity);


        return new PictureServiceModel().setId(pictureEntity.getId()).setUrl(pictureEntity.getUrl()).setPublicId(pictureEntity.getPublicId());

    }

//    @Override
//    public void deletePicture(PictureServiceModel pictureServiceModel) {
//        cloudinaryService.delete(pictureServiceModel.getPublicId());
//        pictureRepository.deleteById(pictureServiceModel.getId());
//    }

    @Override
    public void deletePicture(String tempPublicId, Long picId) {
        cloudinaryService.delete(tempPublicId);
        pictureRepository.deleteById(picId);
    }
}
