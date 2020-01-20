package com.insect.jskin.library.support.base;

import android.content.Context;
import android.util.ArrayMap;
import android.view.View;

import androidx.annotation.AnyRes;

import java.util.ArrayList;
import java.util.List;

public class DynamicAttr {


    private List<SkinAttr> viewAttrs;

    private View v;

    public List<SkinAttr> getViewAttrs() {
        return viewAttrs;
    }

    public Context getContext(){
        return v.getContext();
    }

    public void setViewAttrs(List<SkinAttr> viewAttrs) {
        this.viewAttrs = viewAttrs;
    }

    public View getView() {
        return v;
    }

    public void setView(View v) {
        this.v = v;
    }

    private DynamicAttr(View v, ArrayMap<Integer, Integer> dynamic) {
        this.v=v;
        parseToSkinAttrList(v,dynamic);
    }

    private void parseToSkinAttrList(View v, ArrayMap<Integer, Integer> dynamic) {
        Context context=v.getContext();
        if(!dynamic.isEmpty()){
            viewAttrs=new ArrayList<>();
        }
        for(Integer key:dynamic.keySet()) {
            if(dynamic.get(key)==null) continue;
            SkinAttr mSkinAttr = AttrFactory.get(context,key,dynamic.get(key));
            viewAttrs.add(mSkinAttr);
        }
    }

    public static class Builder{
        View v;
                        //<attrId,resId>
        private ArrayMap<Integer,Integer> dynamic=new ArrayMap<>();

        public Builder(View v){
            this.v=v;
        }

        public DynamicAttr.Builder add(int attrId,@AnyRes int resourceId){
            dynamic.put(attrId,resourceId);
            return this;
        }

        public DynamicAttr build(){
            return new DynamicAttr(v,dynamic);
        }
    }
}
