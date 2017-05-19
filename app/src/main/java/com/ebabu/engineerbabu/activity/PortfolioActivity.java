package com.ebabu.engineerbabu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ebabu.engineerbabu.R;
import com.ebabu.engineerbabu.adapter.PortfolioAdapter;
import com.ebabu.engineerbabu.beans.Portfolio;
import com.ebabu.engineerbabu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class PortfolioActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView rvPortfolio;
    private PortfolioAdapter porfolioAdapter;
    private List<Portfolio> listPortfolioes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);
        context = PortfolioActivity.this;
        initView();
        Utils.setUpToolbar(context, "My Portfolio");
    }

    private void initView() {
        listPortfolioes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listPortfolioes.add(new Portfolio());
        }

        rvPortfolio = (RecyclerView) findViewById(R.id.rv_portfolio);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        porfolioAdapter = new PortfolioAdapter(context, listPortfolioes);
        rvPortfolio.setLayoutManager(layoutManager);
        rvPortfolio.setAdapter(porfolioAdapter);

        findViewById(R.id.btn_add_portfolio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,AddPortfolioActivity.class);
                startActivity(intent);
            }
        });
    }

}
