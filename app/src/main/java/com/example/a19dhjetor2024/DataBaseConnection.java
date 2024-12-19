package com.example.a19dhjetor2024;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import org.mindrot.jbcrypt.BCrypt;

public class DataBaseConnection extends SQLiteOpenHelper {

    public static final String DBNAME = "signup.db";

    public DataBaseConnection(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (" +
                "email TEXT PRIMARY KEY, " +
                "password TEXT, " +
                "firstName TEXT, " +
                "lastName TEXT, " +
                "phone TEXT)");
        db.execSQL("CREATE TABLE otp (otp TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS otp");
        onCreate(db);
    }

    public boolean insertUser(String firstName, String lastName, String phone, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        contentValues.put("firstName", firstName);
        contentValues.put("lastName", lastName);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("password", hashedPassword);

        long result = db.insert("users", null, contentValues);
        return result != -1;
    }


    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email=?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public boolean validateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT password FROM users WHERE email=?", new String[]{email});

        if (cursor.moveToFirst()) {
            String storedHashedPassword = cursor.getString(0);
            cursor.close();
            return BCrypt.checkpw(password, storedHashedPassword);
        }

        cursor.close();
        return false;
    }
    public boolean insert_otp(String otp){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("otp", otp);

        long result = db.insert("otp",null,contentValues);
        return result != -1;

    }
}