package wff.com.androidsummary.xutil3tools;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.xutils.x;

/**
 * Created by wyouflf on 15/11/4.
 */
public class BaseFragment extends Fragment {
    private boolean injected = false;
    protected String mLable = "";
    protected boolean isNormal = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        isNormal = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isNormal = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
