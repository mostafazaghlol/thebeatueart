package com.designsapp.thebeatueart.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.designsapp.thebeatueart.Model.Mcart.OrderItem;
import com.designsapp.thebeatueart.R;
import com.designsapp.thebeatueart.Utils.contants;

import java.util.List;

public class CartsAdapter extends RecyclerView.Adapter<CartsAdapter.ViewHolder>  {


    private List<OrderItem> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private SharedPreferences mSharedPreferences;
    private String id;
    // data is passed into the constructor
    private boolean XX = false;
    public CartsAdapter(Context context, List<OrderItem> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        mSharedPreferences = context.getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        id = String.valueOf(mSharedPreferences.getInt(contants.id,0));
        XX = false;
    }// data is passed into the constructor   // data is passed into the constructor
    public CartsAdapter(Context context, List<OrderItem> data, boolean x) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        mSharedPreferences = context.getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        id = String.valueOf(mSharedPreferences.getInt(contants.id,0));
        XX = x;
    }// data is passed into the constructor
    public CartsAdapter(Context context, List<OrderItem> data, ItemClickListener mClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mClickListener =mClickListener;
        mSharedPreferences = context.getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        id = String.valueOf(mSharedPreferences.getInt(contants.id,0));
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cart_items, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final OrderItem mSearchresult = mData.get(position);
        holder.TitleTextView.setText(mSearchresult.getName());
        if (mSearchresult.getPeopleNumber() == 0){

            holder.PriceText.setText("SR"+mSearchresult.getPrice());

        }else{

            Double price = Double.parseDouble(mSearchresult.getPrice()) * mSearchresult.getPeopleNumber();
            holder.PriceText.setText("SR"+price);

        }
        if(mSearchresult.getPeopleNumber() == 0){
            mSearchresult.setPeopleNumber(1);
            holder.number.setText(mSearchresult.getPeopleNumber().toString());
        }else{
            holder.number.setText(mSearchresult.getPeopleNumber().toString());
        }
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
        TextView TitleTextView,PriceText,delete,number;
        ImageView Imincrease,Imdecrease;

        ViewHolder(View itemView) {
            super(itemView);
            TitleTextView = itemView.findViewById(R.id.cartitemtitle);
            PriceText = itemView.findViewById(R.id.cartitemprice);
            delete = itemView.findViewById(R.id.cartitemdelete);
            number = itemView.findViewById(R.id.cartnumber);
            Imincrease = itemView.findViewById(R.id.cartincrease);
            Imdecrease = itemView.findViewById(R.id.cartdecrease);
            delete.setOnClickListener(this);
            Imincrease.setOnClickListener(this);
            Imdecrease.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    OrderItem getItem(int id) {
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

    public void updatelist(List<OrderItem> newList){
        mData = newList;
    }



    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
