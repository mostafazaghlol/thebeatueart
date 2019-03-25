package com.designsapp.thebeatueart.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.designsapp.thebeatueart.Model.MSalon.Datum;
import com.designsapp.thebeatueart.R;
import com.designsapp.thebeatueart.Utils.contants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomesAdapter extends RecyclerView.Adapter<HomesAdapter.ViewHolder>  {


    private List<Datum> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private SharedPreferences mSharedPreferences;
    private String id;
    // data is passed into the constructor
    private boolean XX = false;
    public HomesAdapter(Context context, List<Datum> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        mSharedPreferences = context.getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        id = String.valueOf(mSharedPreferences.getInt(contants.id,0));
        XX = false;
    }// data is passed into the constructor   // data is passed into the constructor
    public HomesAdapter(Context context, List<Datum> data, boolean x) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        mSharedPreferences = context.getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        id = String.valueOf(mSharedPreferences.getInt(contants.id,0));
        XX = x;
    }// data is passed into the constructor
    public HomesAdapter(Context context, List<Datum> data, ItemClickListener mClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mClickListener =mClickListener;
        mSharedPreferences = context.getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        id = String.valueOf(mSharedPreferences.getInt(contants.id,0));
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.salonitem, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Datum mSearchresult = mData.get(position);
        holder.TitleTextView.setText(mSearchresult.getName());
        holder.PlaceText.setText(mSearchresult.getAddress());
        holder.ratingBar.setRating(mSearchresult.getRate());
        holder.ratingBar.setIsIndicator(true);
        Picasso.get().load(mSearchresult.getImage()).placeholder(R.drawable.salon).into(holder.imageView);
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
        TextView TitleTextView,PlaceText;
        RatingBar ratingBar;
        FrameLayout frameLayout;
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            TitleTextView = itemView.findViewById(R.id.Txtsalonitemtitle);
            PlaceText = itemView.findViewById(R.id.Txtsalonitemlocation);
            ratingBar = itemView.findViewById(R.id.rating);
            frameLayout = itemView.findViewById(R.id.frame);
            imageView = itemView.findViewById(R.id.salonimage);
            frameLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Datum getItem(int id) {
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

    public void updatelist(List<Datum> newList){
        mData = newList;
    }



    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
