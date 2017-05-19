package com.ebabu.engineerbabu.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ebabu.engineerbabu.R;
import com.ebabu.engineerbabu.adapter.ProjectsAdapter;
import com.ebabu.engineerbabu.beans.Project;
import com.ebabu.engineerbabu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class AppliedBidsActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView rvProjects;
    private ProjectsAdapter platformAdapter;
    private List<Project> listProjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_bids);
        context = AppliedBidsActivity.this;
        initView();
        Utils.setUpToolbar(context, "Applied Bids");
    }

    private void initView() {
        listProjects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listProjects.add(new Project());
        }

        rvProjects = (RecyclerView) findViewById(R.id.rv_bids);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        platformAdapter = new ProjectsAdapter(context, listProjects);
        rvProjects.setLayoutManager(layoutManager);
        rvProjects.setAdapter(platformAdapter);

    }
}
