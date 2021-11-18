package softuni.restaurant.model.binding;

import java.math.BigDecimal;
import java.util.Set;

public class ItemUpdateBindingModel {
    private String name;
    private String manufacturer;
    private Integer volume;
    private Integer weight;
    private BigDecimal price;
    private Set<String> allergens;
    private Set<String> categories;
}
