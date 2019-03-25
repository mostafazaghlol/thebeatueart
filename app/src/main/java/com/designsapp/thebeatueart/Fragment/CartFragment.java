package com.designsapp.thebeatueart.Fragment;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.designsapp.thebeatueart.Activity.MainActivity;
import com.designsapp.thebeatueart.Activity.SearchActivity;
import com.designsapp.thebeatueart.Adapters.CartsAdapter;
import com.designsapp.thebeatueart.Model.Mcart.Cart;
import com.designsapp.thebeatueart.Model.Mcart.OrderItem;
import com.designsapp.thebeatueart.R;
import com.designsapp.thebeatueart.Utils.contants;
import com.designsapp.thebeatueart.Volley.cart;
import com.designsapp.thebeatueart.Volley.makeorder;
import com.designsapp.thebeatueart.Volley.profile;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {
    private int i = 1;
    private int j = 1;
    private String date;
    private String timex;
    private String phone;
    @BindView(R.id.empyr)
    RelativeLayout layout;
    private double recentprice;
    private double initprice;

    @OnClick(R.id.cartcart)
    public void cart() {
        MainActivity.drawer.openDrawer(Gravity.RIGHT);
    }

    @OnClick(R.id.search1)
    public void search() {
        startActivity(new Intent(getContext(), SearchActivity.class));
    }

    @BindView(R.id.scr)
    ScrollView src;
    @BindView(R.id.calendar)
    CalendarView mCalendar;
    @BindView(R.id.TxtNumberOfPeople)
    TextView TxtNubmerOfPeople;
    @BindView(R.id.increase)
    ImageView ImIncrease;
    @BindView(R.id.decrease)
    ImageView ImDecrease;
    @BindView(R.id.number)
    TextView TxtNumber;

    @BindView(R.id.openorders)
    ImageView ImOpenorders;
    @BindView(R.id.TxtNameOfSalon)
    TextView TxtNameOfSalon;
    @BindView(R.id.TxtDateOfSalon)
    TextView TxtDateOfSalon;
    @BindView(R.id.day)
    TextView TxtDay;
    @BindView(R.id.month)
    TextView TxtMonth;
    @BindView(R.id.up)
    ImageView ImUp;
    @BindView(R.id.down)
    ImageView ImDown;
    @BindView(R.id.TxtPrice)
    TextView TxtPrice;
    @BindView(R.id.TxtPriceofKhadma)
    TextView TxtPriceKhadma;
    @BindView(R.id.TxtPricefull)
    TextView TxtPriceFull;
    @BindView(R.id.Btconfirm)
    Button Btconfirm;
    @BindView(R.id.SpCities)
    Button SpCities;
    @BindView(R.id.time)
    TextView time;
    Integer numberofpeople = 0;
    @BindView(R.id.ReOrders)
    RecyclerView ReOrders;
    CartsAdapter mCartsAdapter;
    List<OrderItem> orders;

    public CartFragment() {
        // Required empty public constructor
    }

    SharedPreferences mSharedPreferences;
    ProgressDialog progressDialog;
    String id;

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);
        orders = new ArrayList<>();
        mSharedPreferences = getActivity().getSharedPreferences(contants.pref_account, Context.MODE_PRIVATE);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(getString(R.string.app_name));
        progressDialog.setIcon(R.drawable.logo1);
        progressDialog.setMessage(getString(R.string.loading));
        id = String.valueOf(mSharedPreferences.getInt(contants.id, 0));
        phone = mSharedPreferences.getString(contants.phone, "0");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImUp.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance(); // Get today
            cal.add(Calendar.MONTH, i); // get previous month by substracting one to the current one
            i++;
            long previousMonth = cal.getTimeInMillis(); // get the milliseconds of previous month
            mCalendar.setDate(previousMonth); // set in the CalendarView that you want to see the previous month
        });
        ImDown.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance(); // Get today
            cal.add(Calendar.MONTH, -1 * j); // get previous month by substracting one to the current one
            j++;
            long previousMonth = cal.getTimeInMillis(); // get the milliseconds of previous month
            mCalendar.setDate(previousMonth); // set in the CalendarView that you want to see the previous month

        });
        SpCities.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    SpCities.setText(String.valueOf(selectedHour) + ":" + String.valueOf(selectedMinute));
                    TxtDateOfSalon.setText(String.valueOf(selectedHour) + ":" + String.valueOf(selectedMinute));
                    timex = String.valueOf(selectedHour) + ":" + String.valueOf(selectedMinute);
                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });
        ImIncrease.setOnClickListener(v -> {
            numberofpeople++;
            TxtNumber.setText(numberofpeople.toString());
            TxtNubmerOfPeople.setText(numberofpeople.toString() + "فرد");
            setorders(numberofpeople);
        });
        ImDecrease.setOnClickListener(v -> {
            if (numberofpeople > 1) {
                numberofpeople--;
                TxtNumber.setText(numberofpeople.toString());
                TxtNubmerOfPeople.setText(numberofpeople.toString() + "فرد");
                setorders(numberofpeople);

            }
        });
        mCartsAdapter = new CartsAdapter(getContext(), orders, (view1, position) -> {
            int id = view1.getId();
            int numberofpeople = orders.get(position).getPeopleNumber();
            if (id == R.id.cartincrease) {
                numberofpeople++;
            }
            if (id == R.id.cartdecrease) {
                if (numberofpeople > 1) {
                    numberofpeople--;
                }
            }
            orders.get(position).setPeopleNumber(numberofpeople);
//            recentprice = numberofpeople*Double.parseDouble(orders.get(position).getPrice());
            mCartsAdapter.notifyDataSetChanged();
            calculateprice(orders);


            if (id == R.id.cartitemdelete) {
                orders.remove(position);
                mCartsAdapter.notifyDataSetChanged();
            }
            //Toast.makeText(getContext(), ""+String.valueOf(id), Toast.LENGTH_SHORT).show();
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        ReOrders.setLayoutManager(linearLayoutManager);
        ReOrders.setAdapter(mCartsAdapter);
        mCalendar.setOnDateChangeListener((view12, year, month, dayOfMonth) -> {
            TxtDay.setText(String.valueOf(dayOfMonth));
            TxtMonth.setText(getmonth(month) + " " + String.valueOf(year));
            //21-03-2019
            date = String.valueOf(dayOfMonth) + "-" + String.valueOf(month) + "-" + String.valueOf(year);
        });

        Btconfirm.setOnClickListener(v -> {
            progressDialog.show();

            int size = orders.size();
            for (int p = 0; p < orders.size(); p++) {
                final int x = p;
                OrderItem orderItem = orders.get(p);
                makeorder mMakeorder = new makeorder((Response.Listener<String>) response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (x == size - 1) {
                            progressDialog.dismiss();
                            startActivity(new Intent(getContext(), MainActivity.class));
                            getActivity().finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, id, mCartk.getData().getId().toString(), orderItem.getSubServiceId().toString(), date, timex, phone, orderItem.getPeopleNumber().toString(), orderItem.getPrice());
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(mMakeorder);
            }

        });
        addfackdata();
    }

    private void setorders(Integer numberofpeople) {
        for (OrderItem orderItem : orders) {
            orderItem.setPeopleNumber(numberofpeople);
        }
        mCartsAdapter.notifyDataSetChanged();
        calculateprice(orders);
    }

    private String getmonth(int month) {
        List<String> months = new ArrayList<>();
        months.add("jan");
        months.add("feb");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
        return months.get(month);
    }

    Cart mCartk;

    private void addfackdata() {
        progressDialog.show();
        cart mCart = new cart(response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                JSONObject data = jsonObject.getJSONObject("data");
                if (data != null) {
                    if (status.equals("1")) {
                        mCartk = new Gson().fromJson(response, Cart.class);
                        getSalonName(mCartk.getData().getSaloneId());
                        progressDialog.dismiss();
                        orders.addAll(mCartk.getData().getOrderItems());
                        mCartsAdapter.notifyDataSetChanged();
                        calculateprice(mCartk);
                    } else {
                        progressDialog.dismiss();
                    }
                } else {
                    progressDialog.dismiss();
                    src.setVisibility(View.GONE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                progressDialog.dismiss();
                src.setVisibility(View.GONE);
                layout.setVisibility(View.VISIBLE);
            }
        }, id);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(mCart);

    }

    private void calculateprice(Cart mCartk) {
        initprice = 0.0;
        for (int i = 0; i < mCartk.getData().getOrderItems().size(); i++) {
            double p = Double.parseDouble(mCartk.getData().getOrderItems().get(i).getPrice());
            initprice += p;
            TxtPrice.setText(String.valueOf(initprice));
            TxtPriceKhadma.setText("0");
            TxtPriceFull.setText(String.valueOf(initprice));
        }
    }

    private void calculateprice(List<OrderItem> mCartk) {
        initprice = 0.0;
        for (int i = 0; i < mCartk.size(); i++) {
            double p = Double.parseDouble(mCartk.get(i).getPrice()) * mCartk.get(i).getPeopleNumber();
            initprice += p;
            TxtPrice.setText(String.valueOf(initprice));
            TxtPriceKhadma.setText("0");
            TxtPriceFull.setText(String.valueOf(initprice));
        }
    }

    private void getSalonName(Integer saloneId) {

        profile mProfile = new profile(response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String status = jsonObject.getString("status");
                if (status.equals("1")) {
                    JSONObject user = jsonObject.getJSONObject("user");
                    String name = user.getString("name");
                    TxtNameOfSalon.setText(name);
                    time.setText(user.getString("time_work"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, saloneId.toString());

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(mProfile);

    }


}
