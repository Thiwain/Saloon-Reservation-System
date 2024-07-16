package com.ruzzz.nemo.model;

public class CustomerDataBean {

    private static String cMobile;
    private static String cFname;
    private static String cLname;
    private static String cEmail;

    public static String getcMobile() {
        return cMobile;
    }

    public static void setcMobile(String acMobile) {
        cMobile = acMobile;
    }

    public static String getcFname() {
        return cFname;
    }

    public static void setcFname(String acFname) {
        cFname = acFname;
    }

    public static String getcLname() {
        return cLname;
    }

    public static void setcLname(String acLname) {
        cLname = acLname;
    }

    public static String getcEmail() {
        return cEmail;
    }

    public static void setcEmail(String acEmail) {
        cEmail = acEmail;
    }

}
