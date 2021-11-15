package softuni.restaurant.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.restaurant.model.binding.ProductAddBindingModel;
import softuni.restaurant.model.binding.ProductUpdateBindingModel;
import softuni.restaurant.model.service.ProductServiceModel;
import softuni.restaurant.model.view.ProductEditView;
import softuni.restaurant.service.AllergenService;
import softuni.restaurant.service.ProductService;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("products")
public class ProductsController {
    private final AllergenService allergenService;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    public ProductsController(AllergenService allergenService, ProductService productService, ModelMapper modelMapper) {
        this.allergenService = allergenService;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute
    public ProductAddBindingModel productAddBindingModel() {
        return new ProductAddBindingModel();
    }

    @GetMapping("add")
    public String products(Model model) {
        model.addAttribute("allergens", allergenService.allAllergensOrderedByName());
        model.addAttribute("allProducts", productService.allProducts());
        return "product-add";
    }

    @PostMapping("add")
    public String productAddConf(@Valid ProductAddBindingModel productAddBindingModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productAddBindingModel", productAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productAddBindingModel", bindingResult);

            return "redirect:add";
        }


        ProductServiceModel productServiceModel = modelMapper.map(productAddBindingModel, ProductServiceModel.class);


        boolean success = productService.addProduct(productServiceModel);
        if (!success) {
            redirectAttributes.addFlashAttribute("productAddBindingModel", productAddBindingModel);
            redirectAttributes.addFlashAttribute("inValidProduct", true);

            return "redirect:add";
        }


        return "redirect:add";
    }


    @GetMapping("edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {

        ProductEditView productEditView = productService.findById(id);

        if (productEditView == null) {
            return "redirect:add";
        }

        ProductUpdateBindingModel productUpdateBindingModel = modelMapper.map(productEditView, ProductUpdateBindingModel.class);

        model.addAttribute("productUpdateBindingModel", productUpdateBindingModel);


        return "update-product";
    }

    @PatchMapping("edit/{id}")
    public String editProductConf(
            @PathVariable Long id,
            @Valid ProductUpdateBindingModel productUpdateBindingModel,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productUpdateBindingModel", productUpdateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productUpdateBindingModel", bindingResult);

            return "redirect: /products/edit" + id;
        }

        ProductServiceModel productServiceModel = modelMapper.map(productUpdateBindingModel, ProductServiceModel.class);

        boolean success = productService.addProduct(productServiceModel);

        if (!success) {
            redirectAttributes.addFlashAttribute("productUpdateBindingModel", productUpdateBindingModel);
            redirectAttributes.addFlashAttribute("productNameOccupied", true);
            return "redirect:/products/edit" + id;
        }


        return "redirect:/products/add";
    }


    //TODO Delete


}
