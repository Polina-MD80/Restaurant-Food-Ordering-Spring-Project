package softuni.restaurant.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import softuni.restaurant.repository.PictureRepository;
import softuni.restaurant.service.PictureService;
import softuni.restaurant.service.cloudinary.CloudinaryImage;
import softuni.restaurant.service.cloudinary.CloudinaryService;
import softuni.restaurant.model.entity.PictureEntity;
import softuni.restaurant.model.service.PictureServiceModel;

import java.io.IOException;
import java.util.List;

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

    @Override
    public void initializePictures() {
        if (pictureRepository.count() == 0) {
            PictureEntity p1 = new PictureEntity()
                    .setUrl("https://res.cloudinary.com/dme8kfjih/image/upload/v1637321307/Restaurant_DB/BASICALLY-Swordfish_ov4vby.jpg")
                    .setPublicId("Restaurant_DB/BASICALLY-Swordfish_ov4vby");

            PictureEntity p2 = new PictureEntity()
                    .setUrl("https://res.cloudinary.com/dme8kfjih/image/upload/v1637305969/Restaurant_DB/Mushroom-Soup_xtkbzp.jpg")
                    .setPublicId("Restaurant_DB/Mushroom-Soup_xtkbzp");

            PictureEntity p3 = new PictureEntity()
                    .setUrl("https://res.cloudinary.com/dme8kfjih/image/upload/v1637321307/Restaurant_DB/One-Pan-Fish-Dish_njg3qp.jpg")
                    .setPublicId("Restaurant_DB/One-Pan-Fish-Dish_njg3qp");

            PictureEntity p4 = new PictureEntity()
                    .setUrl("https://res.cloudinary.com/dme8kfjih/image/upload/v1637305970/Restaurant_DB/caesarsalad_o61whk.jpg")
                    .setPublicId("Restaurant_DB/caesarsalad_o61whk");

            PictureEntity p5 = new PictureEntity()
                    .setUrl("https://res.cloudinary.com/dme8kfjih/image/upload/v1637305970/Restaurant_DB/chicken-and-sweet-corn-soup-3787-1_wbyn26.jpg")
                    .setPublicId("Restaurant_DB/chicken-and-sweet-corn-soup-3787-1_wbyn26");
            PictureEntity p6 = new PictureEntity()
                    .setUrl("https://res.cloudinary.com/dme8kfjih/image/upload/v1637414596/Restaurant_DB/main-dish-salad_jwyaxv.jpg")
                    .setPublicId("Restaurant_DB/main-dish-salad_jwyaxv");
            PictureEntity p7 = new PictureEntity()
                    .setUrl("https://res.cloudinary.com/dme8kfjih/image/upload/v1637305970/Restaurant_DB/malasian_soup_l5bkbg.jpg")
                    .setPublicId("Restaurant_DB/malasian_soup_l5bkbg");

            PictureEntity p8 = new PictureEntity()
                    .setUrl("https://res.cloudinary.com/dme8kfjih/image/upload/v1637305970/Restaurant_DB/shrimpsoup_xrva88.jpg")
                    .setPublicId("Restaurant_DB/shrimpsoup_xrva88");

            PictureEntity p9 = new PictureEntity()
                    .setUrl("https://res.cloudinary.com/dme8kfjih/image/upload/v1637323603/Restaurant_DB/pizza_rel6fg.jpg")
                    .setPublicId("Restaurant_DB/pizza_rel6fg");
       pictureRepository.saveAll(List.of(p1,p2,p3,p4,p5,p6,p7,p8,p9));
        }
    }

    @Override
    public PictureEntity findPictureByIt(Long id) {
        return pictureRepository.findById(id).orElse(null);
    }
}
