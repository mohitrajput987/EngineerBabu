package com.ebabu.engineerbabu.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.ebabu.engineerbabu.R;
import com.ebabu.engineerbabu.beans.Skill;
import com.ebabu.engineerbabu.constant.IKeyConstants;
import com.ebabu.engineerbabu.customview.CustomTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 12/05/2017.
 */
public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.PlatformViewHolder> {

    private Context context;
    private List<Skill> listPlatforms, listFilteredPlatform;
    private boolean onBind;

    public SkillsAdapter(Context context, List<Skill> listPlatforms) {
        this.context = context;
        this.listPlatforms = listPlatforms;
        this.listFilteredPlatform = new ArrayList<>();
        this.listFilteredPlatform.addAll(listPlatforms);
    }

    @Override
    public PlatformViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_skill, parent, false);
        return new PlatformViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlatformViewHolder holder, final int position) {
        onBind = true;
        final Skill skill = listFilteredPlatform.get(position);
        holder.cbPlatform.setOnCheckedChangeListener(null);
        holder.cbPlatform.setChecked(skill.isChecked());
        holder.tvPlatformName.setText(skill.getName());

        holder.cbPlatform.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                skill.setIsChecked(isChecked);
                listPlatforms.get(position).setIsChecked(isChecked);
                notifyDataSetChanged();
            }
        });
        onBind = false;
    }

    @Override
    public int getItemCount() {
        return listFilteredPlatform.size();
    }

    class PlatformViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {

        AppCompatCheckBox cbPlatform;
        CustomTextView tvPlatformName;

        public PlatformViewHolder(View itemView) {
            super(itemView);
            cbPlatform = (AppCompatCheckBox) itemView.findViewById(R.id.cb_platform);
            cbPlatform.setOnCheckedChangeListener(this);
            tvPlatformName = (CustomTextView) itemView.findViewById(R.id.tv_platform_name);
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(!onBind) {
                // your process when checkBox changed
                // ...

                notifyDataSetChanged();
            }
        }
    }

    public void filter(String keyword) {
        if (keyword == null) {
            keyword = IKeyConstants.EMPTY;
        }
        keyword = keyword.trim();
        listFilteredPlatform.clear();
        if (keyword.isEmpty()) {
            listFilteredPlatform.addAll(listPlatforms);
        } else {
            for (Skill skill : listPlatforms) {
                String skillName = skill.getName().toLowerCase();
                if (skillName.contains(keyword)) {
                    listFilteredPlatform.add(skill);
                }
            }
        }
        notifyDataSetChanged();
    }
}
