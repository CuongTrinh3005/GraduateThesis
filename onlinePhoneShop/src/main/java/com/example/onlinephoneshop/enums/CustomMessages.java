package com.example.onlinephoneshop.enums;

public enum CustomMessages {
    CATEGORY_NAME_EXISTED("This category name has already been existed!"),
    CATEGORY_ID_EXISTED("This category id has already been existed!"),
    CATEGORY_NOT_FOUND("Category with Id %s not found"),
    NOT_DELETE_CATEGORY("Can not delete category has products"),
    BRAND_NOT_FOUND("Brand not be found!"),
    BRAND_ID_EXISTED("This brand id has already been existed!"),
    BRAND_NAME_EXISTED("This brand name has already been existed!"),
    NOT_DELETE_BRAND("Can not delete brand has products"),
    MANUFACTURER_NOT_FOUND("Manufacturer not be found!"),
    MANUFACTURER_ID_EXISTED("This manufacturer id has already been existed!"),
    MANUFACTURER_NAME_EXISTED("This manufacturer name has already been existed!"),
    NOT_DELETE_MANUFACTURER("Can not delete manufacturer has products"),
    PRODUCT_NOT_FOUND("Product not be found!"),
    NOT_DELETE_PRODUCT("Can not delete a product having order details and ratings!");

    private final String description;

    private CustomMessages(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
