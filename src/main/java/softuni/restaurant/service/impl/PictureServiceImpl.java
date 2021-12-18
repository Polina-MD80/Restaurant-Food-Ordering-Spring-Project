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
                    .setUrl("https://res.cloudinary.com/dme8kfjih/image/upload/v1639863732/Restaurant_DB/chicken-and-sweet-corn-soup-3787-1_ijxkam.jpg")
                    .setPublicId("Restaurant_DB/chicken-and-sweet-corn-soup-3787-1_ijxkam");
            PictureEntity p6 = new PictureEntity()
                    .setUrl("https://res.cloudinary.com/dme8kfjih/image/upload/v1639863652/Restaurant_DB/main-dish-salad_hik8zu.jpg")
                    .setPublicId("Restaurant_DB/main-dish-salad_hik8zu");
            PictureEntity p7 = new PictureEntity()
                    .setUrl("https://res.cloudinary.com/dme8kfjih/image/upload/v1637323707/Restaurant_DB/malasian_soup_qx2ixu.jpg")
                    .setPublicId("Restaurant_DB/malasian_soup_qx2ixu");

            PictureEntity p8 = new PictureEntity()
                    .setUrl("https://res.cloudinary.com/dme8kfjih/image/upload/v1639863525/Restaurant_DB/shrimpsoup_rgm8ph.jpg")
                    .setPublicId("Restaurant_DB/shrimpsoup_rgm8ph");

            PictureEntity p9 = new PictureEntity()
                    .setUrl("https://res.cloudinary.com/dme8kfjih/image/upload/v1639863450/Restaurant_DB/pizza_eqozmw.jpg")
                    .setPublicId("Restaurant_DB/pizza_eqozmw");
            PictureEntity p10 = new PictureEntity()
                    .setUrl("https://res.cloudinary.com/dme8kfjih/image/upload/v1639864015/Restaurant_DB/ilhce69fpr8esypgspmr.jpg")
                    .setPublicId("Restaurant_DB/ilhce69fpr8esypgspmr");
            PictureEntity p11 = new PictureEntity()
                    .setUrl("https://res.cloudinary.com/dme8kfjih/image/upload/v1639864345/Restaurant_DB/beer_tqzvfn.jpg")
                    .setPublicId("Restaurant_DB/beer_tqzvfn");
       pictureRepository.saveAll(List.of(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11));
        }
    }

    @Override
    public PictureEntity findPictureByIt(Long id) {
        return pictureRepository.findById(id).orElse(null);
    }
}
