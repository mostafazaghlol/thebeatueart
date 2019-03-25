package com.designsapp.thebeatueart.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.designsapp.thebeatueart.Model.chatModel;
import com.designsapp.thebeatueart.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class chatItemsAdapter extends RecyclerView.Adapter<chatItemsAdapter.ViewHolder>  {


    private List<chatModel> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public chatItemsAdapter(Context context, List<chatModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }// data is passed into the constructor
    public chatItemsAdapter(Context context, List<chatModel> data, ItemClickListener mClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mClickListener =mClickListener;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.chatitem, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
       try{
           final chatModel mSearchresult = mData.get(position);
           if(mSearchresult.getForHow() == 1){
               holder.TitleTextView1.setVisibility(View.VISIBLE);
               holder.TitleTextView1.setText(mSearchresult.getTitle());
               holder.TitleTextView2.setVisibility(View.INVISIBLE);
               holder.imageView.setVisibility(View.INVISIBLE);

           }else{
               holder.imageView.setVisibility(View.VISIBLE);
               holder.TitleTextView2.setVisibility(View.VISIBLE);
               holder.TitleTextView2.setText(mSearchresult.getTitle());
               holder.TitleTextView1.setVisibility(View.INVISIBLE);
               Picasso.get().load(mSearchresult.getImage()).into(holder.imageView);
           }
       }catch (Exception e){
           Log.e("onBindViewHolder",e.getMessage());
       }

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView TitleTextView1;
        TextView TitleTextView2;
        ImageView imageView;
        ViewHolder(View itemView) {
            super(itemView);
            TitleTextView1 = itemView.findViewById(R.id.txt1);
            TitleTextView2 = itemView.findViewById(R.id.txt2);
            imageView = itemView.findViewById(R.id.image1);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    chatModel getItem(int id) {
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
}
