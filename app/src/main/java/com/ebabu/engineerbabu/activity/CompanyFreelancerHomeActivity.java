package com.ebabu.engineerbabu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.androidquery.AQuery;
import com.ebabu.engineerbabu.R;
import com.ebabu.engineerbabu.adapter.ProjectsAdapter;
import com.ebabu.engineerbabu.beans.Project;
import com.ebabu.engineerbabu.constant.IKeyConstants;
import com.ebabu.engineerbabu.customview.CustomTextView;
import com.ebabu.engineerbabu.utils.AppPreference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CompanyFreelancerHomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    private RecyclerView rvProjects;
    private ProjectsAdapter platformAdapter;
    private List<Project> listProjects;
    private DrawerLayout drawerLayout;
    private CustomTextView tvName, tvCompanyName;
    private CircleImageView ivProfilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_freelancer_home);
        context = CompanyFreelancerHomeActivity.this;
        initView();
        setUpToolbar();
    }

    private void initView() {
        listProjects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listProjects.add(new Project());
        }
        tvName = (CustomTextView) findViewById(R.id.tv_name);
        tvCompanyName = (CustomTextView) findViewById(R.id.tv_company_name);
        ivProfilePic = (CircleImageView) findViewById(R.id.iv_profile_pic);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        rvProjects = (RecyclerView) findViewById(R.id.rv_projects);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        platformAdapter = new ProjectsAdapter(context, listProjects);
        rvProjects.setLayoutManager(layoutManager);
        rvProjects.setAdapter(platformAdapter);

        findViewById(R.id.btn_my_portfolio).setOnClickListener(this);
        findViewById(R.id.btn_my_bids).setOnClickListener(this);
        findViewById(R.id.btn_running_projects).setOnClickListener(this);
        findViewById(R.id.btn_subscription).setOnClickListener(this);
        findViewById(R.id.btn_connects).setOnClickListener(this);
        findViewById(R.id.btn_settings).setOnClickListener(this);
    }

    private Toolbar setUpToolbar() {
        final AppCompatActivity activity = (AppCompatActivity) context;
        Toolbar actionBarToolbar = (Toolbar) activity.findViewById(R.id.toolbar_actionbar);
        activity.setSupportActionBar(actionBarToolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        //actionBarToolbar.setBackgroundResource(backgroundResource);
        actionBarToolbar.setNavigationIcon(R.mipmap.menu);
        actionBarToolbar.setNavigationContentDescription(getString(R.string.app_name));
        actionBarToolbar.setTitle("");
        actionBarToolbar.setSubtitle("");
        CustomTextView tvTitle = (CustomTextView) actionBarToolbar.findViewById(R.id.toolbar_title);
        tvTitle.setText(IKeyConstants.EMPTY);
        actionBarToolbar.inflateMenu(R.menu.menu_company_freelancer_home);
        actionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer();
            }
        });

        actionBarToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.action_notifications:
                        intent = new Intent(context, NotificationsActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.action_discussion:

                        break;

                    case R.id.action_filter:

                        break;
                }
                return false;
            }
        });
        return actionBarToolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_company_freelancer_home, menu);
        return true;
    }

    private void openDrawer() {
        if (!drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.openDrawer(Gravity.LEFT);
        }
    }

    private void closeDrawer() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setProfileData();
    }

    @Override
    public void onClick(View view) {
        closeDrawer();
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_my_portfolio:
                intent = new Intent(context, PortfolioActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_running_projects:
                intent = new Intent(context, RunningProjectsActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_my_bids:
                intent = new Intent(context, AppliedBidsActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_subscription:
                intent = new Intent(context, SubscriptionActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_connects:
                intent = new Intent(context, ConnectsActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_settings:
                intent = new Intent(context, SettingsActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void setProfileData() {
        String profilePicUrl = AppPreference.getInstance(context).getProfileImage();
        String companyName = AppPreference.getInstance(context).getCompanyName();
        if (profilePicUrl != null && profilePicUrl.startsWith(IKeyConstants.HTTP)) {
            new AQuery(context).id(ivProfilePic).image(profilePicUrl);
        } else {
            ivProfilePic.setImageResource(R.mipmap.add_profile);
        }

        tvName.setText(AppPreference.getInstance(context).getFirstName() + IKeyConstants.SPACE + AppPreference.getInstance(context).getLastName());
        if (companyName != null && !companyName.isEmpty()) {
            tvCompanyName.setText(companyName);
        } else {
            tvCompanyName.setText(IKeyConstants.EMPTY);
        }
    }
}
