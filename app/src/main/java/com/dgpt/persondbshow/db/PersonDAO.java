package com.dgpt.persondbshow.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dgpt.persondbshow.bean.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonDAO {
    private  MyHelper helper;

    public PersonDAO(Context context) {
        helper = new MyHelper(context);
    }
    public void insert(Person person) {
        SQLiteDatabase db = helper.getWritableDatabase(); // 获取数据库对象
        //db.execSQL("insert into person(name,sex,age) values(" + "'" + person.getName() + "','"+person.getSex()+"',"+person.getAge() + ")");
        //db.execSQL("insert into person(name,sex,age) values(?,?,?)",new Object[]{person.getName(),person.getSex(),person.getAge()});
        // 用来装载要插入的数据的 Map<列名, 列的值>
        ContentValues values = new ContentValues();
        values.put("name", person.getName());
        values.put("sex", person.getSex());
        values.put("age", person.getAge());
        long id = db.insert("person", null, values); // 向account表插入数据values,
        person.setId(id);  // 得到id
        db.close();         // 关闭数据库
    }
    //根据id 删除数据
    public int delete(long id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        // 按条件删除指定表中的数据, 返回受影响的行数
        //db.execSQL("delete from person where id=?", new String[]{id+""});
        int count = db.delete("person", "id=?", new String[] { id + "" });
        db.close();
        return count;
    }
    //更新数据
    public int update(Person person) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues(); // 要修改的数据
        values.put("name", person.getName());
        values.put("sex", person.getSex());
        values.put("age", person.getAge());
        int count = db.update("person", values, "id=?",
                new String[] { person.getId() + "" }); // 更新并得到行数
        db.close();
        return count;
    }
    //查询所有数据倒序排列
    public List<Person> queryAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query("person", null, null, null, null, null,
                "id DESC");
        List<Person> list = new ArrayList<Person>();
        while (c.moveToNext()) {
            long id = c.getLong(c.getColumnIndex("id")); // 可以根据列名获取索引
            String name = c.getString(c.getColumnIndex("name"));
            String sex=c.getString(c.getColumnIndex("sex"));
            int age = c.getInt(c.getColumnIndex("age"));
            list.add(new Person(id, name, sex,age));
        }
        c.close();
        db.close();
        return list;
    }
}
