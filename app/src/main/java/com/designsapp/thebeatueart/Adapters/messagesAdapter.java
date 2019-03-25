package com.designsapp.thebeatueart.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.designsapp.thebeatueart.Model.message;
import com.designsapp.thebeatueart.R;
import com.designsapp.thebeatueart.Utils.contants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class messagesAdapter extends RecyclerView.Adapter<messagesAdapter.ViewHolder>  {


    private List<message> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private SharedPreferences mSharedPreferences;
    private String id;
    // data is passed into the constructor
    private boolean XX = false;
    public messagesAdapter(Context context, List<message> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        mSharedPreferences = context.getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        id = String.valueOf(mSharedPreferences.getInt(contants.id,0));
        XX = false;
    }// data is passed into the constructor   // data is passed into the constructor
    public messagesAdapter(Context context, List<message> data, boolean x) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        mSharedPreferences = context.getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        id = String.valueOf(mSharedPreferences.getInt(contants.id,0));
        XX = x;
    }// data is passed into the constructor
    public messagesAdapter(Context context, List<message> data, ItemClickListener mClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mClickListener =mClickListener;
        mSharedPreferences = context.getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        id = String.valueOf(mSharedPreferences.getInt(contants.id,0));
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.messageitem, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final message mSearchresult = mData.get(position);
        holder.TitleTextView.setText(mSearchresult.getTitle());
        holder.Time.setText("");
        holder.SubTitleTextView.setText("");
        Picasso.get().load(mSearchresult.getImageurl()).into(holder.profile);

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView TitleTextView,SubTitleTextView,Time;
        ImageView profile;

        CardView frameLayout;

        ViewHolder(View itemView) {
            super(itemView);
            TitleTextView = itemView.findViewById(R.id.TxtMessageperson);
            Time = itemView.findViewById(R.id.TxtMessagedate);
            SubTitleTextView = itemView.findViewById(R.id.TxtMessagelast);
            profile = itemView.findViewById(R.id.ImgMessageprofiel);
            frameLayout = itemView.findViewById(R.id.framexx);
             frameLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    message getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void updatelist(List<message> newList){
        mData = newList;
    }



    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
