package com.jslps.pgmisnew.util;

import android.widget.Spinner;

public class SetSpinnerText {
    public SetSpinnerText(Spinner spin, String text) {
        for(int i= 0; i < spin.getAdapter().getCount(); i++)
        {
            if(spin.getAdapter().getItem(i).toString().equals(text))
            {
                spin.setSelection(i);
            }
        }
    }
}
