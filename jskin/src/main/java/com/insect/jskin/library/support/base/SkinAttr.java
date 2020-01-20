package com.insect.jskin.library.support.base;

import android.view.View;

import com.insect.jskin.library.util.JSkinLog;

public abstract class SkinAttr {

    protected static final String RES_TYPE_NAME_COLOR = "color";
    protected static final String RES_TYPE_NAME_DRAWABLE = "drawable";
    protected static final String RES_TYPE_NAME_MIPMAP = "mipmap";
    /**
     * attribute name, eg: background、textColor
     */
    protected String attrName;
    /**
     * attribute id eg: android.R.attr.src 、android.R.attr.background
     */
    protected int attrId;


    /**
     * attribute reference id
     */
    protected int resourcesId;

    /**
     * resources name, eg:app_exit_btn_background
     */
    protected String resourcesName;

    /**
     * type of the value , such as color or drawable
     */
    protected String resourcesTypeName;

    public void apply(View view) {
        //是否是黑暗模式?
         JSkinLog.i(toString());
         applySkin( view);

    }


    protected abstract void applySkin(View view);

    protected void applyNightMode(View view) {

    }

    protected boolean isDrawable() {
        return RES_TYPE_NAME_DRAWABLE.equals(resourcesTypeName)
                || RES_TYPE_NAME_MIPMAP.equals(resourcesTypeName);
    }

    protected boolean isColor() {
        return RES_TYPE_NAME_COLOR.equals(resourcesTypeName);
    }

    @Override
    public String toString() {
        return "SkinAttr -" +
                " attrName = " + attrName +
                ", attrId = "+ attrId +
                ", resourcesId = " + resourcesId +
                ", resourcesName = " + resourcesName +
                ", resourcesTypeName = " + resourcesTypeName;
    }


}
