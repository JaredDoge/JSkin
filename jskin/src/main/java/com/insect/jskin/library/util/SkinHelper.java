package com.insect.jskin.library.util;

import android.content.res.AssetManager;
import android.content.res.Resources;

public interface SkinHelper {

   /**
    * get plugin AssetManager
    * @return if plugin AssetManager   not found when  return origin AssetManager
    */
   AssetManager getAssets();

   /**
    * get plugin resources
    * @return   if plugin resources   not found when  return origin resources
    */
   Resources getResources();

   /**
    * get plugin theme
    * @returnif plugin theme   not found when  return origin theme
    */
   Resources.Theme getTheme();





}
