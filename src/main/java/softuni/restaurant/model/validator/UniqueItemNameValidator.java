package softuni.restaurant.model.validator;

import softuni.restaurant.service.ItemService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueItemNameValidator implements ConstraintValidator<UniqueItemName, String> {
    private final ItemService itemService;

    public UniqueItemNameValidator(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null){
            return true;
        }
        return itemService.isItemNameFree(name);
    }
}
