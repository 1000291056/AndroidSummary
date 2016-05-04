package wff.com.androidsummary;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.provider.UserDictionary;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.x;

import java.security.cert.CertificateFactory;
import java.util.List;

import wff.com.androidsummary.activity.SelfProgressActivity;

public class MainActivity extends Activity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getName();
    private Button bindBtn;
    private Button unbindBtn;
    private EditText nameEtv;
    private EditText ageEtv;
    private EditText idEtv;
    private TextView queryResultTv;
    private IMyAidlInterface iMyAidlInterface;
    private Intent serviceIntent;
    private Goods goods = new Goods("apple", 100);
    private boolean bind = false;//是否已经绑定服务
    private Context context;
    private static final int GETCOUNT = 1;
    private DbDao dbDao;
    int lastX = 0;
    int lastY = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GETCOUNT:
                    try {
                        Log.i(TAG, iMyAidlInterface.getCount() + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    handler.sendEmptyMessageDelayed(GETCOUNT, 1000);
                    break;
            }
        }
    };
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bind = true;
            Toast.makeText(MainActivity.this, "成功连接", Toast.LENGTH_SHORT).show();
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            try {
                iMyAidlInterface.setGoods(goods);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(MainActivity.this, "失去连接", Toast.LENGTH_SHORT).show();
            bind = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        dbDao = new DbImpl(context);
        initView();
//        handProvider();
//        testManager();
//        MyAsycTask myAsycTask = new MyAsycTask();
//        myAsycTask.execute();
    }

    private void testHttps() {
//        CertificateFactory
    }

    private void testManager() {

        final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final View view = LayoutInflater.from(this).inflate(R.layout.window_layout, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "点我了", Toast.LENGTH_SHORT).show();
            }
        });

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        params.format = PixelFormat.RGBA_8888;
        params.gravity = Gravity.CENTER;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        int x = (int) event.getX();
                        int y = (int) event.getY();
                        if (Math.abs(x - lastX) > 25 || Math.abs(y - lastY) > 25) {
                            params.x = x;
                            params.y = y;
                            windowManager.updateViewLayout(view, params);
                            lastX = x;
                            lastY = y;
                        }

                        break;
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) event.getX();
                        lastY = (int) event.getY();
                        break;
                }
                return true;
            }
        });
        windowManager.addView(view, params);
        WifiManager wifiManager= (WifiManager) getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> scanResults= wifiManager.getScanResults();
//        windowManager.updateViewLayout(view, params);
    }

    private void initView() {
        bindBtn = (Button) findViewById(R.id.bind);
        unbindBtn = (Button) findViewById(R.id.unbind);
        nameEtv = (EditText) findViewById(R.id.name);
        ageEtv = (EditText) findViewById(R.id.age);
        idEtv = (EditText) findViewById(R.id.id);
        queryResultTv = (TextView) findViewById(R.id.queryresult);
        bindBtn.setOnClickListener(this);
        unbindBtn.setOnClickListener(this);
        findViewById(R.id.insert).setOnClickListener(this);
        findViewById(R.id.delete).setOnClickListener(this);
        findViewById(R.id.update).setOnClickListener(this);
        findViewById(R.id.query).setOnClickListener(this);
        findViewById(R.id.progress).setOnClickListener(this);
//        handler.sendEmptyMessageDelayed(GETCOUNT, 1000);
    }

    private void handProvider() {
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://wff.com.androidsummary.provider/student");
        resolver.query(uri, null, null, null, null);

    }

    private void bind() {
        if (serviceIntent == null) {
            serviceIntent = new Intent(MainActivity.this, MyService.class);
        }
        bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE);
    }

    private void unbind() {
        unbindService(connection);
        bind = false;
    }

    @Override
    public void onClick(View v) {
        String name = "";
        String age = "";
        String id = "";
        try {
            name = nameEtv.getText().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            age = ageEtv.getText().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            id = idEtv.getText().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (v.getId()) {
            case R.id.bind:
                if (bind) {
                    Toast.makeText(context, "已经绑定服务", Toast.LENGTH_SHORT).show();
                    return;
                }
                bind();
                break;
            case R.id.unbind:
                if (!bind) {
                    Toast.makeText(context, "没有绑定服务，不能解绑", Toast.LENGTH_SHORT).show();
                    return;
                }
                unbind();
                break;
            case R.id.insert:
                dbDao.insert(name, Integer.parseInt(age));
                break;
            case R.id.delete:
                dbDao.delete(Integer.parseInt(id));
                break;
            case R.id.update:
                Student s = new Student(name, Integer.parseInt(id), Integer.parseInt(age));
                dbDao.update(s);
                break;
            case R.id.query:
                Student student = dbDao.query(Integer.parseInt(id));
                if (student != null) {
                    queryResultTv.setText(student.toString());
                } else {
                    Toast.makeText(context, "没有查到数据", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.progress:
                Intent i=new Intent(context, SelfProgressActivity.class);
                startActivity(i);
                break;
        }
    }
}
