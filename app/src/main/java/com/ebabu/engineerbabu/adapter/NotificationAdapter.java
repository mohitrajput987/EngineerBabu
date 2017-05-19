package com.ebabu.engineerbabu.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.ebabu.engineerbabu.R;
import com.ebabu.engineerbabu.beans.Notification;
import com.ebabu.engineerbabu.constant.IKeyConstants;
import com.ebabu.engineerbabu.customview.CustomTextView;
import com.ebabu.engineerbabu.utils.Utils;

import java.util.List;

/**
 * Created by hp on 10/01/2017.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private Context context;
    private List<Notification> listNotifications;
    private Resources resources;

    public NotificationAdapter(Context context, List<Notification> listNotifications) {
        this.context = context;
        this.listNotifications = listNotifications;
        resources = context.getResources();
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        Notification notification = listNotifications.get(position);

        if (notification != null) {
            if (notification.getImage() != null && notification.getImage().startsWith(IKeyConstants.HTTP)) {
                new AQuery(context).id(holder.ivImage).image(notification.getImage());
            } else {
                holder.ivImage.setImageResource(R.mipmap.ic_launcher);
            }

            if (notification.getTitle() != null) {
                holder.tvTitle.setText(notification.getTitle());
            } else {
                holder.tvTitle.setText(IKeyConstants.EMPTY);
            }

            if (notification.getMessage() != null) {
                holder.tvMessage.setText(notification.getMessage().trim());
            } else {
                holder.tvMessage.setText(IKeyConstants.EMPTY);
            }

            if (notification.getCreate_at() != null && !notification.getCreate_at().isEmpty()) {
                Long longTimestamp = Long.parseLong(notification.getCreate_at());
                holder.tvTimestamp.setText(Utils.completeDisplayDateFormat.format(longTimestamp));
            } else {
                holder.tvTimestamp.setText(IKeyConstants.NA);
            }
        }
    }

    @Override
    public int getItemCount() {
        return listNotifications.size();
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;
        CustomTextView tvTitle, tvMessage, tvTimestamp;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.iv_image);
            tvTitle = (CustomTextView) itemView.findViewById(R.id.tv_title);
            tvMessage = (CustomTextView) itemView.findViewById(R.id.tv_message);
            tvTimestamp = (CustomTextView) itemView.findViewById(R.id.tv_date);
        }
    }


}
