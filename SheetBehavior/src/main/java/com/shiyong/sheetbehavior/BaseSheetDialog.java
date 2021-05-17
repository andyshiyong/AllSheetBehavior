package com.shiyong.sheetbehavior;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * 支持手势控制的 Dialog
 */
public abstract class BaseSheetDialog extends AppCompatDialog {
    protected AllSheetBehavior<FrameLayout> mBehavior;
    private boolean mCancelable = true;
    private boolean mCanceledOnTouchOutside = true;
    private boolean mCanceledOnTouchOutsideSet;
    private FrameLayout mSheetView;

    public BaseSheetDialog(Context context, @StyleRes int theme) {
        super(context, theme);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResId) {
        super.setContentView(wrapInSheet(layoutResId, null, null));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(wrapInSheet(0, view, null));
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(wrapInSheet(0, view, params));
    }

    @Override
    public void setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
        if (mCancelable != cancelable) {
            mCancelable = cancelable;
        }
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
        if (cancel && !mCancelable) {
            mCancelable = true;
        }
        mCanceledOnTouchOutside = cancel;
        mCanceledOnTouchOutsideSet = true;
    }

    private View wrapInSheet(int layoutResId, View view, ViewGroup.LayoutParams params) {
        final View container = View.inflate(getContext(),
                R.layout.design_sheet_base_dialog, null);
        final CoordinatorLayout coordinator = container.findViewById(R.id.coordinator);
        if (layoutResId != 0 && view == null) {
            view = getLayoutInflater().inflate(layoutResId, coordinator, false);
        }
        mSheetView = coordinator.findViewById(R.id.design_sheet);
        mBehavior = AllSheetBehavior.from(mSheetView);
        mBehavior.setSheetCallback(mSheetCallback);
        mBehavior.setSlideModel(getSlideMode());
        if (params == null) {
            mSheetView.addView(view);
        } else {
            mSheetView.addView(view, params);
        }
        container.findViewById(R.id.touch_outside).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCancelable && isShowing()) {
                    dismiss();
                }
            }
        });
        return container;
    }

    private AllSheetBehavior.SheetCallback mSheetCallback = new AllSheetBehavior.SheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet,
                                   @AllSheetBehavior.State int newState) {
            if (newState == AllSheetBehavior.STATE_HIDDEN
                    || newState == AllSheetBehavior.STATE_COLLAPSED) {
                BaseSheetDialog.super.dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    /**
     * 滑动方向
     *
     * @return {@link AllSheetBehavior.SlideMode}
     */
    public abstract int getSlideMode();

    @Override
    public void show() {
        super.show();
        mSheetView.post(new Runnable() {
            @Override
            public void run() {
                if (mBehavior != null) {
                    mBehavior.expand();
                }
            }
        });
    }

    @Override
    public void dismiss() {
        mBehavior.collapsed();
    }
}