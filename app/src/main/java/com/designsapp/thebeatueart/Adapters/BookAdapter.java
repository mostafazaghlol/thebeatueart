package com.designsapp.thebeatueart.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.designsapp.thebeatueart.Model.Book;
import com.designsapp.thebeatueart.R;
import com.designsapp.thebeatueart.Utils.contants;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder>  {


    private List<Book> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private SharedPreferences mSharedPreferences;
    private String id;
    // data is passed into the constructor
    private boolean XX = false;
    public BookAdapter(Context context, List<Book> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        mSharedPreferences = context.getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        id = String.valueOf(mSharedPreferences.getInt(contants.id,0));
        XX = false;
    }// data is passed into the constructor   // data is passed into the constructor
    public BookAdapter(Context context, List<Book> data, boolean x) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        mSharedPreferences = context.getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        id = String.valueOf(mSharedPreferences.getInt(contants.id,0));
        XX = x;
    }// data is passed into the constructor
    public BookAdapter(Context context, List<Book> data, ItemClickListener mClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mClickListener =mClickListener;
        mSharedPreferences = context.getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        id = String.valueOf(mSharedPreferences.getInt(contants.id,0));
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.bookitem, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Book mSearchresult = mData.get(position);
        holder.TitleTextView.setText(mSearchresult.getTitle());
        holder.PlaceText.setText(mSearchresult.getBranch());
        holder.DateTime.setText(mSearchresult.getDate());
        holder.openTime.setText(mSearchresult.getOpening());
        holder.Price.setText(mSearchresult.getPrice()+"SR");
        Picasso.get().load(R.drawable.salon).into(holder.circleImageView);
//       holder.frameLayout.setOnClickListener(this);
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
        TextView TitleTextView,PlaceText,openTime,DateTime,Price;
        CircleImageView circleImageView;
        CardView frameLayout;

        ViewHolder(View itemView) {
            super(itemView);
            TitleTextView = itemView.findViewById(R.id.TxtBookItemtitle);
            PlaceText = itemView.findViewById(R.id.TxtBookItemtBranch);
            circleImageView = itemView.findViewById(R.id.profile_image);
            openTime = itemView.findViewById(R.id.TxtBookItemOpeningTime);
            DateTime = itemView.findViewById(R.id.TxtBookItemDate);
            Price = itemView.findViewById(R.id.TxtBookItemPrice);
            frameLayout = itemView.findViewById(R.id.frame1);
            frameLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Book getItem(int id) {
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

    public void updatelist(List<Book> newList){
        mData = newList;
    }



    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
