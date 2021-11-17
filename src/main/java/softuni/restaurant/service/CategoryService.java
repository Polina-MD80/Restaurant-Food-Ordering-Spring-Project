package softuni.restaurant.service;

import softuni.restaurant.model.entity.CategoryEntity;
import softuni.restaurant.model.service.CategoryServiceModel;
import softuni.restaurant.model.view.CategoryEditView;
import softuni.restaurant.model.view.CategoryViewModel;

import java.util.List;

public interface CategoryService {
    boolean isCategoryNameFree(String name);

    boolean addCategory(CategoryServiceModel serviceModel);

    List<CategoryViewModel> getAllCategories();

    CategoryEditView finById(Long id);

    boolean updateCategory(CategoryServiceModel serviceModel);

    void deleteCategory(Long id);

    List<String> getAllCategoryNames();

    CategoryEntity findCategoryByName(String name);
}
