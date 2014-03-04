package com.android.communityfinance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.communityfinance.domain.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shashank on 4/3/14.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "communityfinance";
    // Table Name
    private static final String MEMBERS_TABLE_NAME = "Members";
    // Column names
    private static final String COLUMN_MEMBERS_UID = "UID";
    private static final String COLUMN_MEMBERS_FIRSTNAME = "FirstName";
    private static final String COLUMN_MEMBERS_LASTNAME = "LastName";
    private static final String COLUMN_MEMBERS_CONTACT = "ContactInfo";

    // Contacts table name
    private static final String CREATE_MEMBER_TABLE = "Create table "
            + MEMBERS_TABLE_NAME + " ("+COLUMN_MEMBERS_UID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + COLUMN_MEMBERS_FIRSTNAME+" text,"+ COLUMN_MEMBERS_LASTNAME + " text,"
            + COLUMN_MEMBERS_CONTACT +" text);";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + MEMBERS_TABLE_NAME + ";");

        // Create tables again
        db.execSQL(CREATE_MEMBER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {}

    public ArrayList<Member> getAllMembers()
    {
        ArrayList<Member> membersList = new ArrayList<Member>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MEMBERS_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Member member = new Member();
                member.UID=(Integer.parseInt(cursor.getString(0)));
                member.FirstName=cursor.getString(1);
                member.LastName=cursor.getString(2);
                member.ContactInfo=cursor.getString(3);
                // Adding contact to list
                membersList.add(member);
            } while (cursor.moveToNext());
        }

        // return contact list
        return membersList;
    }

    public void addUpdateMember(Member member)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        if(member.UID == 0)
        {
            ContentValues values = new ContentValues();
            values.put(COLUMN_MEMBERS_FIRSTNAME, member.FirstName);
            values.put(COLUMN_MEMBERS_LASTNAME, member.LastName);
            values.put(COLUMN_MEMBERS_CONTACT, member.ContactInfo);

            db.insertOrThrow(MEMBERS_TABLE_NAME, null, values);
        }
        else
        {
            ContentValues values = new ContentValues();
            values.put(COLUMN_MEMBERS_FIRSTNAME, member.FirstName);
            values.put(COLUMN_MEMBERS_LASTNAME, member.LastName);
            values.put(COLUMN_MEMBERS_CONTACT, member.ContactInfo);

            db.update(MEMBERS_TABLE_NAME,values, COLUMN_MEMBERS_UID +" = "+member.UID,null);
        }

        db.close();
    }

    public void deleteAllMembers()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(MEMBERS_TABLE_NAME,null,null);

        db.close();
    }

}
