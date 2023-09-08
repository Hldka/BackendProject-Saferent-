package com.saferent.exception.message;

public class ErrorMessage {

    public final static String RESOURCE_NOT_FOUND_EXCEPTION = "Resource with id %s not found";
    public final static String ROLE_NOT_FOUND_EXCEPTION = "Role : %s not found";
    public final static String USER_NOT_FOUND_EXCEPTION = "User with email: %s not found";
    public final static String JWTTOKEN_ERROR_MESSAGE = "JWT Token Validation Error: %s";
    public final static String EMAIL_ALREADY_EXIST_MESSAGE = "Email: %s already exist";
    public final static String PRINCIPAL_FOUND_MESSAGE = "User not found";
    public final static String NOT_PERMITTED_METHOD_MESSAGE = "You don't have any permission to change this data";
    public final static String PASSWORD_NOT_MATCHED_MESSAGE = "Your passwords are not matched";
    public final static String IMAGE_NOT_FOUND_MESSAGE = "ImageFile with id %s not found";
    public final static String IMAGE_USED_MESSAGE = "ImageFile is used by another car";
    public final static String RESERVATION_TIME_INCORRECT_MESSAGE = "Reservation pick up time or drop of time not correct";
    public final static String CAR_NOT_AVAILABLE_MESSAGE = "Car is not available for selected time ";
    public final static String CAR_USED_BY_RESERVATION_MESSAGE = "Car couldn't be deleted. Car is used by a reservation ";
    public final static String USER_CANT_BE_DELETED_MESSAGE = "User couldn't be deleted. User is used by a reservation ";
    public final static String RESERVATION_STATUS_CANT_CHANGE_MESSAGE = "Reservation can't be updated for canceled or done reservations";
    public final static String EXCEL_REPORT_ERROR_MESSAGE = "Error occured while generating excel report";
}
