package com.insect.jskin.library.util;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.ArrayMap;

public  class FontManager {

    private static final String PARENT_PATH="fonts/";

    private static FontManager instance;

    private ArrayMap<String,Typeface> cache;




    public FontManager() {
        this.cache = new ArrayMap<>();
    }

    public synchronized static FontManager getInstance() {
        if (instance == null) {
            instance=new FontManager();
        }
        return instance;
    }



    public Typeface getFont(AssetManager assetManager, String fileName) {
        if (cache.containsKey(fileName))
            return cache.get(fileName);

        Typeface font = null;

        try {
            font = Typeface.createFromAsset(assetManager, format(fileName));
            cache.put(fileName, font);
        } catch (Exception e) {
              e.printStackTrace();
        }

        return font;
    }

    private String format(String fileName){
        return String.format(PARENT_PATH+"%s",fileName);
    }





}
