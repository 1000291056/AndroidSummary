package wff.com.androidsummary;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by wufeifei on 2016/4/19.
 */
public class MyService extends Service {
    private static final String TAG = MyService.class.getName();
    private Goods goods=new Goods();
    private MyBinder binder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    class MyBinder extends IMyAidlInterface.Stub {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void setGoods(Goods goods) throws RemoteException {
            if (goods == null) {
                return;
            }
            MyService.this.goods = goods;
            Log.i(TAG, goods.toString());
        }

        @Override
        public int getCount() throws RemoteException {
            return goods.getCount();
        }

        @Override
        public IBinder asBinder() {
            return null;
        }
    }
}
