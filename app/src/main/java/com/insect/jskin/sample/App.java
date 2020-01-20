package com.insect.jskin.sample;

import android.app.Application;
import android.content.res.AssetManager;
import android.util.Log;

import com.insect.jskin.factory.SupportAttrFactory;
import com.insect.jskin.library.SkinPlugin;
import com.insect.jskin.library.support.base.AttrFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        copyAssets("LDM_skin.skin");
        copyAssets("LDM_skin2.skin");
                SkinPlugin.init(this)
                .globalSkinApply(true)
                .debugLog(true)
                .changeStatusColor(true)
                .addSupportAttr(SupportAttrFactory.getSupportAttrs())
                .load("LDM_skin.skin");


    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }



    private void copyAssets(String file) {
        AssetManager assetManager = getAssets();
        String filename = file;
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(filename);

            String outDir = getFilesDir().getAbsolutePath() + File.separator + "skin";

            File outFile = new File(outDir, filename);

            out = new FileOutputStream(outFile);
            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (IOException e) {
            Log.e("tag", "Failed to copy asset file: " + filename, e);
        }

    }
}
