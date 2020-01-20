package com.insect.jskin.library.util;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

public interface SkinResourcesHelper {

    /**
     * get plugin color
     * @param resId
     * @return if plugin color  not found when  return origin color
     */

    int getColor(int resId);
    int getColor(int resId, Resources.Theme theme);
    /**
     * get plugin drawable
     * @param resId
     * @return if plugin drawable   not found when  return origin resource drawable
     */
    Drawable getDrawable(int resId);
    Drawable getDrawable(int resId, Resources.Theme theme);

    /**
     * get plugin string
     * @param resId
     * @return if plugin string   not found when  return origin resource string
     */
    String getString(int resId);


    ColorStateList getColorStateList(int resId, Resources.Theme theme);
    ColorStateList getColorStateList(int resId);

    Resources getResources();


}
