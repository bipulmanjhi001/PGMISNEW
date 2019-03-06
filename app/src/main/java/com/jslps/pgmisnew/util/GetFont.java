package com.jslps.pgmisnew.util;

import android.content.Context;
import android.graphics.Typeface;

public class GetFont {

    private Context context;
    private Typeface face;

    public GetFont(Context context) {
        this.context = context;
    }

    public Typeface font(){
        face = Typeface.createFromAsset(context.getAssets(), "font/UbuntuMono-B.ttf");
        return face;
    }
}
