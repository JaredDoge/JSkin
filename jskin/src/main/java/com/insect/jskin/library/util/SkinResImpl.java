package com.insect.jskin.library.util;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.res.ResourcesCompat;


public class SkinResImpl implements SkinResourcesHelper {

    private String skinPackageName;
    private Resources originResources;
    private Resources skinResources;

    public SkinResImpl(Resources skinResources, Resources originResources,String skinPackageName){
        this.skinResources=skinResources;
        this.originResources =originResources;
        this.skinPackageName=skinPackageName;
    }

    private  int getSkinResId(int resId) {
         if(resId==0) return 0;
         try {

            int id= skinResources.getIdentifier(
                     originResources.getResourceEntryName(resId)
                     , originResources.getResourceTypeName(resId)
                     , skinPackageName);
             return id;
         }catch (Resources.NotFoundException e){
             e.printStackTrace();
             return 0;
         }
    }
    

    @Override
    public int getColor(int resId) {
          return getColor(resId,null);
    }

    @Override
    public int getColor(int resId, Resources.Theme theme) {

        try {
          return   ResourcesCompat.getColor(skinResources, getSkinResId(resId),theme);
        }catch (Resources.NotFoundException|NullPointerException e){
            return  ResourcesCompat.getColor(originResources, resId,theme);
        }
    }

    @Override
    public Drawable getDrawable(int resId) {
        return getDrawable(resId,null);
    }



    @Override
    public Drawable getDrawable(int resId, Resources.Theme theme) {
        try {
            return   ResourcesCompat.getDrawable(skinResources, getSkinResId( resId),theme);
        }catch (Resources.NotFoundException|NullPointerException e){
            return  ResourcesCompat.getDrawable(originResources, resId,theme);
        }
    }

    @Override
    public String getString(int resId) {
        try {
            return  skinResources.getString(getSkinResId(resId));
        }catch (Resources.NotFoundException|NullPointerException e){
            return  originResources.getString(resId);
        }
    }

    @Override
    public ColorStateList getColorStateList(int resId, Resources.Theme theme) {
        try {
            return   ResourcesCompat.getColorStateList(skinResources, getSkinResId(resId),theme);
        }catch (Resources.NotFoundException|NullPointerException e){
            return  ResourcesCompat.getColorStateList(originResources, resId,theme);
        }

    }

    @Override
    public ColorStateList getColorStateList(int resId) {
        return getColorStateList(resId,null);
    }

    @Override
    public Resources getResources() {
        return skinResources;
    }




}
