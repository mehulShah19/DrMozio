package com.example.mehul.drmozio.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * Created by Mehul on 03/09/16.
 */
public class Utility {

    /**
     * Display a Toast messge for short time
     * @param context
     * @param message
     */
    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     *     /**
     * Display a Toast messge for long time
     * @param context
     * @param message
     */
    public static void showLongToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Dismisses Virtual or Soft Keyboard
     * @param context
     */

    public static void dismisskeyboard(Activity context){
        View view = context.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
