package com.haball.Language_Selection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.haball.R;
import com.haball.Select_User.Register_Activity;

public class Language_Selection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language__selection);


        Button rl = (Button) findViewById(R.id.rel_english);

        rl.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent intent = new Intent(Language_Selection.this, Register_Activity.class);
                startActivity(intent);
            }

        });
    }
}
