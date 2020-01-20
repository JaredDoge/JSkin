package com.insect.jskin.library.support.base;

import android.view.View;


import java.util.List;

public class SkinItem {

    public View view;

    public List<SkinAttr> attrs;

    public SkinItem(View view ,List<SkinAttr> attrs) {
        this.view=view;
        this.attrs=attrs;
    }

    public void apply() {
        for (SkinAttr at : attrs) {
            at.apply(view);
        }

    }

    public void clean() {
        attrs.clear();
    }

    @Override
    public String toString() {
        return "SkinItem [view=" + view.getClass().getSimpleName() + ", attrs=" + attrs + "]";
    }

}
