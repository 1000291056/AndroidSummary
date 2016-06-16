package wff.com.androidsummary.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.StackView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import wff.com.androidsummary.R;
import wff.com.androidsummary.adapter.StackViewAdapter;
import wff.com.androidsummary.xutil3tools.BaseActivity;

/**
 * Created by wufeifei on 2016/5/16.
 */
@ContentView(R.layout.activity_stackview)
public class StackViewActivity extends BaseActivity {
    @ViewInject(R.id.stackview)
    private StackView mStackView;
    private StackViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new StackViewAdapter(this);
        mStackView.setAdapter(mAdapter);
    }
}
