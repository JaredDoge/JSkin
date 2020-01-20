package com.insect.jskin.library.framwork;

import android.view.View;

import com.insect.jskin.library.support.base.DynamicAttr;


public interface IDynamicNewView {

    void dynamicAddView(View view, int attrId, int resourceId);
    void dynamicAddView(DynamicAttr attrs);

}
