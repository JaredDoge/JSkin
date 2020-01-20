package com.insect.jskin.library.support;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;

import com.insect.jskin.library.SkinPlugin;
import com.insect.jskin.library.support.base.SkinAttr;

public class ImageViewSrcAttr extends SkinAttr {

    @Override
    protected void applySkin(View view) {
        if(view instanceof ImageView){
            ImageView v= (ImageView) view;
            if (isDrawable()) {
                v.setImageDrawable(SkinPlugin.getInstance().getDrawable(resourcesId));
            } else if (isColor()) {
                v.setImageDrawable(new ColorDrawable(SkinPlugin.getInstance().getColor(resourcesId)));
            }
        }

    }
}
