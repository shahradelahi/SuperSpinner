package com.litehex.superspinner.utils;

import android.annotation.SuppressLint;
import android.content.Context;

public class Metrics {

    private final Context context;

    public Metrics(Context context) {
        this.context = context;
    }

    public int dpToPx(int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    public int pxToDp(int px) {
        return (int) (px / context.getResources().getDisplayMetrics().density);
    }

    public int dpToPx(double dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    public int pxToDp(double px) {
        return (int) (px / context.getResources().getDisplayMetrics().density);
    }

    @SuppressLint("DefaultLocale")
    public static String withSuffix(long count) {
        if (count < 1000) return "" + count;
        int exp = (int) (Math.log(count) / Math.log(1000));
        return String.format("%.1f %c", count / Math.pow(1000, exp), "KMBTPE".charAt(exp - 1));
    }

    @SuppressLint("DefaultLocale")
    public static String withSuffix(float count) {
        if (count < 1000) return "" + count;
        int exp = (int) (Math.log(count) / Math.log(1000));
        return String.format("%.1f %c", count / Math.pow(1000, exp), "KMBTPE".charAt(exp - 1));
    }

}
