package com.insect.jskin.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.insect.jskin.library.SkinPlugin;
import com.insect.jskin.library.base.SkinBaseActivity;
import com.insect.jskin.library.support.base.DynamicAttr;
import com.insect.jskin.sample.databinding.ActivityMainBinding;


public class MainActivity extends SkinBaseActivity {

    ActivityMainBinding vd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         vd= DataBindingUtil.setContentView(this,R.layout.activity_main);
        ViewModel vm=new ViewModel();
    //    vd.setLifecycleOwner(this);
        vd.setSs(false);
        vd.setVm(vm);
        vd.iv2.setOnClickListener(v -> {
            vm.getShow().setValue(!vm.getShow().getValue());
            vd.setSs(!vd.getSs());
        });
       getLifecycle().addObserver(new LifecycleObserver() {

      });
        vd.btn.setOnClickListener(v -> {
            SkinPlugin.getInstance().load("LDM_skin2.skin");
            SkinPlugin.getInstance().notifySkinUpdate();
        });

        vd.btn2.setOnClickListener(v -> {

            ImageView iv=new ImageView(MainActivity.this);
            LinearLayout.LayoutParams param =new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    0, 1.0f
            );
            iv.setLayoutParams(param);
            vd.layout.addView(iv,0);
             dynamicAddView(new DynamicAttr.Builder(iv)
            .add(android.R.attr.src,R.drawable.banner)
            .build());
        });

    }

    @Override
    public void onSkinUpdate() {
        super.onSkinUpdate();
     //   vd.invalidateAll();
    }
}
