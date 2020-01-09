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

    private List<Task> taskList;
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

    public RequestAdapterRecyclerView(List<Task> taskList, Activity activity) {
        this.taskList = taskList;
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
        final Task task = taskList.get(position);

        holder.tv_title.setText(task.getTitle());
        holder.tv_desc.setText(task.getDesc());
        holder.tv_date.setText(task.getDate());

        holder.rl_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goDetail = new Intent(mActivity, MainActivity.class);
                goDetail.putExtra("id", task.getKey());
                goDetail.putExtra("title", task.getTitle());
                goDetail.putExtra("desc", task.getDesc());
                goDetail.putExtra("date", task.getDate());

                mActivity.startActivity(goDetail);


            }
        });

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }


}
