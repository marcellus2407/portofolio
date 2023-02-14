package com.telemedicine;

import android.app.ProgressDialog;
import android.content.Context;

public class Utils {
    public static ProgressDialog PROGRESS(Context ctx){
        ProgressDialog progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Loading. Please wait...");
        return  progressDialog;
    }
}
