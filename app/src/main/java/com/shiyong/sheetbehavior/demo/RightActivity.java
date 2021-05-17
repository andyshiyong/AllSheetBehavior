package com.shiyong.sheetbehavior.demo;

import android.os.Bundle;

import com.shiyong.sheetbehavior.AllSheetBehavior;
import com.shiyong.sheetbehavior.BaseSheetActivity;

/**
 */
public class RightActivity extends BaseSheetActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_right);
    }

    @Override
    protected int getSlideMode() {
        return AllSheetBehavior.RIGHT_SHEET;
    }
}
