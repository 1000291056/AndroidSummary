package wff.com.androidsummary;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings;
import android.provider.UserDictionary;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.security.cert.CertificateFactory;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import wff.com.androidsummary.activity.CameraActvity;
import wff.com.androidsummary.activity.MediaActivity;
import wff.com.androidsummary.activity.PullListViewActivity;
import wff.com.androidsummary.activity.SelfProgressActivity;
import wff.com.androidsummary.activity.StackViewActivity;
import wff.com.androidsummary.activity.TestSurfaceViewActivity;
import wff.com.androidsummary.matrix.MatrixActivity;
import wff.com.androidsummary.receiver.MyReceiver;
import wff.com.androidsummary.xutil3tools.BaseActivity;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, View.OnTouchListener {
    private static final String TAG = MainActivity.class.getName();
    private Button bindBtn;
    private Button unbindBtn;
    @ViewInject(R.id.surface)
    private Button surfaceBtn;
    @ViewInject(R.id.stackview)
    private Button stackViewBtn;
    private EditText nameEtv;
    @ViewInject(R.id.gif)
    private ImageView gifImv;
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
    private GestureDetector mGestureDetector;
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
        context = this;
        dbDao = new DbImpl(context);
        initView();
//        if (Build.VERSION.SDK_INT >= 23) {//6.0
//            int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
//            if (permission != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 123);
//                return;
//            } else {
//                callDirectly("111111");
//
//            }
//        } else {
//            callDirectly("111111");
//        }
        /********************闹钟********************************/
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        PendingIntent pendingIntent=PendingIntent.getActivity();
//        alarmManager.set(AlarmManager.RTC_WAKEUP,10);
//        Title
//        handProvider();
//        testManager();
//        MotionEvent
        MyAsycTask myAsycTask = new MyAsycTask();
        myAsycTask.execute();
        Message message = new Message();
        mGestureDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                LogUtil.i("刚刚手指接触到触摸屏的那一刹那，就是触的那一下");
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                LogUtil.i("手指按在触摸屏上，它的时间范围在按下起效，在长按之前");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                LogUtil.i("手指离开触摸屏的那一刹那");
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                LogUtil.i("手指在触摸屏上滑动");
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                LogUtil.i("手指按在持续一段时间，并且没有松开");
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                LogUtil.i("手指在触摸屏上迅速移动，并松开的动作");
                return false;
            }
        });
        getWindow().getDecorView().setOnTouchListener(this);
        activityManager();
//        message.
//        ExecutorService
//        ThreadPoolExecutor
//        Executors
    }

    private void callDirectly(String mobile) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.CALL");
        intent.setData(Uri.parse("tel:" + mobile));
        startActivity(intent);
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
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> scanResults = wifiManager.getScanResults();
//        windowManager.updateViewLayout(view, params);
    }

    private void initView() {
        bindBtn = (Button) findViewById(R.id.bind);
        unbindBtn = (Button) findViewById(R.id.unbind);
        nameEtv = (EditText) findViewById(R.id.name);
        ageEtv = (EditText) findViewById(R.id.age);
        idEtv = (EditText) findViewById(R.id.id);
        queryResultTv = (TextView) findViewById(R.id.queryresult);
        bindBtn.setOnTouchListener(this);
        bindBtn.setOnClickListener(this);
        unbindBtn.setOnClickListener(this);
        findViewById(R.id.insert).setOnClickListener(this);
        findViewById(R.id.delete).setOnClickListener(this);
        findViewById(R.id.update).setOnClickListener(this);
        findViewById(R.id.query).setOnClickListener(this);
        findViewById(R.id.progress).setOnClickListener(this);
        findViewById(R.id.pullBtn).setOnClickListener(this);
        ImageOptions imageOptions = new ImageOptions.Builder()
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                // 默认自动适应大小
                // .setSize(...)
                .setIgnoreGif(false)
                // 如果使用本地文件url, 添加这个设置可以在本地文件更新后刷新立即生效.
                //.setUseMemCache(false)
                .setImageScaleType(ImageView.ScaleType.CENTER).build();
//        x.image().bind(gifImv, "file:///android_asset/a.gif", imageOptions);
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
        if (true) {
            return;
        }
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
                Intent i = new Intent(context, SelfProgressActivity.class);
                startActivity(i);
                break;
            case R.id.pullBtn:
                Intent iPull = new Intent(context, PullListViewActivity.class);
                startActivity(iPull);
                break;
            case R.id.media:
                Intent media = new Intent(context, MediaActivity.class);
                startActivity(media);
                break;
        }
    }

    @Event(value = {R.id.surface, R.id.stackview, R.id.media, R.id.matrix, R.id.notification,R.id.take_photo}, type = View.OnClickListener.class)
    private void dealWithClick(View view) {
        switch (view.getId()) {
            case R.id.surface:
                startActivity(new Intent(MainActivity.this, TestSurfaceViewActivity.class));
                break;
            case R.id.stackview:
                startActivity(new Intent(MainActivity.this, StackViewActivity.class));
                break;
            case R.id.media:
                Intent media = new Intent(context, MediaActivity.class);
                startActivity(media);
                break;
            case R.id.matrix:
                Intent matrix = new Intent(context, MatrixActivity.class);
                startActivity(matrix);
                break;
            case R.id.notification:
                notification();
                break;
            case R.id.take_photo:
                Intent cameraactvity = new Intent(context, CameraActvity.class);
                startActivity(cameraactvity);
                break;
            default:
                LogUtil.i("无效点击");
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mGestureDetector != null) {
            mGestureDetector.onTouchEvent(event);
        }
        return false;
    }

    private void notification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = null;
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("吴飞飞");
        builder.setContentText("是屌丝");
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setTicker("是是是是的");
        builder.setAutoCancel(true);
        Intent intent = new Intent(this, MyReceiver.class);
        intent.setAction(MyReceiver.ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification = builder.build();
        } else {
            notification = builder.getNotification();
        }
        notificationManager.notify(111, notification);

    }
    private void activityManager(){
        ActivityManager activityManager= (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            activityManager.getAppTasks();
//        }
       List<ActivityManager.RunningAppProcessInfo> appProcessInfos= activityManager.getRunningAppProcesses();
    }
}
