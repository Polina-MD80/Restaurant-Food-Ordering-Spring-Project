package softuni.restaurant.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.restaurant.Service.CategoryService;
import softuni.restaurant.Service.PictureService;
import softuni.restaurant.Service.cloudinary.CloudinaryService;
import softuni.restaurant.model.binding.CategoryBindingModel;
import softuni.restaurant.model.binding.CategoryUpdateBindingModel;
import softuni.restaurant.model.service.CategoryServiceModel;
import softuni.restaurant.model.service.PictureServiceModel;
import softuni.restaurant.model.view.CategoryEditView;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("categories")
public class CategoriesController {
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;
    private final CloudinaryService cloudinaryService;
    private final PictureService pictureService;

    public CategoriesController(ModelMapper modelMapper, CategoryService categoryService, CloudinaryService cloudinaryService, PictureService pictureService) {
        this.modelMapper = modelMapper;

        this.categoryService = categoryService;
        this.cloudinaryService = cloudinaryService;
        this.pictureService = pictureService;
    }


    @GetMapping("add")
    public String categoryAdd(Model model) {
        model.addAttribute("allCategories", categoryService.detAllCategories());
        return "category-add";
    }

    @ModelAttribute
    public CategoryBindingModel CategoryBindingModel() {
        return new CategoryBindingModel();
    }

    @PostMapping("add")
    public String categoryAddConf(@Valid CategoryBindingModel categoryBindingModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("categoryBindingModel", categoryBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.categoryBindingModel", bindingResult);

            return "redirect:add";
        }

        CategoryBindingModel categoryBindingModel1 = categoryBindingModel;


        CategoryServiceModel serviceModel = modelMapper.map(categoryBindingModel, CategoryServiceModel.class);
        PictureServiceModel pictureServiceModel = pictureService.savePicture(categoryBindingModel.getPicture());
        serviceModel.setPicture(pictureServiceModel);


        boolean success = categoryService.addCategory(serviceModel);
        if (!success) {
            redirectAttributes.addFlashAttribute("categoryBindingModel", categoryBindingModel);
            redirectAttributes.addFlashAttribute("inValidCategory", true);
            pictureService.deletePicture(pictureServiceModel.getPublicId(), pictureServiceModel.getId());
            return "redirect:add";
        }


        return "redirect:add";
    }




    @GetMapping("edit/{id}")
    public String editCategory(@PathVariable Long id, Model model) {

        CategoryEditView categoryEditView = categoryService.finById(id);
        if (categoryEditView == null) {
            return "redirect:add";
        }
        CategoryUpdateBindingModel categoryUpdateBindingModel = modelMapper.map(categoryEditView, CategoryUpdateBindingModel.class);

        model.addAttribute("categoryUpdateBindingModel", categoryUpdateBindingModel);

        return "update-category";


    }

    @PatchMapping("edit/{id}")
    public String editOffer(
            @PathVariable Long id,
            @Valid CategoryUpdateBindingModel categoryUpdateBindingModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) throws IOException {
        System.out.println("here");

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("categoryUpdateBindingModel", categoryUpdateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.categoryUpdateBindingModel", bindingResult);

            return "redirect:/categories/edit/" + id;
        }

        CategoryServiceModel serviceModel = modelMapper.map(categoryUpdateBindingModel, CategoryServiceModel.class);

        if (!categoryUpdateBindingModel.getPicture().isEmpty()){

            MultipartFile multipartFile = categoryUpdateBindingModel.getPicture();
            PictureServiceModel pictureServiceModel = pictureService.savePicture(multipartFile);
            serviceModel.setPicture(pictureServiceModel);

        }


        serviceModel.setId(id);
        categoryService.updateCategory(serviceModel);


        return "redirect:/categories/add";
    }

    @GetMapping
    public String categories() {
        return "categories";
    }
}
