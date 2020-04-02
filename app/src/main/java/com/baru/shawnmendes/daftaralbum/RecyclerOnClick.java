package com.baru.shawnmendes.daftaralbum;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


public class RecyclerOnClick implements RecyclerView.OnItemTouchListener {

    private OnItemClickListener clickListener;
    private GestureDetector blanfh;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public RecyclerOnClick(Context context, OnItemClickListener listener) {
        clickListener = listener;
        blanfh = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && clickListener != null && blanfh.onTouchEvent(e)) {
            clickListener.onItemClick(childView, view.getChildPosition(childView));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

}