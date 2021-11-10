package softuni.restaurant.model.entity.enums;

public enum AllergenEnumName {
    WHEAT("https://img.icons8.com/plasticine/50/000000/wheat.png"),
    MILK("https://img.icons8.com/plasticine/50/000000/milk-bottle.png"),
    EGG("https://img.icons8.com/plasticine/50/000000/sunny-side-up-eggs.png"),
    NUT("https://img.icons8.com/color/50/000000/peanuts.png"),
    SOY("https://img.icons8.com/plasticine/50/000000/soy.png"),
    BEE("https://img.icons8.com/plasticine/50/000000/bee.png"),
    FISH("https://img.icons8.com/plasticine/50/000000/fish-food.png"),
    SEED("https://img.icons8.com/plasticine/50/000000/paper-bag-with-seeds.png"),
    MOLLUSCS("https://img.icons8.com/plasticine/50/000000/shellfish.png");


    private final String imageUrl;
    AllergenEnumName(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
