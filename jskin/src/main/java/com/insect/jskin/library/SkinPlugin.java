package com.insect.jskin.library;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.SparseArray;

import com.insect.jskin.library.framwork.ISkinUpdate;
import com.insect.jskin.library.support.base.AttrFactory;
import com.insect.jskin.library.support.base.SkinAttr;
import com.insect.jskin.library.util.SkinFileUtil;
import com.insect.jskin.library.util.SkinResImpl;
import com.insect.jskin.library.util.SkinResourcesHelper;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class SkinPlugin implements SkinResourcesHelper {

    private static final String TAG="SkinPlugin";

    private static volatile  SkinPlugin instance;

    private boolean globalSkinApply=SkinDefaultConfig.GLOBAL_SKIN_APPLY;

    private String baseSkinPath;

    private Context context;

    private SkinResourcesHelper skinHelper;

    private List<ISkinUpdate> mSkinObservers;

    private String currentSkinPath;

    private boolean changeStatusColor=true;

    private String skinPackageName;

    private boolean debugLog;


    private SkinPlugin(Context context) {

        this.context=context.getApplicationContext();

        this.baseSkinPath=context.getFilesDir().getAbsolutePath()+
                File.separator+SkinDefaultConfig.DEFAULT_SKIN_FOLDER_NAME;
        SkinFileUtil.createFolder(baseSkinPath);

    }


    public static SkinPlugin init(Context context) {
        if (instance == null) {
            synchronized (SkinPlugin.class) {
                if (instance == null) {
                    instance = new SkinPlugin(context);
                }
            }
        }
        return instance;
    }

    public static SkinPlugin getInstance(){
        return instance;
    }

    public SkinPlugin basePath(String basePath){
        this.baseSkinPath=basePath;
        return this;
    }

    public String getBasePath(){
       return baseSkinPath;
    }

    public boolean isGlobalSkinApply() {
        return globalSkinApply;
    }

    public boolean isChangeStatusColor() {
        return changeStatusColor;
    }

    public SkinPlugin changeStatusColor(boolean changeStatusColor) {
        this.changeStatusColor = changeStatusColor;
      return this;
    }

    public void load(String skinName){
      loadByPath(baseSkinPath+File.separator+skinName);
    }

    public void loadByPath(String skinPath){
        currentSkinPath =skinPath;
        skinHelper=createResHelper(context, currentSkinPath);
    }


    public String getCurrentSkinPath(){
        return currentSkinPath;
    }

    public SkinPlugin globalSkinApply(boolean globalSkinApply){
        this.globalSkinApply=globalSkinApply;
        return this;
    }

    public SkinPlugin addSupportAttr(int attrId, Class<?extends SkinAttr> skinAttr){
        AttrFactory.addSupportAttr(attrId,skinAttr);
      return this;
    }
    public SkinPlugin  addSupportAttr(SparseArray<Class<? extends SkinAttr>> array) {
        AttrFactory.addSupportAttr(array);
        return this;
    }
    /**
     * skin 插件是否存在
     *
     * @return isExist
     */
    public boolean skinExist(String name) {
        return SkinFileUtil.checkFileExists(skinPath(name));
    }

    /**
     * skin 插件是否存在
     *
     * @return isExist
     */
    public boolean skinExistByPath(String path) {
        return SkinFileUtil.checkFileExists(path);
    }



    public  void attach(ISkinUpdate observer) {
        if (mSkinObservers == null) {
            mSkinObservers = new ArrayList<>();
        }
        if (!mSkinObservers.contains(observer)) {
            mSkinObservers.add(observer);
        }
    }

    public  void detach(ISkinUpdate observer) {
        if (mSkinObservers != null) {
            mSkinObservers.remove(observer);
        }
    }

    /**
     * 更新skin
     */
    public  void notifySkinUpdate() {
        if (mSkinObservers != null) {
            for (ISkinUpdate observer : mSkinObservers) {
                observer.onSkinUpdate();
            }
        }
    }

    public boolean isDebugLog() {
        return debugLog;
    }

    public SkinPlugin debugLog(boolean debugLog) {
        this.debugLog = debugLog;
        return this;
    }

    /**
     * 獲取配件版本號
     *
     * @return
     */
    public  int getPluginVersionCodeByPath(String skinPath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pkgInfo = pm.getPackageArchiveInfo(skinPath
                , PackageManager.GET_ACTIVITIES);
        if (null != pkgInfo) {
            Log.i(TAG,"skin plugin version(" + pkgInfo.versionCode + ")");
            return pkgInfo.versionCode;
        } else {
            Log.e(TAG,"skin plugin don't get apk package information");
        }
        return -1;
    }

    /**
     * 獲取配件版本號
     *
     * @return
     */
    public  int getPluginVersionCode(String name) {
      return getPluginVersionCodeByPath(skinPath(name));
    }

    public String getSkinPackageName() {
        return skinPackageName;
    }

    public  SkinResourcesHelper createResHelper(Context context, String skinPath) {

    try {
        PackageManager mPm = context.getPackageManager();
        PackageInfo mInfo = mPm.getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES);
        if(mInfo==null) return null;
        skinPackageName = mInfo.packageName;

        AssetManager assetManager = AssetManager.class.newInstance();
        Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
        addAssetPath.invoke(assetManager, skinPath);
        Resources resources=context.getResources();
        Resources mResources = new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());

        return new SkinResImpl(mResources,context.getResources(),skinPackageName);
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }

}

    public  int getColorPrimaryDark() {
            Resources resources=skinHelper.getResources();

            int identify = resources.getIdentifier(
                    "colorPrimaryDark",
                    "color",skinPackageName);
            if (identify > 0) {
                return resources.getColor(identify);
            }
            return -1;
    }

    @Override
    public int getColor(int resId) {
        return skinHelper.getColor(resId);
    }

    @Override
    public int getColor(int resId, Resources.Theme theme) {

        return skinHelper.getColor(resId, theme);
    }

    @Override
    public Drawable getDrawable(int resId) {
        return skinHelper.getDrawable(resId);
    }

    @Override
    public Drawable getDrawable(int resId, Resources.Theme theme) {
        return skinHelper.getDrawable(resId, theme);
    }

    @Override
    public String getString(int resId) {
        return skinHelper.getString(resId);
    }

    @Override
    public ColorStateList getColorStateList(int resId, Resources.Theme theme) {
        return skinHelper.getColorStateList(resId, theme);
    }

    @Override
    public ColorStateList getColorStateList(int resId) {
        return skinHelper.getColorStateList(resId);
    }

    @Override
    public Resources getResources() {
        return skinHelper.getResources();
    }

    private String skinPath(String name){
        return baseSkinPath+File.separator+name;
    }



}
