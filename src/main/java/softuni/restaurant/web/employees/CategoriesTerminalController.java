package softuni.restaurant.web.employees;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.restaurant.model.binding.CategoryAddBindingModel;
import softuni.restaurant.model.binding.CategoryUpdateBindingModel;
import softuni.restaurant.model.service.CategoryServiceModel;
import softuni.restaurant.model.service.PictureServiceModel;
import softuni.restaurant.model.view.CategoryEditView;
import softuni.restaurant.service.CategoryService;
import softuni.restaurant.service.ItemService;
import softuni.restaurant.service.PictureService;
import softuni.restaurant.web.exception.ObjectNotFoundException;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("terminal/categories")
public class CategoriesTerminalController {
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final ItemService itemService;
    private final PictureService pictureService;

    public CategoriesTerminalController(CategoryService categoryService, ModelMapper modelMapper, ItemService itemService, PictureService pictureService) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.itemService = itemService;
        this.pictureService = pictureService;
    }

    @GetMapping
    public String categories(Model model) {
        model.addAttribute("allCategories", categoryService.getAllCategories());
        return "categories-terminal";
    }

    @GetMapping("cat/{name}")
    public String getItemsByCategory(@PathVariable String name, Model model){
        model.addAttribute("itemsByType", itemService.getAllByCategoryName(name));
        return "foods";
    }

    @GetMapping("add")
    public String categoryAdd(Model model) {
        model.addAttribute("allCategories", categoryService.getAllCategories());
        return "category-add";
    }

    @ModelAttribute
    public CategoryAddBindingModel CategoryBindingModel() {
        return new CategoryAddBindingModel();
    }

    @PostMapping("add")
    public String categoryAddConf(@Valid CategoryAddBindingModel categoryAddBindingModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("categoryAddBindingModel", categoryAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.categoryAddBindingModel", bindingResult);

            return "redirect:add";
        }





        CategoryServiceModel serviceModel = modelMapper.map(categoryAddBindingModel, CategoryServiceModel.class);
        PictureServiceModel pictureServiceModel = new PictureServiceModel();
        if (!categoryAddBindingModel.getPicture().isEmpty()) {
            pictureServiceModel = pictureService.savePicture(categoryAddBindingModel.getPicture());
            serviceModel.setPicture(pictureServiceModel);
        }


        boolean success = categoryService.addCategory(serviceModel);
        if (!success) {
            redirectAttributes.addFlashAttribute("categoryAddBindingModel", categoryAddBindingModel);
            redirectAttributes.addFlashAttribute("inValidCategory", true);
            if (pictureServiceModel != null) {
                pictureService.deletePicture(pictureServiceModel.getPublicId(), pictureServiceModel.getId());
            }


            return "redirect:add";
        }
        redirectAttributes.addFlashAttribute("success","Category " + categoryAddBindingModel.getName() +" has been added successfully." );

        return "redirect:/terminal/categories";
    }


    @GetMapping("edit/{id}")
    public String editCategory(@PathVariable Long id, Model model) {

        CategoryEditView categoryEditView = categoryService.finById(id);
        if (categoryEditView == null) {
            return "redirect:add";
        }
        CategoryUpdateBindingModel categoryUpdateBindingModel = modelMapper.map(categoryEditView, CategoryUpdateBindingModel.class);

        model.addAttribute("categoryUpdateBindingModel", categoryUpdateBindingModel);

        return "category-update";


    }

    @PatchMapping("edit/{id}")
    public String editOffer(
            @PathVariable Long id,
            @Valid CategoryUpdateBindingModel categoryUpdateBindingModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) throws IOException {


        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("categoryUpdateBindingModel", categoryUpdateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.categoryUpdateBindingModel", bindingResult);

            return "redirect:/terminal/categories/edit/" + id;
        }

        CategoryServiceModel serviceModel = modelMapper.map(categoryUpdateBindingModel, CategoryServiceModel.class);

        if (!categoryUpdateBindingModel.getPicture().isEmpty()) {

            MultipartFile multipartFile = categoryUpdateBindingModel.getPicture();
            PictureServiceModel pictureServiceModel = pictureService.savePicture(multipartFile);
            serviceModel.setPicture(pictureServiceModel);

        }


        serviceModel.setId(id);
        boolean success = categoryService.updateCategory(serviceModel);
        if (!success) {
            redirectAttributes.addFlashAttribute("categoryUpdateBindingModel", categoryUpdateBindingModel);
            redirectAttributes.addFlashAttribute("categoryNameOccupied", true);
            return "redirect:/terminal/categories/edit/" + id;
        }
        redirectAttributes.addFlashAttribute("success","Category with id " + categoryUpdateBindingModel.getId() +" has been updated successfully." );

      return "redirect:/terminal/categories";
    }

    @DeleteMapping("delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.deleteCategory(id);
            redirectAttributes.addFlashAttribute("success","Category with id " + id +" has been delete successfully." );
        }catch (ObjectNotFoundException ex){
            redirectAttributes.addFlashAttribute("success", ex.getMessage());
        }

        return "redirect:/terminal/categories";
    }



}
