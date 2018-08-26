package com.example.yumi.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class HomeListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private int layoutID;
    private String[] namelist;
    private String[] birthdaylist;

    static class ViewHolder {
        TextView text;
        TextView birthday;
    }

    HomeListAdapter(Context context, int itemLayoutId,
                String[] names, String[] birthdays ){

        inflater = LayoutInflater.from(context);
        layoutID = itemLayoutId;

        namelist = names;
        birthdaylist = birthdays;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(layoutID, null);
            holder = new ViewHolder();
          //  holder.img = convertView.findViewById(R.id.img_item);
            holder.text = convertView.findViewById(R.id.tvHomeName);
            holder.birthday = convertView.findViewById(R.id.tvHomeBirthday);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //リストビューへの表示
        String str = "Staff ID.170900"+ String.valueOf(position+1)
                + "\n\nBirthday:" + birthdaylist[position]
                + "\nTel: 020-8931-9933 #340" + String.valueOf(position+1);
        holder.birthday.setText(str);

        holder.text.setText(namelist[position]);

        return convertView;
    }

    @Override
    public int getCount() {

        return namelist.length;
    }

    @Override
    public Object getItem(int position) {

        return position;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }
}