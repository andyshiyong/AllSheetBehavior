package com.shiyong.sheetbehavior.demo;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shiyong.sheetbehavior.BaseSheetDialog;

/**
 */
public class MyDialog extends BaseSheetDialog {

    private int mSlideMode;
    private RecyclerView mRecyclerView;

    public MyDialog(Context context, int slideMode) {
        super(context, R.style.TransparentDialog);
        this.mSlideMode = slideMode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getOwnerActivity()));
        mRecyclerView.setAdapter(new MyAdapter());
    }

    @Override
    public int getSlideMode() {
        return mSlideMode;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.action_sheet_common_button, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
            viewHolder.mTextView.setText("Button " + (i + 1));
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.action_sheet_button);
        }
    }

}
