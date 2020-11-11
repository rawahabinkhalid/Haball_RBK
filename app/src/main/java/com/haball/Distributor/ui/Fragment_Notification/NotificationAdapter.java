package com.haball.Distributor.ui.Fragment_Notification;

import android.content.Context;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.haball.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private Context context;
    private List<NotificationModel> notificationLists;
    private String token;

    public NotificationAdapter(Context context, List<NotificationModel> notificationLists, String token) {
        this.context = context;
        this.notificationLists = notificationLists;
        this.token = token;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_inflate = LayoutInflater.from(context).inflate(R.layout.notifications_recycler,parent,false);
        return new NotificationAdapter.ViewHolder(view_inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.notification_message.setText(notificationLists.get(position).getAlertMessage());
        holder.subject.setText(notificationLists.get(position).getSubject());
        holder.menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                // final PopupMenu popup = new PopupMenu(context, view);
                Context wrapper = new ContextThemeWrapper(context, R.style.AppBaseTheme);
                final PopupMenu popup = new PopupMenu(wrapper, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.notification_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.dismiss:
                                Log.i("DISMISS CASE", "HERE");

                                Dismiss_Notification dismiss_notification = new Dismiss_Notification();
                                dismiss_notification.requestDismissNotification(notificationLists.get(position).getID(),context, token);
                                notificationLists.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, notificationLists.size());
//                                Toast.makeText(context, "Notification Dismissed", Toast.LENGTH_LONG).show();
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView notification_message, subject;
        public ImageButton menu_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notification_message = itemView.findViewById(R.id.notification_message);
            subject = itemView.findViewById(R.id.subject);
            menu_btn = itemView.findViewById(R.id.menu_btn);
        }
    }
}