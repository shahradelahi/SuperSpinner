package com.litehex.superspinner.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.litehex.superspinner.interfaces.OnDataBinding;
import com.litehex.superspinner.interfaces.OnDismissListener;
import com.litehex.superspinner.interfaces.OnItemSelectedListener;
import com.litehex.superspinner.views.BottomSheetView;

public class SuperListingAdapter extends RecyclerView.Adapter<SuperListingAdapter.ViewHolder> {

    @LayoutRes
    int mLayoutId;
    int mItemsCount;
    OnDataBinding mOnDataBinding;
    OnItemSelectedListener mOnItemSelectedListener;
    OnDismissListener mOnDismissListener;
    BottomSheetView mBottomSheetView;
    boolean mDismiss = true;

    public SuperListingAdapter(@LayoutRes int layoutId, int itemsCount, BottomSheetView sheetView, OnDataBinding onDataBinding) {
        mLayoutId = layoutId;
        mItemsCount = itemsCount;
        mBottomSheetView = sheetView;
        mOnDataBinding = onDataBinding;
    }

    public SuperListingAdapter(@LayoutRes int layoutId, int itemsCount, OnDataBinding onDataBinding) {
        mLayoutId = layoutId;
        mItemsCount = itemsCount;
        mOnDataBinding = onDataBinding;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        mOnDataBinding.OnBidingView(holder.itemView, position);
        holder.itemView.setOnClickListener(view -> {
            int position1 = holder.getAdapterPosition();
            if (getOnItemSelectedListener() != null) mOnItemSelectedListener.onSelect(position1);
            if (mBottomSheetView != null) if (mDismiss) mBottomSheetView.dismiss();
        });
    }

    @Override
    public int getItemCount() {
        return mItemsCount;
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        mOnItemSelectedListener = listener;
    }

    public OnItemSelectedListener getOnItemSelectedListener() {
        return mOnItemSelectedListener;
    }

    public void setOnDismissListener(OnDismissListener listener) {
        mOnDismissListener = listener;
    }

    public OnDismissListener getOnDismissListener() {
        return mOnDismissListener;
    }

    public void setDismissOnItemSelected(boolean dismiss) {
        mDismiss = dismiss;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
