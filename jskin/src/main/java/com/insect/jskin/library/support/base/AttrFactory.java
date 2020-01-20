package com.insect.jskin.library.support.base;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

import com.insect.jskin.R;
import com.insect.jskin.library.support.BackgroundAttr;
import com.insect.jskin.library.support.ImageViewSrcAttr;
import com.insect.jskin.library.support.TextColorAttr;


public final class AttrFactory {



    private static SparseArray<Class<? extends SkinAttr>> support= new SparseArray<>();

    /**
     * 支持map
     * support.put(父類別class , 對應的attr class)
     */
    static {
        support.put(android.R.attr.src, ImageViewSrcAttr.class);
        support.put(R.attr.srcCompat,ImageViewSrcAttr.class);
        support.put(android.R.attr.background, BackgroundAttr.class);
        support.put(android.R.attr.textColor, TextColorAttr.class);

    }

    /**
     * check current attribute if can be support
     *
     * @return true : supported <br>
     * false: not supported
     */
    public  static boolean isSupport(int attrId){
       return support.get(attrId)!=null;
    }


    public static int[] getSupportAttrs(){
        int[] attrs=new int[support.size()];
        for(int i = 0;i<support.size();i++) {
           attrs[i]=support.keyAt(i);
        }
      //  Arrays.sort(attrs);
        return attrs;
    }


    /**
     * get Base attr
     * @return
     */

    public static  SkinAttr get(String attrName,int attrId,int resId, String resName, String resTypeName) {
       Class c=support.get(attrId);
       if(c==null) return null;
        try {
            SkinAttr skinAttr= (SkinAttr) c.newInstance();
            skinAttr.attrName=attrName;
            skinAttr.attrId=attrId;
            skinAttr.resourcesId=resId;
            skinAttr.resourcesName=resName;
            skinAttr.resourcesTypeName=resTypeName;
            return skinAttr;
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static  SkinAttr get(Context ctx,int attrId, int resId) {
        Class c=support.get(attrId);
        if(c==null) return null;
        //entryName，eg:text_color_selector
        String entryName = ctx.getResources().getResourceEntryName(resId);
        //typeName，eg:color、drawable
        String typeName = ctx.getResources().getResourceTypeName(resId);
        try {
            SkinAttr skinAttr= (SkinAttr) c.newInstance();
            skinAttr.attrName=ctx.getResources().getResourceEntryName(attrId);
            skinAttr.attrId=attrId;
            skinAttr.resourcesId=resId;
            skinAttr.resourcesName=entryName;
            skinAttr.resourcesTypeName=typeName;
            return skinAttr;
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addSupportAttr(int attrId, Class<? extends SkinAttr> skinAttr) {
        support.put(attrId, skinAttr);
    }

    public static void addSupportAttr(SparseArray<Class<? extends SkinAttr>> array){

        for (int i=0;i<array.size();i++) {
            int key=array.keyAt(i);
            support.put(key,array.get(key));
        }
    }

//    private static  <T> Class<T> getActualTypeArgument(Class<? extends BaseAttr> c){
//        try {
//            ParameterizedType pt = (ParameterizedType) c.getGenericSuperclass();
//            return (Class<T>) pt.getActualTypeArguments()[0];
//        }catch (ClassCastException e){
//            e.printStackTrace();
//            throw e;
//        }
//
//    }


}
