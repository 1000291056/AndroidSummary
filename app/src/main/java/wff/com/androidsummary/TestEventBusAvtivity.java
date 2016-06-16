package wff.com.androidsummary;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by wufeifei on 2016/5/23.
 */
public class TestEventBusAvtivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new FirstEvent("FirstEvent btn clicked"));
    }
    public void onEventMainThread(FirstEvent event) {

        String msg = "onEventMainThread收到了消息：" + event.getMsg();
        Log.d("harvic", msg);
//        tv.setText(msg);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
    public class FirstEvent {

        private String mMsg;
        public FirstEvent(String msg) {
            // TODO Auto-generated constructor stub
            mMsg = msg;
        }
        public String getMsg(){
            return mMsg;
        }
    }
}
