package com.insect.jskin.library.base;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.insect.jskin.library.SkinPlugin;
import com.insect.jskin.library.util.SkinResourcesHelper;

public class SkinResources extends Resources {
    private SkinResourcesHelper helper;

    public SkinResources(Context context,Resources originResources , String skinPath) {
        super(originResources.getAssets(), originResources.getDisplayMetrics(), originResources.getConfiguration());
        loadHelper(context, skinPath);
    }

    public void loadHelper(Context context, String skinPath){
        helper= SkinPlugin.getInstance().createResHelper(context,skinPath);
    }



    @Override
    public Drawable getDrawable(int id, @Nullable Theme theme) throws NotFoundException {
        return helper.getDrawable(id,theme);
    }

    @Override
    public Drawable getDrawable(int id) throws NotFoundException {
        return helper.getDrawable(id);
    }

    @Override
    public int getColor(int id, @Nullable Theme theme) throws NotFoundException {
        return helper.getColor(id,theme);
    }

    @NonNull
    @Override
    public ColorStateList getColorStateList(int id, @Nullable Theme theme) throws NotFoundException {
        return helper.getColorStateList(id, theme);
    }

    @NonNull
    @Override
    public ColorStateList getColorStateList(int id) throws NotFoundException {
        return helper.getColorStateList(id);
    }

    @Override
    public int getColor(int id) throws NotFoundException {
        return helper.getColor(id);
    }

    @NonNull
    @Override
    public String getString(int id) throws NotFoundException {
        return helper.getString(id);
    }





}
