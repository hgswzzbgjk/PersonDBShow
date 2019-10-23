package com.dgpt.persondbshow.adpter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dgpt.persondbshow.R;
import com.dgpt.persondbshow.bean.Person;
import com.dgpt.persondbshow.db.PersonDAO;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<Person> list;
    private PersonDAO dao;

    public MyAdapter(Context context, List<Person> list,PersonDAO dao) {
        this.context = context;
        this.list = list;
        this.dao = dao;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        // 重用convertView
        View item = convertView != null ? convertView : View.inflate(
                context, R.layout.item_view, null);
        // 获取该视图中的TextView
        TextView tvItemId;
        TextView tvItemName;
        TextView tvItemSex;
        TextView tvItemAge;
        ImageView ivItemUp;
        ImageView ivItemDown;
        ImageView ivItemDelete;

        tvItemId = (TextView) item.findViewById(R.id.tv_item_id);
        tvItemName = (TextView) item.findViewById(R.id.tv_item_name);
        tvItemSex = (TextView) item.findViewById(R.id.tv_item_sex);
        tvItemAge = (TextView) item.findViewById(R.id.tv_item_age);
        ivItemUp = (ImageView) item.findViewById(R.id.iv_item_up);
        ivItemDown = (ImageView) item.findViewById(R.id.iv_item_down);
        ivItemDelete = (ImageView) item.findViewById(R.id.iv_item_delete);

        // 根据当前位置获取Account对象
        final Person p = list.get(position);
        // 把Account对象中的数据放到TextView中
        tvItemId.setText(p.getId() + "");
        tvItemName.setText(p.getName());
        tvItemSex.setText(p.getSex());
        tvItemAge.setText(p.getAge()+"");

        //向上箭头的点击事件触发的方法
        ivItemUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                p.setAge(p.getAge() + 1); // 修改值
                notifyDataSetChanged(); // 刷新界面
                dao.update(p); // 更新数据库
            }
        });
        //向下箭头的点击事件触发的方法
        ivItemDown.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                p.setAge(p.getAge() - 1); // 修改值
                notifyDataSetChanged(); // 刷新界面
                dao.update(p); // 更新数据库
            }
        });
        //删除图片的点击事件触发的方法
        ivItemDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //删除数据之前首先弹出一个对话框
                android.content.DialogInterface.OnClickListener listener =
                        new android.content.DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                list.remove(p);          // 从集合中删除
                                dao.delete(p.getId()); // 从数据库中删除
                                notifyDataSetChanged();// 刷新界面
                            }
                        };
                AlertDialog.Builder builder = new AlertDialog.Builder(context); // 创建对话框
                builder.setTitle("确定要删除吗?");                    // 设置标题
                // 设置确定按钮的文本以及监听器
                builder.setPositiveButton("确定", listener);
                builder.setNegativeButton("取消", null);         // 设置取消按钮
                builder.show(); // 显示对话框
            }
        });
        return item;
    }
}
