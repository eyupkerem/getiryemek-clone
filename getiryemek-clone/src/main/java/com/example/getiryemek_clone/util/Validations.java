package com.example.getiryemek_clone.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Validations {
    public static final String ERROR = "ERROR";
    public static final String SUCCESS = "SUCCESS";
    public static final String CUSTOMER_NOT_FOUND = "Customer does not exist";
    public static final String ADMIN_NOT_FOUND = "Admin does not exist";
    public static final String FOOD_NOT_FOUND = "Food does not exist";
    public static final String BASKET_NOT_FOUND = "Basket does not exist";
    public static final String ADDRESS_NOT_FOUND = "Address does not exist";
    public static final String CATEGORY_NOT_FOUND = "Category does not exist";
    public static final String RESTAURANT_NOT_FOUND = "Restaurant does not exist";
    public static final String RESTAURANTADMIN_NOT_FOUND = "Restaurant admin does not exist";
    public static final String DIFFERENT_RESTAURANT = " \n You can order only just one restaurant at the same time \n ";
    public static final String BASKET_EMPTY = "Your basket is empty . Please add food";
    public static final String ALREADY_IN_USE = "Email or phone number already in use";
    public static final String EMAIL_ALREADY_IN_USE = "Email already in use";
    public static final String CATEGORY_ALREADY_EXIST = "Category already exist";
    public static final String GMAIL_MANDATORY = "Email must be a valid Gmail address";
    public static final String AUTHENTICATION_FAILED = "Authentication failed. Please check your username and password";
    public static final String INVALID_CREDENTIALS = "Invalid credentials";
    public static final String TOKEN_GENERATED = "Token generated successfully";
    public static final String FIELDS_NOT_EMPTY="All fields must be non-empty";


}
