package com.capstone.affinity_ad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class PagerAdapter extends RecyclerView.Adapter<PagerAdapter.ViewHolder> {
    private Context context;
    private int[] images;
    WebPageLinked webPageLinked;
    String[] webUrls;

    public PagerAdapter(Context context, int[] images, String[] webUrls) {
        this.context = context;
        this.images = images;
        this.webUrls = webUrls;
        webPageLinked = new WebPageLinked(this.context, webUrls);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.banners, parent, false);

        view.setOnClickListener(webPageLinked);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindImage(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.banner_img);
        }
        public void bindImage(int rsc) {
            img.setImageResource(rsc);
        }
    }


    class WebPageLinked implements View.OnClickListener {
        String[] webUrls;
        Intent webIntent;
        String url;

        Context context;
        public void updateUrl(int i) {
            url = webUrls[i];
        }
        WebPageLinked(Context context, String[] webUrls) {
            this.webUrls = webUrls;
            this.context = context;
        }
        @Override
        public void onClick(View view) {
            webIntent = new Intent(context,WebViewActivity.class);
            webIntent.putExtra("webUrl",url);
            context.startActivity(webIntent);
        }
    }
}