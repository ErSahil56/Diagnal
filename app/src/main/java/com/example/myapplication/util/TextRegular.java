package com.example.myapplication.util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TextRegular extends androidx.appcompat.widget.AppCompatTextView {
    public TextRegular(@NonNull Context context) {
        super(context);

        Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/TitilliumWebRegular.ttf");
        this.setTypeface(face);

    }

    public TextRegular(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/TitilliumWebRegular.ttf");
        this.setTypeface(face);
    }

    public TextRegular(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/TitilliumWebRegular.ttf");
        this.setTypeface(face);
    }


}
