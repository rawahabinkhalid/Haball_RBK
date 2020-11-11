package com.haball;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class ProcessingError {
    public void showError(Context context) {
        try {
            final Dialog fbDialogue = new Dialog(context);
            //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
            fbDialogue.setContentView(R.layout.password_updatepopup);
            TextView tv_pr1;
            TextView txt_header1;

            tv_pr1 = fbDialogue.findViewById(R.id.txt_details);
            txt_header1 = fbDialogue.findViewById(R.id.txt_header1);
//                            tv_pr1.setText("User Profile ID " + ID + " password has been changed successfully.");
            txt_header1.setText("Processing Error");
            txt_header1.setTextColor(context.getResources().getColor(R.color.error_stroke_color));
            txt_header1.setBackground(ContextCompat.getDrawable(context, R.drawable.border_set_error));
            tv_pr1.setText("An error occurred while processing your request. Please try again later. If the problem persists, contact support.");
            fbDialogue.setCancelable(true);
            fbDialogue.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
            WindowManager.LayoutParams layoutParams = fbDialogue.getWindow().getAttributes();
            layoutParams.y = 200;
            layoutParams.x = -70;// top margin
            fbDialogue.getWindow().setAttributes(layoutParams);
            fbDialogue.show();

            ImageButton close_button = fbDialogue.findViewById(R.id.image_button);
            close_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fbDialogue.dismiss();
                }
            });

            fbDialogue.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
//                Intent intent = new Intent(Retailer_UpdatePassword.this, RetailerLogin.class);
//                startActivity(intent);
//                finish();
                }
            });
        } catch (WindowManager.BadTokenException ex) {
        }

    }
}
