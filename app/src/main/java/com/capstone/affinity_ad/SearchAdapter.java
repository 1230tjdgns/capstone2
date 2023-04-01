package com.capstone.affinity_ad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
/**
 * Created by Administrator on 2017-08-07.
 */

public class SearchAdapter extends BaseAdapter {

    private Context context;
    private List<String> lbrand;
    private List<String> lname;
    private List<String> lprice;
    private LayoutInflater inflate;
    private ViewHolder viewHolder;

    public SearchAdapter(List<String> lbrand,List<String> lname,List<String> lprice, Context context){
        this.lbrand = lbrand;
        this.lname = lname;
        this.lprice = lprice;
        this.context = context;
        this.inflate = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return lname.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if(convertView == null){
            convertView = inflate.inflate(R.layout.listview,null);

            viewHolder = new ViewHolder();
            viewHolder.item_brand = (TextView) convertView.findViewById(R.id.home_item_brand);
            viewHolder.item_name = (TextView) convertView.findViewById(R.id.home_item_name);
            viewHolder.item_price = (TextView) convertView.findViewById(R.id.home_item_price);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        // 리스트에 있는 데이터를 리스트뷰 셀에 뿌린다.
        viewHolder.item_brand.setText(lbrand.get(position));
        viewHolder.item_name.setText(lname.get(position));
        viewHolder.item_price.setText(lprice.get(position));
        return convertView;
    }

    class ViewHolder{
        public TextView item_brand;
        public TextView item_name;
        public TextView item_price;
    }

}
