package com.reggisam.mtl.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.reggisam.mtl.MainActivity;
import com.reggisam.mtl.R;
import com.reggisam.mtl.model.Task;

import java.util.List;

public class RequestAdapterRecyclerView extends RecyclerView.Adapter<RequestAdapterRecyclerView.MyViewHolder> {

    private List<Task> moviesList;
    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout rl_layout;
        public TextView tv_title, tv_desc, tv_date;

        public MyViewHolder(View view) {
            super(view);
            rl_layout = view.findViewById(R.id.rl_layout);
            tv_title = view.findViewById(R.id.tv_title);
            tv_desc = view.findViewById(R.id.tv_desc);
            tv_date = view.findViewById(R.id.tv_date);
        }
    }

    public RequestAdapterRecyclerView(List<Task> moviesList, Activity activity) {
        this.moviesList = moviesList;
        this.mActivity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_request, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Task movie = moviesList.get(position);

        holder.tv_title.setText(movie.getTitle());
        holder.tv_desc.setText(movie.getDesc());
        holder.tv_date.setText(movie.getDate());

        holder.rl_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goDetail = new Intent(mActivity, MainActivity.class);
                goDetail.putExtra("id", movie.getKey());
                goDetail.putExtra("title", movie.getTitle());
                goDetail.putExtra("desc", movie.getDesc());
                goDetail.putExtra("date", movie.getDate());

                mActivity.startActivity(goDetail);


            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }


}
