package com.litehex.superspinner.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.litehex.superspinner.R;
import com.litehex.superspinner.adapters.SuperListingAdapter;
import com.litehex.superspinner.interfaces.OnCreateView;
import com.litehex.superspinner.utils.DividerItemDecorator;
import com.litehex.superspinner.utils.Metrics;

public class BottomSheetView extends BottomSheetDialog {

    private @LayoutRes
    int mLayoutId = 0;
    private OnCreateView mOnCreateView;
    private SuperListingAdapter mAdapter;

    private LinearLayout lwtBody;
    private RecyclerView rwvItems;
    private TextView txtCancel;

    private int mDividerSize = new Metrics(getContext()).dpToPx(0.5);
    private int mDividerColor = Color.parseColor("#1F000000");

    private com.litehex.superspinner.interfaces.OnDismissListener mOnDismissListener;

    public BottomSheetView(@NonNull Context context) {
        super(context);
    }

    public BottomSheetView(@NonNull Context context, int theme) {
        super(context, theme);
    }

    @NonNull
    public static BottomSheetView getInstance(Context context, @LayoutRes int layoutId, SuperListingAdapter adapter, OnCreateView createView) {
        BottomSheetView sheetView = new BottomSheetView(context, R.style.SheetDialog);
        sheetView.mAdapter = adapter;
        sheetView.mLayoutId = layoutId;
        sheetView.mOnCreateView = createView;
        sheetView.onCreateView();
        return sheetView;
    }

    @NonNull
    public static BottomSheetView getInstance(Context context, SuperListingAdapter adapter) {
        BottomSheetView sheetView = new BottomSheetView(context, R.style.SheetDialog);
        sheetView.mAdapter = adapter;
        sheetView.onCreateView();
        return sheetView;
    }

    @NonNull
    public static BottomSheetView getInstance(Context context) {
        BottomSheetView sheetView = new BottomSheetView(context, R.style.SheetDialog);
        sheetView.onCreateView();
        return sheetView;
    }

    private void onCreateView() {
        if (mLayoutId != 0) {
            setContentView(mLayoutId);
            mOnCreateView.onCreate(this);
        }
        getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        setContentView(R.layout.layout_body_super_spinner_library);
        provideViews();
    }

    private void provideViews() {
        lwtBody = findViewById(R.id.lwt_body);
        rwvItems = findViewById(R.id.rwv_items);
        txtCancel = findViewById(R.id.txt_cancel);
        makeView();
        setCornerRadius(45);
    }

    private void makeView() {
        rwvItems.setAdapter(mAdapter);
        txtCancel.setOnClickListener(view -> dismiss());
    }

    public void setAdapter(SuperListingAdapter adapter) {
        mAdapter = adapter;
        rwvItems.setAdapter(mAdapter);
        rwvItems.addItemDecoration(new DividerItemDecorator(ContextCompat.getDrawable(getContext(), R.drawable.divider_super_spinner_library)));
        if (mOnDismissListener != null) mAdapter.setOnDismissListener(mOnDismissListener);
    }

    public void setOnDismissListener(com.litehex.superspinner.interfaces.OnDismissListener listener) {
        mOnDismissListener = listener;
        setOnDismissListener(dialogInterface -> mOnDismissListener.onDismiss());
        if (mAdapter != null) mAdapter.setOnDismissListener(mOnDismissListener);
    }

    public void setCancelTextSize(float size) {
        txtCancel.setTextSize(size);
    }

    public void setCancelTextColor(@ColorInt int color) {
        txtCancel.setTextColor(color);
    }

    public void setCancelTextGravity(int gravity) {
        txtCancel.setGravity(gravity);
    }

    public void setCornerRadius(int radius) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadii(new float[]{radius, radius, radius, radius, 0, 0, 0, 0});
        shape.setColor(Color.WHITE);
        rwvItems.setBackground(shape);
    }

    public void setDividerSize(int pxSize) {
        mDividerSize = pxSize;
        addItemDivider();
    }

    public void setDividerColor(@ColorInt int color) {
        txtCancel.setTextColor(color);
        addItemDivider();
    }

    public void removeItemDivider() {
        if (rwvItems.getItemDecorationCount() >= 1) {
            rwvItems.removeItemDecorationAt(0);
        }
    }

    public void addItemDivider() {
        removeItemDivider();
        GradientDrawable mShape = new GradientDrawable();
        mShape.setShape(GradientDrawable.RECTANGLE);
        mShape.setSize(1, mDividerSize);
        mShape.setColor(mDividerColor);
        rwvItems.addItemDecoration(new DividerItemDecorator(mShape));
    }

}
