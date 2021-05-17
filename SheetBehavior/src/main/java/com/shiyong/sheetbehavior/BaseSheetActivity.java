package com.shiyong.sheetbehavior;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;


/**
 * 支持手势控制的BaseSheetActivity
 */
public abstract class BaseSheetActivity extends AppCompatActivity {
    private AllSheetBehavior<FrameLayout> mBehavior;

    @Override
    public void setContentView(@LayoutRes int layoutResId) {
        super.setContentView(wrapInSheet(layoutResId, null, null));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0,0);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(wrapInSheet(0, view, null));
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(wrapInSheet(0, view, params));
    }

    private View wrapInSheet(int layoutResId, View view, ViewGroup.LayoutParams params) {
        final CoordinatorLayout coordinator = (CoordinatorLayout) View.inflate(this,
                R.layout.design_sheet_base_activity, null);
        if (layoutResId != 0 && view == null) {
            view = getLayoutInflater().inflate(layoutResId, coordinator, false);
        }
        FrameLayout designSheet = coordinator.findViewById(R.id.design_sheet);
        mBehavior = AllSheetBehavior.from(designSheet);
        mBehavior.setSheetCallback(mSheetCallback);
        mBehavior.setSlideModel(getSlideMode());
        if (params == null) {
            designSheet.addView(view);
        } else {
            designSheet.addView(view, params);
        }
        coordinator.post(new Runnable() {
            @Override
            public void run() {
                mBehavior.expand();//执行展开动画
            }
        });
        return coordinator;
    }


    private AllSheetBehavior.SheetCallback mSheetCallback = new AllSheetBehavior.SheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet,
                                   @AllSheetBehavior.State int newState) {
            if (newState == AllSheetBehavior.STATE_HIDDEN
                    || newState == AllSheetBehavior.STATE_COLLAPSED) {
                overridePendingTransition(0,0);
                BaseSheetActivity.super.finish();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    @Override
    public void finish() {
        if (mBehavior != null) {
            mBehavior.collapsed();
        } else {
            super.finish();
        }
    }


    /**
     * 滑动方向，
     * @return {@link AllSheetBehavior.SlideMode}
     */
    protected abstract int getSlideMode();
}