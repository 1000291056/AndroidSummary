package wff.com.androidsummary.activity;

import android.app.Activity;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import wff.com.androidsummary.R;
import wff.com.androidsummary.adapter.TestPullListViewAdapter;
import wff.com.androidsummary.view.PullListView;

/**
 * Created by wufeifei on 2016/5/4.
 */
public class PullListViewActivity extends Activity {
    @BindView(R.id.listview)
    PullListView mListview;
    private TestPullListViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_listview_layout);
        ButterKnife.bind(this);
        mAdapter = new TestPullListViewAdapter(this);
        mListview.setAdapter(mAdapter);
    }
}
