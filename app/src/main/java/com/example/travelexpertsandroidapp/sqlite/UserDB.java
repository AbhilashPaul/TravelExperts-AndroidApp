package com.example.travelexpertsandroidapp.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.travelexpertsandroidapp.models.Customer;

import java.util.ArrayList;

public class UserDB {

        // database and database helper objects
        private SQLiteDatabase db;
        private UserDBHelper userDbHelper;

        // constructor
        public UserDB(Context context) {
            userDbHelper = new UserDBHelper(context, UserContract.DB_NAME, null, UserContract.DB_VERSION);
        }

        // private methods
        private void openReadableDB() {
            db = userDbHelper.getReadableDatabase();
        }
        private void openWriteableDB() {
            db = userDbHelper.getWritableDatabase();
        }
        private void closeDB() { if (db != null) db.close(); }

        // public methods

        //method to get list of agent records
        public ArrayList<Customer> getUser() {
            ArrayList<Customer> user = new ArrayList<Customer>();
            openReadableDB();
            Cursor cursor = db.query(UserContract.CUSTOMER_TABLE,
                    null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                Customer cust = new Customer();
                cust.setCustomerId(cursor.getInt(UserContract.CUSTOMER_ID_COL));
                cust.setCustFirstName(cursor.getString(UserContract.CUSTOMER_FIRSTNAME_COL));
                cust.setCustLastName(cursor.getString(UserContract.CUSTOMER_LASTNAME_COL));
                cust.setCustAddress(cursor.getString(UserContract.CUSTOMER_ADDRESS_COL));
                cust.setCustCity(cursor.getString(UserContract.CUSTOMER_CITY_COL));
                cust.setCustProv(cursor.getString(UserContract.CUSTOMER_PROVINCE_COL));
                cust.setCustPostal(cursor.getString(UserContract.CUSTOMER_POSTAL_COL));
                cust.setCustCountry(cursor.getString(UserContract.CUSTOMER_COUNTRY_COL));
                cust.setCustBusPhone(cursor.getString(UserContract.CUSTOMER_BUSPHONE_COL));
                cust.setCustHomePhone(cursor.getString(UserContract.CUSTOMER_HOMEPHONE_COL));
                cust.setCustEmail(cursor.getString(UserContract.CUSTOMER_EMAIL_COL));
                cust.setAgentId(cursor.getInt(UserContract.CUSTOMER_AGENTID_COL));
                cust.setCustUsername(cursor.getString(UserContract.CUSTOMER_USERNAME_COL));
                cust.setCustPassword(cursor.getString(UserContract.CUSTOMER_PASSWORD_COL));
                user.add(cust);
            }
            if (cursor != null)
                cursor.close();
            closeDB();

            return user;
        }

        //method to update agent record
        public int createUser(Customer user) {
            ContentValues cv = new ContentValues();
            cv.put(UserContract.CUSTOMER_ID, user.getCustomerId());
            cv.put(UserContract.CUSTOMER_FIRSTNAME, user.getCustFirstName());
            cv.put(UserContract.CUSTOMER_LASTNAME, user.getCustLastName());
            cv.put(UserContract.CUSTOMER_ADDRESS, user.getCustAddress());
            cv.put(UserContract.CUSTOMER_CITY, user.getCustCity());
            cv.put(UserContract.CUSTOMER_PROVINCE, user.getCustProv());
            cv.put(UserContract.CUSTOMER_POSTAL, user.getCustPostal());
            cv.put(UserContract.CUSTOMER_COUNTRY, user.getCustCountry());
            cv.put(UserContract.CUSTOMER_BUSPHONE, user.getCustBusPhone());
            cv.put(UserContract.CUSTOMER_HOMEPHONE, user.getCustHomePhone());
            cv.put(UserContract.CUSTOMER_EMAIL, user.getCustEmail());
            cv.put(UserContract.CUSTOMER_AGENTID, user.getAgentId());
            cv.put(UserContract.CUSTOMER_USERNAME, user.getCustUsername());
            cv.put(UserContract.CUSTOMER_PASSWORD, user.getCustPassword());
            this.openWriteableDB();
            int rowCount =(int)db.insert(UserContract.CUSTOMER_TABLE,null,cv);
            this.closeDB();
            return rowCount;
        }
}
