package softuni.restaurant.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.restaurant.model.binding.ItemAddBindingModel;
import softuni.restaurant.model.service.ItemServiceModel;
import softuni.restaurant.model.service.PictureServiceModel;
import softuni.restaurant.service.CategoryService;
import softuni.restaurant.service.ItemService;
import softuni.restaurant.service.PictureService;
import softuni.restaurant.service.ProductService;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("items")
public class ItemController {
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final ItemService itemService;
    private final ProductService productService;
    private final PictureService pictureService;


    public ItemController(CategoryService categoryService, ModelMapper modelMapper, ItemService itemService, ProductService productService, PictureService pictureService) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.itemService = itemService;
        this.productService = productService;
        this.pictureService = pictureService;
    }

    @ModelAttribute
    public ItemAddBindingModel addBindingModel(){
        return new ItemAddBindingModel();
    }

    @GetMapping("add")
    public String itemAdd(Model model) {
     model.addAttribute("categoriesByName", categoryService.getAllCategoryNames());
     model.addAttribute("allItems", itemService.getAllItems());

        return "item-add";
    }

    @PostMapping("add")
    public String itemAddConf(@Valid ItemAddBindingModel itemAddBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("itemAddBindingModel",itemAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.itemAddBindingModel", bindingResult);

            return "redirect:add";
        }
        ItemServiceModel itemServiceModel = modelMapper.map(itemAddBindingModel, ItemServiceModel.class);
        PictureServiceModel pictureServiceModel = new PictureServiceModel();
        if (!itemAddBindingModel.getPicture().isEmpty()) {
            pictureServiceModel = pictureService.savePicture(itemAddBindingModel.getPicture());
            itemServiceModel.setPicture(pictureServiceModel);
        }

        boolean success = itemService.addItem(itemServiceModel);
        if (!success) {
            redirectAttributes.addFlashAttribute("itemAddBindingModel", itemAddBindingModel);
            redirectAttributes.addFlashAttribute("inValidItemName", true);
            if (pictureServiceModel != null) {
                pictureService.deletePicture(pictureServiceModel.getPublicId(), pictureServiceModel.getId());
            }
            return "redirect:add";
        }


        return "redirect:add";
    }
}
