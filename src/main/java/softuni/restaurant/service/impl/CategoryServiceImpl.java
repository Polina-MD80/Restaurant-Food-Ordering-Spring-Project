package softuni.restaurant.service.impl;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.restaurant.repository.CategoryRepository;
import softuni.restaurant.service.CategoryService;
import softuni.restaurant.service.PictureService;
import softuni.restaurant.model.entity.CategoryEntity;
import softuni.restaurant.model.entity.PictureEntity;
import softuni.restaurant.model.service.CategoryServiceModel;
import softuni.restaurant.model.view.CategoryEditView;
import softuni.restaurant.model.view.CategoryViewModel;
import softuni.restaurant.model.view.PictureViewModel;
import softuni.restaurant.web.exception.ObjectNotFoundException;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final PictureService pictureService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, PictureService pictureService) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.pictureService = pictureService;
    }

    @Override
    public boolean isCategoryNameFree(String name) {
        return !categoryRepository.existsByName(name);
    }

    @Override
    public boolean addCategory(CategoryServiceModel serviceModel) {
        CategoryEntity categoryEntity = modelMapper.map(serviceModel, CategoryEntity.class);

        try {
            categoryRepository.save(categoryEntity);
        } catch (PersistenceException e) {
            return false;
        }

        return true;
    }

    @Override
    public List<CategoryViewModel> detAllCategories() {
        return categoryRepository.findAllOrderedByName().stream()
                .map(categoryEntity -> {
                    CategoryViewModel categoryViewModel = modelMapper.map(categoryEntity, CategoryViewModel.class);
                    if (categoryViewModel.getPicture() == null) {
                        categoryViewModel.setPicture(new PictureViewModel().setUrl("https://img.icons8.com/dusk/64/000000/kawaii-bread-1.png"));
                    }
                    return categoryViewModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CategoryEditView finById(Long id) {

        CategoryEntity categoryEntity = categoryRepository.findById(id).orElse(null);
        return modelMapper.map(categoryEntity, CategoryEditView.class);


    }

    @Override
    public boolean updateCategory(CategoryServiceModel serviceModel) {
        CategoryEntity categoryEntity = categoryRepository.findById(serviceModel.getId()).orElseThrow(() ->
                new ObjectNotFoundException("Category with id " + serviceModel.getId() + " not found!"));

        assert categoryEntity != null;
        categoryEntity.setName(serviceModel.getName())
                .setDescription(serviceModel.getDescription());

        if (serviceModel.getPicture() != null) {
            String tempPublicId = "";
            Long tempPicId = 0L;

            if (categoryEntity.getPicture() != null) {
                tempPublicId = categoryEntity.getPicture().getPublicId();
                tempPicId = categoryEntity.getPicture().getId();
            }
            PictureEntity pictureEntity = modelMapper.map(serviceModel.getPicture(), PictureEntity.class);
            categoryEntity.setPicture(pictureEntity);
            try {

                pictureService.deletePicture(tempPublicId, tempPicId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            categoryRepository.save(categoryEntity);
        }catch (Exception e){
            System.err.println("HOHO Failed");
            return false;
        }
        return true;

    }

    @Override
    public void deleteCategory(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(()->new ObjectNotFoundException("Category with id " + id + " not found!"));

        PictureEntity picture = categoryEntity.getPicture();
        categoryEntity.setPicture(null);
        if (picture!=null){
            pictureService.deletePicture(picture.getPublicId(),picture.getId());
        }

        categoryRepository.delete(categoryEntity);


    }

    @Override
    public List<String> getAllCategoryNames() {

       return categoryRepository.findAllOrderedByName().stream().map(CategoryEntity::getName).collect(Collectors.toList());
    }
}
