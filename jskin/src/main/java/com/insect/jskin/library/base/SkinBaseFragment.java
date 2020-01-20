package com.insect.jskin.library.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.insect.jskin.library.framwork.IDynamicNewView;
import com.insect.jskin.library.support.base.DynamicAttr;
import com.insect.jskin.library.util.SkinInflaterFactory;


public class SkinBaseFragment extends Fragment implements IDynamicNewView {

    private IDynamicNewView mIDynamicNewView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mIDynamicNewView = (IDynamicNewView) context;
        } catch (ClassCastException e) {
            mIDynamicNewView = null;
        }
    }




    public final SkinInflaterFactory getSkinInflaterFactory() {
        if (getActivity() instanceof SkinBaseActivity) {
            return ((SkinBaseActivity) getActivity()).getInflaterFactory();
        }
        return null;
    }

    @Override
    public void onDestroyView() {
        removeAllView(getView());
        super.onDestroyView();
    }

    protected void removeAllView(View v) {
        if (v instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) v;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                removeAllView(viewGroup.getChildAt(i));
            }
            removeViewInSkinInflaterFactory(v);
        } else {
            removeViewInSkinInflaterFactory(v);
        }
    }

    private void removeViewInSkinInflaterFactory(View v) {
        if (getSkinInflaterFactory() != null) {
            getSkinInflaterFactory().removeSkinView(v);
        }
    }

    @Override
    public void dynamicAddView(View view, int attrId, int resourceId) {
          mIDynamicNewView.dynamicAddView(view, attrId, resourceId);
    }

    @Override
    public void dynamicAddView(DynamicAttr attrs) {
         mIDynamicNewView.dynamicAddView(attrs);
    }
}
