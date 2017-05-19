package com.ebabu.engineerbabu.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ebabu.engineerbabu.R;
import com.ebabu.engineerbabu.beans.Platform;
import com.ebabu.engineerbabu.customview.CustomTextView;

import java.util.List;

/**
 * Created by hp on 12/05/2017.
 */
public class PlatformAdapter extends RecyclerView.Adapter<PlatformAdapter.PlatformViewHolder> {

    private Context context;
    private List<Platform> listPlatforms;

    public PlatformAdapter(Context context, List<Platform> listPlatforms) {
        this.context = context;
        this.listPlatforms = listPlatforms;
    }

    @Override
    public PlatformViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_platorm, parent, false);
        return new PlatformViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlatformViewHolder holder, int position) {
        Platform platform = listPlatforms.get(position);

        holder.cbPlatform.setChecked(platform.isChecked());
        holder.ivPlatform.setImageResource(platform.getResId());
        holder.tvPlatformName.setText(platform.getName());
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
}
