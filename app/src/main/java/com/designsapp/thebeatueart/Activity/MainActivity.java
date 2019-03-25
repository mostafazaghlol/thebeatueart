package com.designsapp.thebeatueart.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.designsapp.thebeatueart.Fragment.AccountFragment;
import com.designsapp.thebeatueart.Fragment.BookFragment;
import com.designsapp.thebeatueart.Fragment.CartFragment;
import com.designsapp.thebeatueart.Fragment.MainFragment;
import com.designsapp.thebeatueart.Fragment.MessagesFragment;
import com.designsapp.thebeatueart.Fragment.khadamatSalonSubFragment;
import com.designsapp.thebeatueart.Fragment.notificationsFragment;
import com.designsapp.thebeatueart.Model.MSalonServies.SaloneServices;
import com.designsapp.thebeatueart.R;
import com.designsapp.thebeatueart.Utils.contants;
import com.designsapp.thebeatueart.Utils.screenwithoutAction;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private SharedPreferences mSharedPreferences;
    private TextView mTextMessage;
    public static SaloneServices mSaloneServices;
    @BindView(R.id.user)
    ImageView imUser;
    @BindView(R.id.book)
    ImageView imBook;
    @BindView(R.id.chat)
    ImageView imChat;
    @BindView(R.id.house)
    ImageView imHouse;
    @BindView(R.id.fab)
    ImageView imFab;
    @BindView(R.id.imageViewprogile) ImageView imageView;
    @BindView(R.id.nameuser) TextView Txtname;
    @BindView(R.id.emailuser) TextView Txtemail;
    @BindView(R.id.arabicswitch) SwitchCompat arswitchCompat;
    @BindView(R.id.englishswitch) SwitchCompat enswitchCompat;
    public static DrawerLayout drawer;
    public static NavigationView navigationView;
    public SharedPreferences.Editor editor;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(this,MainActivity.class));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        screenwithoutAction.FullScreen(this);
        ButterKnife.bind(this);
        mSharedPreferences = getSharedPreferences(contants.pref_account,MODE_PRIVATE);
        editor = mSharedPreferences.edit();
        Picasso.get().load(mSharedPreferences.getString(contants.image,"0")).placeholder(R.drawable.salon).into(imageView);
        Txtname.setText(mSharedPreferences.getString(contants.username,""));
        Txtemail.setText(mSharedPreferences.getString(contants.email,""));
        mTextMessage = (TextView) findViewById(R.id.message);
        int x = getIntent().getIntExtra(contants.gox,0);
        String userx = getIntent().getStringExtra(contants.user_id);
        if(x != 0){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, khadamatSalonSubFragment.newInstance(String.valueOf(x),userx))
                    .commitNow();
        }else if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        arswitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    enswitchCompat.setChecked(false);
                }else{
                    enswitchCompat.setChecked(true);
                }
            }
        });
        enswitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    arswitchCompat.setChecked(false);
                }else{
                    arswitchCompat.setChecked(true);
                }
            }
        });







    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id1 = menuItem.getItemId();
        if(id1 == R.id.navigation_main){
            Toast.makeText(this, "الرئيسيه", Toast.LENGTH_SHORT).show();
        }else if(id1 == R.id.notifications){

        }else if(id1 == R.id.navigation_aboutapp){
            Toast.makeText(this, "عن التطبيق", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "الشروط والاحكام", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void openbottomnavi(View view) {
        int id = view .getId();
        if(id == R.id.user){
            reset(1);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AccountFragment.newInstance())
                    .commitNow();
        }else if(id == R.id.book){
            reset(2);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, BookFragment.newInstance())
                    .commitNow();
        }else if(id == R.id.fab){
            reset(3);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CartFragment.newInstance())
                    .commitNow();
        }else if(id == R.id.chat){
            reset(4);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MessagesFragment.newInstance())
                    .commitNow();
        }else if(id == R.id.house){
            reset(5);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }else if(id == R.id.shroot){
//            Toast.makeText(this, "shroot", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,ShrootActivity.class));

        }else if(id == R.id.aboutapp){
           // Toast.makeText(this, "about app", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,AboutApp.class));
        }else if(id == R.id.notifications){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, notificationsFragment.newInstance())
                    .commitNow();
            drawer.closeDrawers();
        }else if(id == R.id.main){
//            Toast.makeText(this, "main", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,MainActivity.class));
            finish();

        }else if(id == R.id.signout){
//            Toast.makeText(this, "main", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("تريد الخروج")
                    .setMessage("هل تريد الخروج ؟")
                    .setIcon(R.drawable.logo1)
                    .setPositiveButton("نعم", (dialog, which) -> {
                        // continue with delete

                        editor.clear();
                        editor.commit();
                        finish();
                    })
                    .setNegativeButton("لا", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .setIcon(R.drawable.logo1)
                    .show();
        }
    }

    private void reset(int i) {
        if(i == 1){
            Picasso.get().load(R.drawable.user1).into(imUser);
            Picasso.get().load(R.drawable.order).into(imBook);
            Picasso.get().load(R.drawable.shopping_basket_button).into(imFab);
            Picasso.get().load(R.drawable.chat2).into(imChat);
            Picasso.get().load(R.drawable.house).into(imHouse);
        }else if(i == 2){
            Picasso.get().load(R.drawable.user_1).into(imUser);
            Picasso.get().load(R.drawable.order_1).into(imBook);
            Picasso.get().load(R.drawable.shopping_basket_button).into(imFab);
            Picasso.get().load(R.drawable.chat2).into(imChat);
            Picasso.get().load(R.drawable.house).into(imHouse);
        }else if(i == 3){
            Picasso.get().load(R.drawable.user_1).into(imUser);
            Picasso.get().load(R.drawable.order).into(imBook);
            Picasso.get().load(R.drawable.shopping_basket_button).into(imFab);
            Picasso.get().load(R.drawable.chat2).into(imChat);
            Picasso.get().load(R.drawable.house).into(imHouse);
        }else if(i == 4){
            Picasso.get().load(R.drawable.user_1).into(imUser);
            Picasso.get().load(R.drawable.order).into(imBook);
            Picasso.get().load(R.drawable.shopping_basket_button).into(imFab);
            Picasso.get().load(R.drawable.chat_1).into(imChat);
            Picasso.get().load(R.drawable.house).into(imHouse);
        }else if(i == 5){
            Picasso.get().load(R.drawable.user_1).into(imUser);
            Picasso.get().load(R.drawable.order).into(imBook);
            Picasso.get().load(R.drawable.shopping_basket_button).into(imFab);
            Picasso.get().load(R.drawable.chat2).into(imChat);
            Picasso.get().load(R.drawable.house_1).into(imHouse);
        }
    }
}
