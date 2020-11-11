package com.haball;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.haball.Retailor.RetailorDashboard;
import com.haball.Select_User.Register_Activity;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Iterator;

public class HaballError {
    public void printErrorMessage(Context context, VolleyError error) {
        error.printStackTrace();
        if (context != null) {
//            printAdvancedErrorInfo(context, error);
//            if (error instanceof ClientError) {
//                Log.i("Logout", "Logout Activity");
////                SharedPreferences login_token = context.getSharedPreferences("LoginToken",
////                        Context.MODE_PRIVATE);
////                SharedPreferences.Editor editor = login_token.edit();
////                editor.remove("Login_Token");
////                editor.remove("User_Type");
////                editor.remove("Retailer_Id");
////                editor.remove("username");
////                editor.remove("CompanyName");
////                editor.remove("UserId");
////                editor.commit();
//
////                Intent intent = new Intent((FragmentActivity) context, Register_Activity.class);
////                ((FragmentActivity) context).startActivity(intent);
////                ((FragmentActivity) context).finish();
////                new CustomToast().showToast((FragmentActivity) context, "Session has been expired");
//            } else {
            if (error != null && error.networkResponse != null)
                if (error.networkResponse.statusCode == 498) {
//                    String message = "";
//                    String responseBody = "";
//                    try {
//                        responseBody = new String(error.networkResponse.data, "utf-8");
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        JSONObject data = new JSONObject(responseBody);
//                        if (data.getString("name").equals("TokenExpiredError")) {
                    Log.i("Logout", "Logout Activity");
                    SharedPreferences login_token = context.getSharedPreferences("LoginToken",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = login_token.edit();
                    editor.remove("Login_Token");
                    editor.remove("User_Type");
                    editor.remove("Retailer_Id");
                    editor.remove("username");
                    editor.remove("CompanyName");
                    editor.remove("UserId");
                    editor.commit();

                    Intent intent = new Intent((FragmentActivity) context, Register_Activity.class);
                    ((FragmentActivity) context).startActivity(intent);
                    ((FragmentActivity) context).finish();
                    new CustomToast().showToast((FragmentActivity) context, "Session has been expired");
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                } else {
                    Toast.makeText(context, String.valueOf(error), Toast.LENGTH_LONG).show();
                }
//            }
        }
//         if (context != null) {
//             if (error instanceof NetworkError) {
//                 Toast.makeText(context, "Network Error !", Toast.LENGTH_LONG).show();
//             } else if (error instanceof ServerError) {
//                 Toast.makeText(context, "Server Error !", Toast.LENGTH_LONG).show();
//             } else if (error instanceof AuthFailureError) {
//                 Toast.makeText(context, "Auth Failure Error !", Toast.LENGTH_LONG).show();
//             } else if (error instanceof ParseError) {
//                 Toast.makeText(context, "Parse Error !", Toast.LENGTH_LONG).show();
//             } else if (error instanceof NoConnectionError) {
//                 Toast.makeText(context, "No Connection Error !", Toast.LENGTH_LONG).show();
//             } else if (error instanceof TimeoutError) {
//                 Toast.makeText(context, "Timeout Error !", Toast.LENGTH_LONG).show();
//             }
//
//             if (error.networkResponse != null && error.networkResponse.data != null) {
//                 try {
//                     String message = "";
//                     String responseBody = new String(error.networkResponse.data, "utf-8");
//                     JSONObject data = new JSONObject(responseBody);
//                     Iterator<String> keys = data.keys();
//                     while (keys.hasNext()) {
//                         String key = keys.next();
//                         //                if (data.get(key) instanceof JSONObject) {
//                         message = message + data.get(key) + "\n";
//                         //                }
//                     }
//                     if (message.equals(""))
//                         message = responseBody;
//                     //                    if(data.has("message"))
//                     //                        message = data.getString("message");
//                     //                    else if(data. has("Error"))
//                     Toast.makeText(context, message, Toast.LENGTH_LONG).show();
//                 } catch (UnsupportedEncodingException e) {
//                     e.printStackTrace();
//                 } catch (JSONException e) {
//                     e.printStackTrace();
//                 }
//             }
//             //        NetworkResponse response = error.networkResponse;
//             //        if (error instanceof ServerError && response != null) {
//             //            try {
//             //                String message = "";
//             //
//             //                String res = new String(response.data,
//             //                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
//             //                // Now you can use any deserializer to make sense of data
//             //                JSONObject obj = new JSONObject(res);
//             //                Log.i("obj", String.valueOf(obj));
//             //                Iterator<String> keys = obj.keys();
//             //                int i = 0;
//             //                while(keys.hasNext()) {
//             //                    String key = keys.next();
//             ////                    if (obj.get(key) instanceof JSONObject) {
//             //                        message = message + obj.get(key) + "\n";
//             ////                    }
//             //                    i++;
//             //                }
//             //                Log.i("message", message);
//             //                Toast.makeText(context, String.valueOf(message), Toast.LENGTH_LONG).show();
//             //            } catch (UnsupportedEncodingException e1) {
//             //                // Couldn't properly decode data to string
//             //                e1.printStackTrace();
//             //            } catch (JSONException e2) {
//             //                // returned data is not JSONObject?
//             //                e2.printStackTrace();
//             //            }
//             //        }
//         }
    }

    private void printAdvancedErrorInfo(Context mContext, VolleyError error) {
        if (error instanceof NoConnectionError) {
            ConnectivityManager cm = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = null;
            if (cm != null) {
                activeNetwork = cm.getActiveNetworkInfo();
            }
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                Toast.makeText(mContext, "Server is not connected to internet.",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "Your device is not connected to internet.",
                        Toast.LENGTH_SHORT).show();
            }
        } else if (error instanceof NetworkError || error.getCause() instanceof ConnectException
                || (error.getCause().getMessage() != null
                && error.getCause().getMessage().contains("connection"))) {
            Toast.makeText(mContext, "Your device is not connected to internet.",
                    Toast.LENGTH_SHORT).show();
        } else if (error.getCause() instanceof MalformedURLException) {
            Toast.makeText(mContext, "Bad Request.", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ParseError || error.getCause() instanceof IllegalStateException
                || error.getCause() instanceof JSONException
                || error.getCause() instanceof XmlPullParserException) {
            Toast.makeText(mContext, "Parse Error (because of invalid json or xml).",
                    Toast.LENGTH_SHORT).show();
        } else if (error.getCause() instanceof OutOfMemoryError) {
            Toast.makeText(mContext, "Out Of Memory Error.", Toast.LENGTH_SHORT).show();
        } else if (error instanceof AuthFailureError) {
            Toast.makeText(mContext, "server couldn't find the authenticated request.",
                    Toast.LENGTH_SHORT).show();
        } else if (error instanceof ServerError || error.getCause() instanceof ServerError) {
            Toast.makeText(mContext, "Server is not responding.", Toast.LENGTH_SHORT).show();
        } else if (error instanceof TimeoutError || error.getCause() instanceof SocketTimeoutException
                || error.getCause() instanceof ConnectTimeoutException
                || error.getCause() instanceof SocketException
                || (error.getCause().getMessage() != null
                && error.getCause().getMessage().contains("Connection timed out"))) {
            Toast.makeText(mContext, "Connection timeout error",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "An unknown error occurred.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
