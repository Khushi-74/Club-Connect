package com.example.clubconnect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
 Context context;
 ArrayList<user> usersArrayList;

    public MyAdapter(Context context, ArrayList<user> usersArrayList) {
        this.context = context;
        this.usersArrayList = usersArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.clubdetails,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
    user u = usersArrayList.get(position);
    holder.clubName.setText(u.clubName);
        holder.leader.setText(u.leader);
        holder.description.setText(u.description);

    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView clubName,leader,description;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            clubName=itemView.findViewById(R.id.tvClubName);
            leader=itemView.findViewById(R.id.tvLeader);
            description=itemView.findViewById(R.id.tvDescription);
        }
    }
}
