package wff.com.androidsummary;

import android.app.Application;
import android.content.Intent;

import org.xutils.x;

import wff.com.androidsummary.utils.AppVar;

/**
 * Created by wufeifei on 2016/4/28.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
        AppVar.getInstance().init(this);
    }
}
