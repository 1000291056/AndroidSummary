package wff.com.androidsummary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wufeifei on 2016/5/13.
 */
public class TestSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private  SurfaceHolder mSurfaceHolder;
    private Context mContext;
    private ExecutorService mService = Executors.newFixedThreadPool(Integer.MAX_VALUE);
    private int mTouchX;
    private int mTouchY;
    private BlockingQueue<Circle> mCircles = new LinkedBlockingQueue<>(100);

    public TestSurfaceView(Context context) {
        super(context);
        init(context);
    }

    public TestSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TestSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        this.mContext = context;
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);

    }

    @Override
    public void surfaceCreated(final SurfaceHolder holder) {
        mService.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Circle circle = mCircles.poll();
                    if (circle == null) {
                        continue;
                    }
                    LogUtil.i("获取到了一个圆");
//                    new Thread(new MyRunnable(circle)).start();
                    mService.execute(new MyRunnable(circle));
                }
            }
        });

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                try {
                    mCircles.put(new Circle(event.getX(), event.getY()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }
        return true;
    }

    class MyRunnable implements Runnable {
        private Circle mCircle;

        public MyRunnable(Circle circle) {
            mCircle = circle;
        }

        @Override
        public void run() {
            while (mCircle.canDraw) {
                try {
                    Canvas canvas = mSurfaceHolder.lockCanvas();
                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                    mCircle.draw(canvas);
                    mSurfaceHolder.unlockCanvasAndPost(canvas);
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Circle {
        private float x;
        private float y;
        private int alpha = 255;
        private int radius = 100;
        private Lock mLock = new ReentrantLock();
        private boolean canDraw = true;

        public Circle(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public int getRadius() {
            return radius;
        }

        public void setRadius(int radius) {
            this.radius = radius;
        }

        public void draw(Canvas canvas) {
            mLock.lock();
            try {
                Paint paint = new Paint();
                paint.setColor(Color.BLUE);
                paint.setStyle(Paint.Style.STROKE);
                paint.setAlpha(alpha);
                canvas.drawCircle(x, y, radius, paint);
                changeCircleState();
            } finally {
                mLock.unlock();
            }
        }

        /**
         * 改变圆的属性
         */
        private void changeCircleState() {
            radius+=10;
            alpha -= 10;
            if (alpha <= 0) {
                canDraw = false;
            }
        }

        private void init() {
            x = 0;
            y = 0;
            alpha = 255;
            canDraw = true;
        }
    }
}
