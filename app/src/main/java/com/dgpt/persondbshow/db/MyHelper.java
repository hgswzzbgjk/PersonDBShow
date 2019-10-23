package com.dgpt.persondbshow.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyHelper extends SQLiteOpenHelper {
    public MyHelper(Context context) {
        super(context, "personDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE person(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR(20)," + // 姓名列
                "sex CHAR(2),"+ // 性别列
                "age INTEGER)"); // 年龄列
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
