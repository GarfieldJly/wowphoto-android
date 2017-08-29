package com.milier.wowgallery.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

import com.milier.wowgallery.utils.MilierLog;

/**
 * Created by jly on 2017/4/12.
 */

public class MoveImageButton extends android.widget.ImageButton {
    private int lastX = 0;

    private int lastY = 0;

    private int initX = 0;

    private int initY = 0;

    private int screenWidth = 720;
    //屏幕宽度
    private int screenHeight = 1280;
//屏幕高度

    public MoveImageButton(Context context) {
        this(context, null);
    }

    public MoveImageButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initX = lastX = (int) event.getRawX();
                initY = lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;

                if (Math.abs(dx) > 10 || Math.abs(dy) > 10) {

                    int left = getLeft() + dx;
                    int top = getTop() + dy;
                    int right = getRight() + dx;
                    int bottom = getBottom() + dy;
                    if (left < 0) {
                        left = 0;
                        right = left + getWidth();
                    }
                    if (right > screenWidth) {
                        right = screenWidth;
                        left = right - getWidth();
                    }
                    if (top < 0) {
                        top = 0;
                        bottom = top + getHeight();
                    }
                    if (bottom > screenHeight) {
                        bottom = screenHeight;
                        top = bottom - getHeight();
                    }
                    layout(left, top, right, bottom);
                    lastX = (int) event.getRawX();
                    lastY = (int) event.getRawY();
                }

                break;
            case MotionEvent.ACTION_UP:
                int dxx = (int) event.getRawX() - initX;
                int dyy = (int) event.getRawY() - initY;
                if (Math.abs(dxx) < 10 && Math.abs(dyy) < 10) {
                    MilierLog.i("info", "新手福利");

                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
