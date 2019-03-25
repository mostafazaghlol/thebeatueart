package com.designsapp.thebeatueart.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.designsapp.thebeatueart.Model.MSalonSupServies.SupService;
import com.designsapp.thebeatueart.R;
import com.designsapp.thebeatueart.Utils.contants;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class subkhadatAdapter extends RecyclerView.Adapter<subkhadatAdapter.ViewHolder>  {


    private List<SupService> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private SharedPreferences mSharedPreferences;
    private String id;
    // data is passed into the constructor
    private boolean XX = false;
    public subkhadatAdapter(Context context, List<SupService> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        mSharedPreferences = context.getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        id = String.valueOf(mSharedPreferences.getInt(contants.id,0));
        XX = false;
    }// data is passed into the constructor   // data is passed into the constructor
    public subkhadatAdapter(Context context, List<SupService> data, boolean x) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        mSharedPreferences = context.getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        id = String.valueOf(mSharedPreferences.getInt(contants.id,0));
        XX = x;
    }// data is passed into the constructor
    public subkhadatAdapter(Context context, List<SupService> data, ItemClickListener mClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mClickListener =mClickListener;
        mSharedPreferences = context.getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        id = String.valueOf(mSharedPreferences.getInt(contants.id,0));
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.subkhadamatitem, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final SupService mSearchresult = mData.get(position);
        holder.TitleTextView.setText(mSearchresult.getName());
        Picasso.get().load(mSearchresult.getPhoto()).placeholder(R.drawable.salon).into(holder.circleImageView);
        holder.PriceTextVeiw.setText(mSearchresult.getPrice()+"SR");
        holder.DesTextView.setText(mSearchresult.getDetails());
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
        TextView TitleTextView,DesTextView,PriceTextVeiw;
        RoundedImageView circleImageView;
        ImageView addImageView;

        ViewHolder(View itemView) {
            super(itemView);
            TitleTextView = itemView.findViewById(R.id.TxtSubkhadamatitemtitle);
            DesTextView = itemView.findViewById(R.id.TxtSubkhadamatitemdes);
            PriceTextVeiw = itemView.findViewById(R.id.TxtSubkhadamatitemprice);
            TitleTextView = itemView.findViewById(R.id.TxtSubkhadamatitemtitle);
            circleImageView = itemView.findViewById(R.id.ImgSubkhadamatitemimage);
            addImageView = itemView.findViewById(R.id.ImgSubkhadamatitemadd);
            addImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    SupService getItem(int id) {
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

    public void updatelist(List<SupService> newList){
        mData = newList;
    }



    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
