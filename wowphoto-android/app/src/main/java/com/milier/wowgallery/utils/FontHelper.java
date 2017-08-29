package com.milier.wowgallery.utils;

import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jly on 2017/1/3.
 */
public class FontHelper {
    private static final String FONT_DIR = "fonts/";
    private static final String FONT_PATH = FONT_DIR + "font.ttf";

    public static void injectFont(View view){
        injectFont(view, Typeface.createFromAsset(view.getContext().getAssets(),FONT_PATH));
    }

    public static void injectFont(View rootView, Typeface typeface){
        if(rootView instanceof ViewGroup){
            ViewGroup viewGroup = (ViewGroup) rootView;
            for(int i = 0; i < viewGroup.getChildCount();i++){
                injectFont(viewGroup.getChildAt(i));
            }
        }else if(rootView instanceof TextView){
            ((TextView) rootView).setTypeface(typeface);
        }
    }
}
