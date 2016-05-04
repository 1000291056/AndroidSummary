package wff.com.androidsummary.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wff.com.androidsummary.R;
import wff.com.androidsummary.view.SelfProgress;

/**
 * Created by wufeifei on 2016/5/3.
 */
public class SelfProgressActivity extends Activity {
    private static final int CHANGEPROGRESS = 1;//刷新进度条
    private static final int REFRESHTIME = 500;
    @BindView(R.id.selfProgress)
    SelfProgress mSelfProgress;
    private int mProgress = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CHANGEPROGRESS:
                    mSelfProgress.setProgress(mProgress);
                    if (mProgress >= SelfProgress.MAXPROGRESS) {
                        return;
                    }
                    mProgress++;
                    mHandler.sendEmptyMessageDelayed(1, REFRESHTIME);
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfprogress_layout);
        ButterKnife.bind(this);
        mHandler.sendEmptyMessageDelayed(1, REFRESHTIME);
    }

    @OnClick(R.id.selfProgress)
    public void onClick() {
    }
}
