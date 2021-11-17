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
    NOT_DELETE_PRODUCT("Can not delete a product having order details and ratings and view histories!"),
    NOT_ENOUGH_QUALITY_TO_SELL("The quantity of product id %s is not enough to be sold!"),
    NOT_AVAILABLE_TO_SELL("The product id %s is not available to be sold!"),
    ORDER_FAILED("Order products is not complete!"),
    ORDER_SUCCESS("Order successfully!!!"),
    PAYMENT_NOT_FOUND("Payment not found!"),
    DATA_INVALID_FORMAT("Data format of %s is invalid!"),
    USER_NOT_FOUND("User not found!"),
    INCORRECT_CURRENT_PASSWORD("Current password is not correct!"),
    NEW_PASSWORD_MUST_DIFFERENT_CURRENT_PASSWORD("New password must be different with current password!"),
    NOT_DELETE_USER("Not allow to delete user having orders or ratings and view histories"),
    CHANGE_PASSWORD_SUCCESS("Change password successfully!"),
    CHANGE_PASSWORD_FAILED("Change password failed!"),
    HISTORY_VIEWING_NOT_EXISTED("History viewing is not found!");

    private final String description;

    private CustomMessages(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
