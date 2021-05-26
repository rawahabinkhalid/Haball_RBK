package com.haball.Language_Selection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.haball.LanguageClasses.ChangeLanguage;
import com.haball.LanguageClasses.LanguageHelper;
import com.haball.R;
import com.haball.Select_User.Register_Activity;

import io.paperdb.Paper;

public class Language_Selection extends AppCompatActivity {
    Button rel_english, rel_urdu;
    String language = "";
    String selectedLang = "";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language__selection);
        rel_english = findViewById(R.id.rel_english);
        rel_urdu =  findViewById(R.id.rel_urdu);
        Paper.init(this);
        rel_english.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Paper.book().write("language", "en");
                updateView((String) Paper.book().read("language"));
                Intent intent = new Intent(Language_Selection.this, Register_Activity.class);
                startActivity(intent);
            }

        });
        rel_urdu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Paper.book().write("language", "ur");
                updateView((String) Paper.book().read("language"));

                Intent intent = new Intent(Language_Selection.this, Register_Activity.class);
                startActivity(intent);
            }

        });

        SharedPreferences languageType = getSharedPreferences("changeLanguage",
                Context.MODE_PRIVATE);
        selectedLang = languageType.getString("language", "");

        if (language == null) {

            Paper.book().write("language", "en");
            updateView((String) Paper.book().read("language"));
        }

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LanguageHelper.onAttach(newBase, "en"));
    }
    private void updateView(String lang) {
        context = LanguageHelper.setLocale(this, lang);
        Resources resources = context.getResources();
        SharedPreferences languageType = getSharedPreferences("changeLanguage",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = languageType.edit();
        editor.putString("language", lang);
        editor.apply();
        ChangeLanguage changeLanguage = new ChangeLanguage();
        changeLanguage.changeLanguage(this, lang);



    }
}
