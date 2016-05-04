package wff.com.androidsummary.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by wufeifei on 2016/1/22.
 */
public class OilGauge extends View {
    private static int BLACKCIRCLEX;
    private static int BLACKCIRCLEY;
    private Paint mPaint = new Paint();
    private int degree = 0;//指正旋转的角度
    private float lastX = 0;
    private float lastY = 0;
    private float currentX = 0;
    private float currentY = 0;
    private static float pointX = 0;
    private static float pointY = 0;
    private int progress = 0;
    private boolean clear = false;
    private static final int MAX = 720;
    private Path path = new Path();
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            postInvalidate();
        }
    };

    public OilGauge(Context context) {
        super(context);
    }

    public OilGauge(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OilGauge(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.translate(BLACKCIRCLEX,BLACKCIRCLEY);

        BLACKCIRCLEX = getWidth() / 2;
        BLACKCIRCLEY = getHeight() / 2;
        mPaint.setAntiAlias(true);

        canvas.translate(BLACKCIRCLEX, BLACKCIRCLEY - 50);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.GREEN);
        canvas.drawArc(new RectF(-getWidth() / 2 + 10, -getWidth() / 2 + 10, getWidth() / 2 - 10, getWidth() / 2 - 10), -20, -40, false, mPaint);
        mPaint.setColor(Color.BLACK);
        canvas.drawArc(new RectF(-getWidth() / 2 + 10, -getWidth() / 2 + 10, getWidth() / 2 - 10, getWidth() / 2 - 10), -60, -100, false, mPaint);
//        canvas.drawLine(0, -BLACKCIRCLEY + 200, 0, -BLACKCIRCLEY + 145, mPaint);
        mPaint.setStrokeWidth(5);
        canvas.save();
        canvas.rotate(-70);
        canvas.drawLine(0, -BLACKCIRCLEY + 200, 0, -getWidth() / 2 + 10, mPaint);
        for (int i = 0; i < 14; i++) {
            canvas.rotate(10);
            canvas.drawLine(0, -BLACKCIRCLEY + 200, 0, -getWidth() / 2 + 10, mPaint);
        }
        canvas.restore();
        canvas.rotate(degree, 0, 0);
        mPaint.setStyle(Paint.Style.FILL);
        Log.i("degree", degree + "");
        path.moveTo(-10, 50);
        path.lineTo(0, -BLACKCIRCLEY + 150);
        path.lineTo((10), 50);
        path.close();
        mPaint.setColor(Color.RED);

        canvas.drawPath(path, mPaint);
        canvas.rotate(-degree, 0, 0);
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(0, 0, 25, mPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                currentX = event.getX();
                currentY = event.getY();
                if (getDegree(currentX, currentY)) {
                    mHandler.sendEmptyMessage(0);
                }


                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    private boolean getDegree(float x, float y) {
        //求xy相对于旋转中心的坐标
        float xTemp = x - getWidth() / 2;
        float yTemp = y + 50 - getHeight() / 2;
        if ((xTemp <= 0 && yTemp > 0)) {
            degree = -70;
            return true;
        } else if ((xTemp >= 0 && yTemp > 0)) {
            degree = 70;
            return true
                    ;
        }
        int degreeTemp = (int) Math.toDegrees(Math.atan((-xTemp) / (yTemp)));

        if (degreeTemp >= -70 && degreeTemp <= 70) {
            degree = degreeTemp;
            return true;
        }

        return false;

    }
}
