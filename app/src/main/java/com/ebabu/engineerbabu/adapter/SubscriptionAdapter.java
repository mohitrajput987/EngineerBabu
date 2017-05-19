package com.ebabu.engineerbabu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ebabu.engineerbabu.R;
import com.ebabu.engineerbabu.beans.Subscription;
import com.ebabu.engineerbabu.customview.CustomTextView;

import java.util.List;

/**
 * Created by hp on 10/01/2017.
 */
public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.NotificationViewHolder> {

    private Context context;
    private List<Subscription> listSubscriptions;

    public SubscriptionAdapter(Context context, List<Subscription> listSubscriptions) {
        this.context = context;
        this.listSubscriptions = listSubscriptions;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_subscription, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        Subscription notification = listSubscriptions.get(position);

        if (notification != null) {
//            if (notification.getImage() != null && notification.getImage().startsWith(IKeyConstants.HTTP)) {
//                new AQuery(context).id(holder.ivImage).image(notification.getImage());
//            } else {
//                holder.ivImage.setImageResource(R.mipmap.ic_launcher);
//            }
//
//            if (notification.getTitle() != null) {
//                holder.tvTitle.setText(notification.getTitle());
//            } else {
//                holder.tvTitle.setText(IKeyConstants.EMPTY);
//            }
//
//            if (notification.getMessage() != null) {
//                holder.tvMessage.setText(notification.getMessage().trim());
//            } else {
//                holder.tvMessage.setText(IKeyConstants.EMPTY);
//            }
//
//            if (notification.getCreate_at() != null && !notification.getCreate_at().isEmpty()) {
//                Long longTimestamp = Long.parseLong(notification.getCreate_at());
//                holder.tvTimestamp.setText(Utils.completeDisplayDateFormat.format(longTimestamp));
//            } else {
//                holder.tvTimestamp.setText(IKeyConstants.NA);
//            }
        }
    }

    @Override
    public int getItemCount() {
        return listSubscriptions.size();
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
