package softuni.restaurant.web.employees;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.restaurant.model.binding.ItemAddBindingModel;
import softuni.restaurant.model.binding.ItemUpdateBindingModel;
import softuni.restaurant.model.service.ItemServiceModel;
import softuni.restaurant.model.service.PictureServiceModel;
import softuni.restaurant.service.CategoryService;
import softuni.restaurant.service.ItemService;
import softuni.restaurant.service.PictureService;
import softuni.restaurant.service.ProductService;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("terminal/items")
public class ItemTerminalController {
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final ItemService itemService;
    private final ProductService productService;
    private final PictureService pictureService;

    public ItemTerminalController(CategoryService categoryService, ModelMapper modelMapper, ItemService itemService, ProductService productService, PictureService pictureService) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.itemService = itemService;
        this.productService = productService;
        this.pictureService = pictureService;
    }

    @GetMapping
    public String allItems(Model model) {
        model.addAttribute("allItems", itemService.getAllItems());
        return "items-terminal";
    }

    @GetMapping("add")
    public String itemAdd(Model model) {
        model.addAttribute("allCategories", categoryService.getAllCategoryEntities());
        model.addAttribute("allProducts", productService.getAllProductEntities());


        return "item-add";
    }

    @ModelAttribute
    public ItemAddBindingModel addBindingModel() {
        ItemAddBindingModel item = new ItemAddBindingModel();
        item.setActive(true);

        return item;
    }


    @PostMapping("add")
    public String itemAddConf(@Valid ItemAddBindingModel itemAddBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("itemAddBindingModel", itemAddBindingModel);
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


        return "redirect:";
    }

    @ModelAttribute
    public ItemUpdateBindingModel itemBindingModel() {

        return new ItemUpdateBindingModel();
    }

    @GetMapping("edit/{id}")
    public String itemUpdate(@PathVariable Long id, Model model) {

        ItemUpdateBindingModel item = itemService.getItemUpdateBindingModel(id);
        model.addAttribute("itemBindingModel", item);
        model.addAttribute("allCategories", categoryService.getAllCategoryEntities());
        model.addAttribute("allProducts", productService.getAllProductEntities());

        return "item-update";
    }


    @PatchMapping("edit/{id}")
    public String itemUpdateConf(@PathVariable Long id,
                                 @Valid ItemUpdateBindingModel itemBindingModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("itemBindingModel", itemBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.itemBindingModel", bindingResult);

            return "redirect:/terminal/items/edit/" + id;
        }

        ItemServiceModel itemServiceModel = modelMapper.map(itemBindingModel, ItemServiceModel.class);


        if (!itemBindingModel.getPicture().isEmpty()) {
            MultipartFile multipartFile = itemBindingModel.getPicture();
            PictureServiceModel pictureServiceModel = pictureService.savePicture(multipartFile);
            itemServiceModel.setPicture(pictureServiceModel);
        }



        boolean success = itemService.itemUpdate(itemServiceModel);
        if (!success) {
            redirectAttributes.addFlashAttribute("itemBindingModel", itemBindingModel);
            redirectAttributes.addFlashAttribute("inValidItemName", true);
//            if (pictureServiceModel != null) {
//                pictureService.deletePicture(pictureServiceModel.getPublicId(), pictureServiceModel.getId());
//            }
            return "redirect:/terminal/items/edit/" + id;
        }


        return "redirect:/terminal/items";
    }
}
