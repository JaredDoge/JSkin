package com.insect.jskin.library.util;

import android.content.Context;

import com.insect.jskin.library.SkinPlugin;

import java.io.File;

public class SkinFileUtil {



   /**
    * 檢查內部空間檔案是否存在
    *
    * @return
    */
   public static   boolean checkFileExists(String filePath) {
       return new File(filePath).exists();
   }

   public static boolean createFolder(String folderPath){
       File file=new File(folderPath);
      return file.mkdirs();
   }

}
