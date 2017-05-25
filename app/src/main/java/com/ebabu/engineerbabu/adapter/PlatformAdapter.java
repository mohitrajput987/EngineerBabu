package com.ebabu.engineerbabu.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.ebabu.engineerbabu.R;
import com.ebabu.engineerbabu.beans.Platform;
import com.ebabu.engineerbabu.constant.IKeyConstants;
import com.ebabu.engineerbabu.customview.CustomTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 12/05/2017.
 */
public class PlatformAdapter extends RecyclerView.Adapter<PlatformAdapter.PlatformViewHolder> {

    private Context context;
    private List<Platform> listPlatforms, selectedListPlatforms;

    public PlatformAdapter(Context context, List<Platform> listPlatforms) {
        this.context = context;
        this.listPlatforms = listPlatforms;
        selectedListPlatforms = new ArrayList<>();
    }

    @Override
    public PlatformViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_platorm, parent, false);
        return new PlatformViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlatformViewHolder holder, int position) {
        final Platform platform = listPlatforms.get(position);
        holder.cbPlatform.setOnCheckedChangeListener(null);
        holder.cbPlatform.setChecked(platform.isChecked());
        if (platform.getCategory_image() != null && platform.getCategory_image().startsWith(IKeyConstants.HTTP)) {
            new AQuery(context).id(holder.ivPlatform).image(platform.getCategory_image());
        } else {
            holder.ivPlatform.setImageResource(R.mipmap.smm);
        }
        holder.tvPlatformName.setText(platform.getCategory_name());
        holder.cbPlatform.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    selectedListPlatforms.add(platform);
                } else {
                    selectedListPlatforms.remove(platform);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPlatforms.size();
    }

    static class PlatformViewHolder extends RecyclerView.ViewHolder {

        AppCompatCheckBox cbPlatform;
        ImageView ivPlatform;
        CustomTextView tvPlatformName;

        public PlatformViewHolder(View itemView) {
            super(itemView);
            cbPlatform = (AppCompatCheckBox) itemView.findViewById(R.id.cb_platform);
            ivPlatform = (ImageView) itemView.findViewById(R.id.iv_platform);
            tvPlatformName = (CustomTextView) itemView.findViewById(R.id.tv_platform_name);
        }
    }

    public String getSelectedPlatformsInCsv() {
        if (selectedListPlatforms.size() == 0) {
            return IKeyConstants.EMPTY;
        } else {
            String csvString = selectedListPlatforms.get(0).getCategory_id();
            for (Platform platform : selectedListPlatforms) {
                csvString = csvString + IKeyConstants.COMMA + platform.getCategory_id();
            }
            return csvString;
        }
    }

}
