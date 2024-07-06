/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ruzzz.nemo.model;

import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class LoggedUserData {

    public LoggedUserData() {

    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String aUserId) {
        userId = aUserId;
    }

    public static String getFirstName() {
        return firstName;
    }

    public static void setFirstName(String aFirstName) {
        firstName = aFirstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static void setLastName(String aLastName) {
        lastName = aLastName;
    }

    public static String getMobile() {
        return mobile;
    }

    public static void setMobile(String aMobile) {
        mobile = aMobile;
    }

    public static String getGenderId() {
        return genderId;
    }

    public static void setGenderId(String aGenderId) {
        genderId = aGenderId;
    }

    public static String getUserEmail() {
        return userEmail;
    }

    public static void setUserEmail(String aUserEmail) {
        userEmail = aUserEmail;
    }

    public static String getUserPassword() {
        return userPassword;
    }

    public static void setUserPassword(String aUserPassword) {
        userPassword = aUserPassword;
    }

    private static String userId;
    private static String firstName;
    private static String lastName;
    private static String mobile;
    private static String genderId;
    private static String userEmail;
    private static String userPassword;
    private static String userRole;

    public static String getUserRole() {
        return userRole;
    }

    public static void setUserRole(String aUserRole) {
        userRole = aUserRole;
    }

}
