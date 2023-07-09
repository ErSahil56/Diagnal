package com.example.myapplication.util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TextSemiBold extends androidx.appcompat.widget.AppCompatTextView {
    public TextSemiBold(@NonNull Context context) {
        super(context);

        Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/TitilliumWebSemiBold.ttf");
        this.setTypeface(face);


    }

    public TextSemiBold(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/TitilliumWebSemiBold.ttf");
        this.setTypeface(face);
    }

    public TextSemiBold(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/TitilliumWebSemiBold.ttf");
        this.setTypeface(face);
    }


}
