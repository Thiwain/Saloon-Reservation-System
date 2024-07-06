/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.ruzzz.nemo.validation;

import raven.toast.Notifications;

/**
 *
 * @author Acer
 */
public class ValidationProcess {

    public static boolean validateUsername(String username) {

        try {

            if (username.isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Username is required!");
                return false;
            } else if (username.length() < 4) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Username can't be less than four characters!");
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean names(String username) {

        try {

            if (username.isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Name is required!");
                return false;
            } else if (username.length() < 4) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Name can't be less than four characters!");
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean validatePassword(String password) {
        if (password.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Password is required!");
            return false;
        } else if (!password.matches(Validation.PASSWORD_VALIDATION.validate())) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Weak Password");
            return false;
        } else {
            return true;
        }
    }

    public static boolean validateEmail(String email) {
        if (email.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Email is required!");
            return false;
        } else if (!email.matches(Validation.EMAIL_VALIDATION.validate())) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Email is Invalid!");
            return false;
        } else {
            return true;
        }
    }

    public static boolean validateMobile(String mobile) {
        if (mobile.isEmpty()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Mobile number is required!");
            return false;
        } else if (!mobile.matches(Validation.MOBLIE_VALIDATION.validate())) {
            Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Mobile number is invalid!");
            return false;
        } else {
            return true;
        }
    }

}
