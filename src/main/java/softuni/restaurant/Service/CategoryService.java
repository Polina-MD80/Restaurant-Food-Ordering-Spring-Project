package softuni.restaurant.Service;

import softuni.restaurant.model.service.CategoryServiceModel;
import softuni.restaurant.model.view.CategoryEditView;
import softuni.restaurant.model.view.CategoryViewModel;

import java.util.List;

public interface CategoryService {
    boolean isCategoryNameFree(String name);

    boolean addCategory(CategoryServiceModel serviceModel);

    List<CategoryViewModel> detAllCategories();

    CategoryEditView finById(Long id);

    boolean updateCategory(CategoryServiceModel serviceModel);
}
