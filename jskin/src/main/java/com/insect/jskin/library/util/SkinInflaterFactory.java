package com.insect.jskin.library.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.insect.jskin.library.SkinDefaultConfig;
import com.insect.jskin.library.SkinPlugin;
import com.insect.jskin.library.support.base.DynamicAttr;
import com.insect.jskin.library.support.base.SkinItem;
import com.insect.jskin.library.support.base.AttrFactory;
import com.insect.jskin.library.support.base.SkinAttr;

import java.util.ArrayList;
import java.util.List;


@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class SkinInflaterFactory implements LayoutInflater.Factory2 {

    private AppCompatActivity mAppCompatActivity;
    private ArrayMap<View, SkinItem> mSkinItemMap=new ArrayMap<>();


    public SkinInflaterFactory(AppCompatActivity appCompatActivity) {
        this.mAppCompatActivity = appCompatActivity;
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

   //     TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.skin);
     //   boolean isSkinEnable=array.getBoolean(R.styleable.skin_skin_enable,true);
        boolean isSkinEnable = attrs.getAttributeBooleanValue(SkinDefaultConfig.NAMESPACE, SkinDefaultConfig.ATTR_SKIN_ENABLE_NAME, false);

        AppCompatDelegate delegate = mAppCompatActivity.getDelegate();
        View view = delegate.createView(parent, name, context, attrs);

//        boolean hasFont=array.hasValue(R.styleable.skin_text_font);
//
//        boolean fontEnable=array.getBoolean(R.styleable.skin_font_enable,false);
//
//        //切換字型
//        if(view instanceof TextView && hasFont){
//            if(fontEnable){
//
//
//            }else{
//
//
//            }
//        }

      //  array.recycle();

        if (isSkinEnable || SkinPlugin.getInstance().isGlobalSkinApply()) {
            if (view == null) {
                view = ViewProducer.createViewFromTag(context, name, attrs);
            }
            if (view == null) {
                return null;
            }
            parseSkinAttr(context,attrs, view);
        }


        return view;
    }

    private void parseSkinAttr(Context context,AttributeSet attrs, View view) {


        List<SkinAttr> viewAttrs=new ArrayList<>();
        for(int i=0;i<attrs.getAttributeCount();i++){
            String attrName = attrs.getAttributeName(i);
            int attrId=attrs.getAttributeNameResource(i);
            String attrValue = attrs.getAttributeValue(i);
            JSkinLog.i("parseSkinAttr - attrName:" +attrName
                    + " | attrValue:" + attrValue+" | attrId:"+attrId);
            if("style".equals(attrName)){
                int[] skinAttrs = AttrFactory.getSupportAttrs();

                TypedArray a = context.obtainStyledAttributes(attrs, skinAttrs, 0, 0);

                for(int x=0;x<a.getIndexCount();x++){
                    int index=a.getIndex(x);
                    int resourceId=a.getResourceId(index,-1);
                    if(resourceId==-1){
                        JSkinLog.w("attrName:"+attrName +" is not support or value is not startsWith(\"@\")!! ");
                        continue;
                    }

                    SkinAttr mSkinAttr = AttrFactory.get(context,skinAttrs[index],resourceId);
                    if (mSkinAttr != null) {
                        viewAttrs.add(mSkinAttr);
                    }

                }
                a.recycle();
                continue;
            }

            if(AttrFactory.isSupport(attrId) && attrValue.startsWith("@")){
                try {
                    //resource id
                    int id = Integer.parseInt(attrValue.substring(1));
                    if (id == 0) continue;
                    SkinAttr mSkinAttr = AttrFactory.get(context,attrId, id);
                    if (mSkinAttr != null) {
                        viewAttrs.add(mSkinAttr);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }else{
                JSkinLog.w("attrName:"+attrName +" is not support or value is not startsWith(\"@\")!! ");
            }


        }
        JSkinLog.i( "-----------------------------------------------");
        if(!viewAttrs.isEmpty()) {
            SkinItem skinItem=new SkinItem(view,viewAttrs);
            mSkinItemMap.put(view,skinItem);
           // skinItem.apply();
        }

    }

    public void applySkin() {
        if (mSkinItemMap.isEmpty()) {
            return;
        }
        for (View view : mSkinItemMap.keySet()) {
            if (view == null || mSkinItemMap.get(view)==null) {
                continue;
            }
            mSkinItemMap.get(view).apply();
        }
    }

    /**
     * clear skin view
     */
    public void clean() {
        for (View view : mSkinItemMap.keySet()) {
            if (view == null|| mSkinItemMap.get(view)==null) {
                continue;
            }
            mSkinItemMap.get(view).clean();
        }
        mSkinItemMap.clear();
        mSkinItemMap = null;
    }

    /**
     * Dynamically add skin view
     *
     */
    public void addDynamicSkinView( View view, int attrId, int resourceId) {
        List<SkinAttr> viewAttrs = new ArrayList<>();
        viewAttrs.add(AttrFactory.get(view.getContext(),attrId,resourceId));
        SkinItem skinItem = new SkinItem(view,viewAttrs);
        addSkinView(skinItem);
    }


    public void addDynamicSkinView(DynamicAttr attrs) {
        SkinItem skinItem = new SkinItem(attrs.getView(),attrs.getViewAttrs());
        addSkinView(skinItem);
    }



    private void addSkinView(SkinItem item) {
        if (mSkinItemMap.get(item.view) != null) {
            mSkinItemMap.get(item.view).attrs.addAll(item.attrs);
        } else {
            mSkinItemMap.put(item.view, item);
        }
    }



    public void removeSkinView(View v) {
        mSkinItemMap.remove(v);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return null;
    }
}
