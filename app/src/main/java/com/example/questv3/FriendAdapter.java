package com.example.questv3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {

    Context mContext;
    private ArrayList<FriendItem> mData;
    private OnFriendListener mOnFriendListener;

    public FriendAdapter(Context mContext, ArrayList<FriendItem> mData, OnFriendListener onFriendListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.mOnFriendListener = onFriendListener;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.item_friend,parent,false);

        return new FriendViewHolder(layout, mOnFriendListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {

        // Apply animations here
        holder.container.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_scale_anim));

        // Bind data here
        holder.ftv_title.setText(mData.get(position).getfName());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class FriendViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView ftv_title;
        RelativeLayout container;
        OnFriendListener onFriendListener;

        public FriendViewHolder(@NonNull View itemView, OnFriendListener onFriendListener) {
            super(itemView);
            container = itemView.findViewById(R.id.friendContainerRL);
            ftv_title = itemView.findViewById(R.id.ftv_title);
            this.onFriendListener = onFriendListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onFriendListener.onFriendClick(getAdapterPosition());
        }
    }

    public interface OnFriendListener{
        void onFriendClick(int position);
    }
}
