package com.haball.Select_User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.haball.Distribution_Login.Distribution_Login;
import com.haball.LanguageClasses.ChangeLanguage;
import com.haball.R;
import com.haball.Retailer_Login.RetailerLogin;
import com.haball.Retailor.Retailer_TermsAndConditionsFragment;
import com.haball.Retailor.Retailer_UpdatePassword;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;

public class Register_Activity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    private String language;
    private TextView txt_retailer, txt_distributor, select_type, welcome_text, tv_description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);
        Drawable background_drawable = getResources().getDrawable(R.drawable.background_logo);
        background_drawable.setAlpha(80);
        LinearLayout ll_main_background = findViewById(R.id.ll_main_background);
        ll_main_background.setBackground(background_drawable);

//        testingFunction();
        //language change
        SharedPreferences languageType = getSharedPreferences("changeLanguage",
                Context.MODE_PRIVATE);
        language = languageType.getString("language", "");
        RelativeLayout rl_distributor = findViewById(R.id.rl_distributor);
        RelativeLayout rl_retailor = findViewById(R.id.rl_retailor);
        welcome_text = findViewById(R.id.welcome_text);
        txt_retailer = findViewById(R.id.txt_retailer);
        txt_distributor = findViewById(R.id.txt_distributor);
        select_type = findViewById(R.id.select_type);
        tv_description = findViewById(R.id.tv_description);
        rl_distributor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Register_Activity.this, Distribution_Login.class);
                startActivity(intent);
            }

        });
        rl_retailor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Register_Activity.this, RetailerLogin.class);
                startActivity(intent);
            }

        });

        changeLanguage();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 1500);

    }
    void changeLanguage() {
        ChangeLanguage changeLanguage = new ChangeLanguage();
        changeLanguage.changeLanguage(this, language);
        if (language.equals("ur")) {
            txt_retailer.setText(R.string.retailer);
            txt_distributor.setText(R.string.distributor);
            select_type.setText(R.string.select_account);
            welcome_text.setText(R.string.wellcome_haball);
            tv_description.setText(R.string.Txt_Description_haball);


        }
    }
}
