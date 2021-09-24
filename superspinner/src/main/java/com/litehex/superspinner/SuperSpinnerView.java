/*
 * Designed and developed by 2021 Litehex (Shahrad Elahi)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.litehex.superspinner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.litehex.superspinner.adapters.SuperListingAdapter;
import com.litehex.superspinner.interfaces.OnDataBinding;
import com.litehex.superspinner.interfaces.OnDismissListener;
import com.litehex.superspinner.interfaces.OnItemSelectedListener;
import com.litehex.superspinner.interfaces.OnShowingListener;
import com.litehex.superspinner.utils.Metrics;
import com.litehex.superspinner.views.BottomSheetView;

public class SuperSpinnerView extends ConstraintLayout {

    public SuperSpinnerView(@NonNull Context context) {
        super(context);
        Initialization(null);
    }

    public SuperSpinnerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Initialization(attrs);
    }

    public SuperSpinnerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Initialization(attrs);
    }

    SuperListingAdapter mAdapter;
    OnItemSelectedListener mOnItemSelectedListener;
    OnShowingListener mOnShowingListener;
    OnDismissListener mOnDismissListener;

    boolean hasDefaultView = false;
    BottomSheetView mSheetView;
    ConstraintLayout lytContainer;
    TextView txtValue;
    ImageView imvArrow;

    boolean ssvDefaultPreview = false;
    View mPreviewView;

    boolean hasSelectedPosition = false;
    int mSelectedPosition = -0;

    int ssvArray = -0;

    boolean ssvItemDividerShow = true;
    int ssvItemDividerHeight = new Metrics(getContext()).dpToPx(0.5);
    int ssvItemDividerColor;

    Drawable ssvArrow;
    boolean ssvArrowShow = true;
    int ssvArrowGravity = 1;
    int ssvArrowTint;
    boolean ssvArrowAnimation = true;
    int ssvArrowAnimationDuration = 500;

    private void Initialization(AttributeSet attrs) {

        getAttributes(attrs);

        if (ssvDefaultPreview) {
            mPreviewView = inflate(getContext(), R.layout.layout_super_spinner_library, this);
        }

        post(() -> {

            if (ssvDefaultPreview) {
                removeViewAt(0);
            }

            if (getChildCount() == 0) {
                View view = inflate(getContext(), R.layout.layout_super_spinner_library, this);
                lytContainer = view.findViewById(R.id.F4IbiLak3d);
                txtValue = view.findViewById(R.id.AEmhgZ2PvD);
                imvArrow = view.findViewById(R.id.XO9cfHToOf);
                hasDefaultView = true;
                hasDefaultView();
            }

        });

        setOnClickListener(view -> {
            if (mSheetView != null) {
                if (hasDefaultView) {
                    if (ssvArrowAnimation) {
                        makeAnimatedRotation(imvArrow, ssvArrowAnimationDuration, 0.0f, -180.0f);
                    }
                }
                if (mOnShowingListener != null) mOnShowingListener.onShow();
                showSheet();
            }
        });

        if (ssvArray != -0) setup(getResources().getStringArray(ssvArray));

    }

    private void hasDefaultView() {

        if (ssvArrow != null) imvArrow.setImageDrawable(ssvArrow);

        if (!ssvArrowShow) imvArrow.setVisibility(GONE);

        imvArrow.setColorFilter(ssvArrowTint, android.graphics.PorterDuff.Mode.SRC_IN);

        if (ssvArrowGravity == 1) lytContainer.setLayoutDirection(LAYOUT_DIRECTION_LTR);
        else lytContainer.setLayoutDirection(LAYOUT_DIRECTION_RTL);

    }

    private void getAttributes(AttributeSet attrs) {
        if (attrs != null) {

            TypedArray StylesAttr = getContext().obtainStyledAttributes(attrs, R.styleable.SuperSpinnerView);

            ssvDefaultPreview = StylesAttr.getBoolean(R.styleable.SuperSpinnerView_ssvDefaultPreview, false);

            ssvArray = StylesAttr.getResourceId(R.styleable.SuperSpinnerView_ssvArray, -0);

            ssvArrow = StylesAttr.getDrawable(R.styleable.SuperSpinnerView_ssvArrow);
            ssvArrowShow = StylesAttr.getBoolean(R.styleable.SuperSpinnerView_ssvArrowShow, true);
            ssvArrowTint = StylesAttr.getColor(R.styleable.SuperSpinnerView_ssvArrowTint, Color.WHITE);
            ssvArrowGravity = StylesAttr.getInt(R.styleable.SuperSpinnerView_ssvArrowGravity, 1);
            ssvArrowAnimation = StylesAttr.getBoolean(R.styleable.SuperSpinnerView_ssvArrowAnimation, true);
            ssvArrowAnimationDuration = StylesAttr.getInt(R.styleable.SuperSpinnerView_ssvArrowAnimationDuration, 500);

            ssvItemDividerShow = StylesAttr.getBoolean(R.styleable.SuperSpinnerView_ssvItemDividerShow, true);
            ssvItemDividerHeight = StylesAttr.getDimensionPixelSize(R.styleable.SuperSpinnerView_ssvItemDividerHeight, new Metrics(getContext()).dpToPx(0.5));
            ssvItemDividerColor = StylesAttr.getColor(R.styleable.SuperSpinnerView_ssvItemDividerColor, Color.parseColor("#1F000000"));

            StylesAttr.recycle();

        }
    }

    public void makeSheet(@NonNull SuperListingAdapter adapter) {
        mSheetView = BottomSheetView.getInstance(getContext(), adapter);
    }

    public void makeSheet() {
        mSheetView = BottomSheetView.getInstance(getContext());
    }

    public BottomSheetView getSheet() {
        return mSheetView;
    }

    public void showSheet() {
        if (mSheetView != null) {
            mSheetView.show();
        }
    }

    public void setup(@LayoutRes int layout, int length, OnDataBinding binding) {

        mSheetView = BottomSheetView.getInstance(getContext());
        mSheetView.setOnDismissListener(mOnDismissListener);

        mAdapter = new SuperListingAdapter(layout, length, getSheet(), binding);
        mAdapter.setOnItemSelectedListener(mOnItemSelectedListener);

        getSheet().setAdapter(mAdapter);

    }

    public void setup(@NonNull String[] items) {

        setup(R.layout.layout_preference_super_spinner_library, items.length, (holder, position) -> {
            TextView txtTitle = holder.findViewById(R.id.preference_title);
            txtTitle.setText(items[position]);
            View swv = holder.findViewById(R.id.item_divider);
            if (ssvItemDividerShow) {

                // setting styles
                swv.setBackgroundColor(ssvItemDividerColor);
                swv.getLayoutParams().height = ssvItemDividerHeight;

                // Hiding last divider
                if (position == items.length - 1) {
                    swv.setVisibility(View.GONE);
                }

            } else {
                swv.setVisibility(View.GONE);
            }
        });

        setOnItemSelectedListener(position -> {
            if (hasDefaultView) {
                txtValue.setText(items[position]);
            }
        });

        setOnDismissListener(() -> {
            if (hasDefaultView) {
                if (ssvArrowAnimation) {
                    makeAnimatedRotation(imvArrow, ssvArrowAnimationDuration, -180.0f, 0.0f);
                }
            }
        });

    }

    public void setOnItemSelectedListener(@NonNull OnItemSelectedListener listener) {
        if (mAdapter != null) {
            mAdapter.setOnItemSelectedListener(listener);
        }
        mOnItemSelectedListener = listener;
    }

    public void setOnShowingListener(@NonNull OnShowingListener listener) {
        mOnShowingListener = listener;
    }

    public void setOnDismissListener(@NonNull OnDismissListener listener) {
        if (mSheetView != null) {
            mSheetView.setOnDismissListener(listener);
        }
        mOnDismissListener = listener;
    }

    public void makeAnimatedRotation(@NonNull View view, long durationMillis, float fromDegrees, float toDegrees) {
        AnimationSet animSet = new AnimationSet(true);
        animSet.setInterpolator(new DecelerateInterpolator());
        animSet.setFillAfter(true);
        animSet.setFillEnabled(true);

        RotateAnimation animRotate = new RotateAnimation(
                fromDegrees, toDegrees,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f
        );

        animRotate.setDuration(durationMillis);
        animRotate.setFillAfter(true);
        animSet.addAnimation(animRotate);

        view.startAnimation(animSet);
    }

}
