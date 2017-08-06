package com.example.jayes.studentcomplaintportal;

import android.content.Context;
import android.support.v4.util.Pools;

/**
 * Created by jayes on 25/03/2017.
 */

public class SharedPreferenceManager {
    private static Context mctx;
    private static SharedPreferenceManager mInstance;
    private SharedPreferenceManager(Context context)
    {
        mctx=context;
    }
    public static com.example.jayes.studentcomplaintportal.SharedPreferenceManager getmInstance(Context context) {
        return mInstance;
    }
}
