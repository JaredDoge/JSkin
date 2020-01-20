package com.insect.jskin.library.support;

import android.view.View;
import android.widget.TextView;

import com.insect.jskin.library.SkinPlugin;
import com.insect.jskin.library.support.base.SkinAttr;

public class TextColorAttr extends SkinAttr {
    @Override
    protected void applySkin(View view) {
        if(view instanceof TextView){
            TextView tv = (TextView) view;
            if(isColor()) {
                tv.setTextColor(SkinPlugin.getInstance().getColor(resourcesId));
            }else if(isDrawable()){
                tv.setTextColor(SkinPlugin.getInstance().getColorStateList(resourcesId));
            }
        }
    }
}
