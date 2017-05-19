package com.ebabu.engineerbabu.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebabu.engineerbabu.R;
import com.ebabu.engineerbabu.activity.ProjectDetailActivity;
import com.ebabu.engineerbabu.beans.Portfolio;

import java.util.List;

/**
 * Created by hp on 12/05/2017.
 */
public class PortfolioAdapter extends RecyclerView.Adapter<PortfolioAdapter.ProjectViewHolder> {

    private Context context;
    private List<Portfolio> listProjects;

    public PortfolioAdapter(Context context, List<Portfolio> listProjects) {
        this.context = context;
        this.listProjects = listProjects;
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_portfolio, parent, false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, final int position) {
        final Portfolio portfolio = listProjects.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProjectDetailActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProjects.size();
    }

    class ProjectViewHolder extends RecyclerView.ViewHolder {


        public ProjectViewHolder(View itemView) {
            super(itemView);
            //btnViewDetails = (CustomTextView) itemView.findViewById(R.id.btn_view_details);
        }

    }

}
