package com.example.clubconnect;

import com.bumptech.glide.Glide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder2> {
    private final Context context;
    private final ArrayList<eventdata> imageList;

    public EventAdapter(Context context, ArrayList<eventdata> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public EventAdapter.MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new MyViewHolder2(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.MyViewHolder2 holder, int position) {
        eventdata imageData = imageList.get(position);
        holder.itemName.setText(imageData.getItemName());
        holder.itemDesc.setText(imageData.getItemDesc());


        // Load the first image URL using a library like Glide or Picasso
        if (imageData.getImageUrls() != null && !imageData.getImageUrls().isEmpty()) {
            String imageUrl = imageData.getImageUrls().get(0);
            Glide.with(context)
                    .load(imageUrl)
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class MyViewHolder2 extends RecyclerView.ViewHolder {
        TextView itemName, itemDesc;
        ImageView imageView;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.textView1);
            itemDesc = itemView.findViewById(R.id.textView2);

            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
