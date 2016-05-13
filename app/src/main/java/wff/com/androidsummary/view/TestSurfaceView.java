package wff.com.androidsummary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wufeifei on 2016/5/13.
 */
public class TestSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mSurfaceHolder;
    private Context mContext;
    private ExecutorService mService = Executors.newSingleThreadExecutor();
    private int mTouchX;
    private int mTouchY;
    private List<Circle> mCircles = new ArrayList<>();

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
                Canvas canvas = holder.lockCanvas();
                holder.unlockCanvasAndPost(canvas);
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
        return super.onTouchEvent(event);
    }

    class Circle {
        private int x;
        private int y;
        private int radius = 100;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getRadius() {
            return radius;
        }

        public void setRadius(int radius) {
            this.radius = radius;
        }
    }
}
