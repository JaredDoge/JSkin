package com.insect.jskin.library.support;

import android.view.View;

import com.insect.jskin.library.SkinPlugin;
import com.insect.jskin.library.support.base.SkinAttr;

public class BackgroundAttr extends SkinAttr {
    @Override
    protected void applySkin(View view) {
        if (isColor()) {
            view.setBackgroundColor(SkinPlugin.getInstance().getColor(resourcesId));
        } else if (isDrawable()) {
            view.setBackground(SkinPlugin.getInstance().getDrawable(resourcesId));
        }
    }
}
