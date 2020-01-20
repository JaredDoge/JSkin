package com.insect.jskin.library.base;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.LayoutInflaterCompat;

import com.insect.jskin.library.SkinPlugin;
import com.insect.jskin.library.framwork.IDynamicNewView;
import com.insect.jskin.library.framwork.ISkinUpdate;
import com.insect.jskin.library.support.base.DynamicAttr;
import com.insect.jskin.library.util.SkinInflaterFactory;

import java.lang.reflect.Field;


public  class SkinBaseActivity extends AppCompatActivity implements IDynamicNewView, ISkinUpdate {

    private SkinInflaterFactory mSkinInflaterFactory;

    private SkinResources skinResources;

    private boolean isLoaded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mSkinInflaterFactory = new SkinInflaterFactory(this);
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), mSkinInflaterFactory);
        loadSkin();
        super.onCreate(savedInstanceState);

        changeStatusColor();

    }




    private void loadSkin() {
        try {
            Field field = getSuperClassByName(AppCompatActivity.class.getName()).getDeclaredField("mResources");
            skinResources =new SkinResources(this,getResources(),SkinPlugin.getInstance().getCurrentSkinPath());
            field.setAccessible(true);
            field.set(this,skinResources);
            isLoaded=true;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    private Class getSuperClassByName(String name){
        Class c=this.getClass();
        while (!c.getName().equals(name)){
            c=c.getSuperclass();
        }
        return c;
    }
    @Override
    protected void onResume() {
        super.onResume();
        SkinPlugin.getInstance().attach(this);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinPlugin.getInstance().detach(this);
        mSkinInflaterFactory.clean();
    }




    public void changeStatusColor() {
        if (!SkinPlugin.getInstance().isChangeStatusColor()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window=getWindow();
            int color = SkinPlugin.getInstance().getColorPrimaryDark();
            if (color != -1) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(color);
            }
        }
    }


    public SkinInflaterFactory getInflaterFactory() {
        return mSkinInflaterFactory;
    }






    @Override
    public void onSkinUpdate() {
        mSkinInflaterFactory.applySkin();
        if(isLoaded){
            skinResources.loadHelper(this,SkinPlugin.getInstance().getCurrentSkinPath());
        }
        changeStatusColor();
    }

    @Override
    public void dynamicAddView(View view, int attrId, int resourceId) {
        mSkinInflaterFactory.addDynamicSkinView(view,attrId,resourceId);
    }

    @Override
    public void dynamicAddView(DynamicAttr attrs) {
        mSkinInflaterFactory.addDynamicSkinView(attrs);
    }
}
