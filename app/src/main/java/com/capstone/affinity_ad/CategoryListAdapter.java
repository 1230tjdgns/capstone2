package com.capstone.affinity_ad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder>{
    List<String> brands;
    GridViewAdapter adapter;
    ExpandableHeightGridView gv;

    List<String> l_brand;
    List<String> l_name;
    List<String> l_price;
    List<String> l_image;
    List<String>l_id;
    CategoryListAdapter(GridViewAdapter adapter, ExpandableHeightGridView gv, List<String> l_brand, List<String> l_name, List<String> l_price, List<String> l_image, List<String> l_id) {
        this.adapter = adapter;
        this.gv = gv;
        this.l_brand = l_brand;
        this.l_name = l_name;
        this.l_price = l_price;
        this.l_image = l_image;
        this.l_id = l_id;
    }
    public void addList(List<String> i) {
        brands = i;
    }
    @NonNull
    @Override
    public CategoryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_bts, parent, false);

        CategoryListAdapter.ViewHolder viewHolder = new CategoryListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getButton().setText(brands.get(position));
    }

    @Override
    public int getItemCount() {
        return brands.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Button bt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bt = itemView.findViewById(R.id.category_bt);
            bt.setOnClickListener(new CategorySelect());
        }
        public Button getButton() {
            return bt;
        }
    }
    class CategorySelect implements View.OnClickListener {
        Button bt;
        String brand;
        @Override
        public void onClick(View view) {
            adapter.clearItem();

            bt = (Button)view;
            for(String v : brands) {
                if(v == bt.getText()) {
                    brand = v;
                }
            }

            if(brand.matches("All")) {
                for(int i = 0 ; i < l_brand.size() ; i++) {
                    adapter.addItem(new gridItem(l_id.get(i),l_image.get(i),l_brand.get(i), l_name.get(i), l_price.get(i)));
                }
            }
            else {
                for(int i = 0 ; i < l_brand.size() ; i++) {
                    if(l_brand.get(i).matches(brand)) {
                        adapter.addItem(new gridItem(l_id.get(i),l_image.get(i),l_brand.get(i), l_name.get(i), l_price.get(i)));
                    }
                }
            }
            gv.setAdapter(adapter);
        }
    }
}
