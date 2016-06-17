package wff.com.androidsummary.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import org.xutils.common.util.LogUtil;

/**
 * Created by wufeifei on 2016/6/17.
 */
public class MyReceiver extends BroadcastReceiver {
    public static final String ACTION = "wff.reveiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION.equals(intent.getAction())) {
            Toast.makeText(context, "收到", Toast.LENGTH_SHORT).show();
        }
    }
}
