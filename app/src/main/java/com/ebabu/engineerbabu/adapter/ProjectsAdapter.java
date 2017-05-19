package com.ebabu.engineerbabu.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebabu.engineerbabu.R;
import com.ebabu.engineerbabu.activity.ProjectDetailActivity;
import com.ebabu.engineerbabu.beans.Project;
import com.ebabu.engineerbabu.customview.CustomTextView;

import java.util.List;

/**
 * Created by hp on 12/05/2017.
 */
public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ProjectViewHolder> {

    private Context context;
    private List<Project> listProjects;

    public ProjectsAdapter(Context context, List<Project> listProjects) {
        this.context = context;
        this.listProjects = listProjects;
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_project, parent, false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, final int position) {
        final Project project = listProjects.get(position);

        holder.btnViewDetails.setOnClickListener(new View.OnClickListener() {
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

        CustomTextView btnViewDetails;

        public ProjectViewHolder(View itemView) {
            super(itemView);
            btnViewDetails = (CustomTextView) itemView.findViewById(R.id.btn_view_details);
        }

    }

}
