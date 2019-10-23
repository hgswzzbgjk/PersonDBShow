package com.dgpt.persondbshow.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.dgpt.persondbshow.R;
import com.dgpt.persondbshow.adpter.MyAdapter;
import com.dgpt.persondbshow.bean.Person;
import com.dgpt.persondbshow.db.PersonDAO;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText etMainName;
    private Spinner spMainSex;
    private EditText etMainAge;
    private ImageView ivMainAdd;
    private ListView lvMainPerson;
    private PersonDAO dao;
    private MyAdapter adapter;
    private List<Person> personList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        ivMainAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etMainName.getText().toString().trim();
                String sex = spMainSex.getSelectedItem().toString();
                int age=Integer.parseInt(etMainAge.getText().toString());

                Person p=new Person(name, sex, age);

                dao.insert(p);                      // 插入数据库
                personList.add(p);                        // 插入集合
                adapter.notifyDataSetChanged(); // 刷新界面
                // 选中最后一个
                lvMainPerson.setSelection(lvMainPerson.getCount() - 1);
                etMainName.setText("");
                etMainAge.setText("");
            }
        });
    }

    private void initData() {
        dao=new PersonDAO(MainActivity.this);
        personList=dao.queryAll();
        adapter=new MyAdapter(MainActivity.this, personList, dao);
        lvMainPerson.setAdapter(adapter);
    }

    private void initView() {
        etMainName = (EditText) findViewById(R.id.et_main_name);
        spMainSex = (Spinner) findViewById(R.id.sp_main_sex);
        etMainAge = (EditText) findViewById(R.id.et_main_age);
        ivMainAdd = (ImageView) findViewById(R.id.iv_main_add);
        lvMainPerson = (ListView) findViewById(R.id.lv_main_person);
    }
}
