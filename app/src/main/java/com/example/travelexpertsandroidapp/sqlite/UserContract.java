package com.example.travelexpertsandroidapp.sqlite;

public class UserContract {
    // database constants
    public static final String DB_NAME = "user.db";
    public static final int    DB_VERSION = 1;

    // CUSTOMER table constants
    public static final String CUSTOMER_TABLE = "customer";

    public static final String CUSTOMER_ID = "CustomerId";
    public static final int    CUSTOMER_ID_COL = 0;

    public static final String CUSTOMER_FIRSTNAME = "CustFirstName";
    public static final int    CUSTOMER_FIRSTNAME_COL = 1;

    public static final String CUSTOMER_LASTNAME = "CustLastName";
    public static final int    CUSTOMER_LASTNAME_COL = 2;

    public static final String CUSTOMER_ADDRESS = "CustAddress";
    public static final int    CUSTOMER_ADDRESS_COL = 3;

    public static final String CUSTOMER_CITY = "CustCity";
    public static final int    CUSTOMER_CITY_COL = 4;

    public static final String CUSTOMER_PROVINCE = "CustProv";
    public static final int    CUSTOMER_PROVINCE_COL = 5;

    public static final String CUSTOMER_POSTAL = "CustPostal";
    public static final int    CUSTOMER_POSTAL_COL = 6;

    public static final String CUSTOMER_COUNTRY = "CustCountry";
    public static final int    CUSTOMER_COUNTRY_COL = 7;

    public static final String CUSTOMER_HOMEPHONE = "CustHomePhone";
    public static final int    CUSTOMER_HOMEPHONE_COL = 8;

    public static final String CUSTOMER_BUSPHONE = "CustBusPhone";
    public static final int    CUSTOMER_BUSPHONE_COL = 9;

    public static final String CUSTOMER_EMAIL = "CustEmail";
    public static final int    CUSTOMER_EMAIL_COL = 10;

    public static final String CUSTOMER_AGENTID = "AgentId";
    public static final int    CUSTOMER_AGENTID_COL = 11;

    public static final String CUSTOMER_USERNAME = "CustUsername";
    public static final int    CUSTOMER_USERNAME_COL = 12;

    public static final String CUSTOMER_PASSWORD = "CustPassword";
    public static final int    CUSTOMER_PASSWORD_COL = 13;

    // CREATE and DROP TABLE statements
    public static final String CREATE_CUSTOMER_TABLE =
            "CREATE TABLE " + CUSTOMER_TABLE +
                    " (" +
                    CUSTOMER_ID   + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CUSTOMER_FIRSTNAME + " TEXT NOT NULL, " +
                    CUSTOMER_LASTNAME + " TEXT NOT NULL, " +
                    CUSTOMER_ADDRESS + " TEXT, " +
                    CUSTOMER_CITY + " TEXT, " +
                    CUSTOMER_PROVINCE + " TEXT, " +
                    CUSTOMER_POSTAL + " TEXT, " +
                    CUSTOMER_COUNTRY + " TEXT, " +
                    CUSTOMER_HOMEPHONE + " TEXT, " +
                    CUSTOMER_BUSPHONE + " TEXT, " +
                    CUSTOMER_EMAIL + " TEXT, " +
                    CUSTOMER_AGENTID + " TEXT, " +
                    CUSTOMER_USERNAME + " TEXT, " +
                    CUSTOMER_PASSWORD + " TEXT, " +
                    ")";


    public static final String DROP_CUSTOMER_TABLE =
            "DROP TABLE IF EXISTS " + CUSTOMER_TABLE;

}
