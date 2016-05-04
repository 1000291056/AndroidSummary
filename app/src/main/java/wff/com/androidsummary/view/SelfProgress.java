package wff.com.androidsummary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import wff.com.androidsummary.R;

/**
 * Created by wufeifei on 2016/5/3.
 */
public class SelfProgress extends View {
    private static final String TAG = SelfProgress.class.getName();
    private Context mContext;
    private Paint mPaint;//画笔
    private int mProgress;//进度
    private int defaultTextSize = 50;//进度字体大小
    private static int mRadius;//圆形进度条半径
    private static final int PADDING = 100;//进度条里边框距离;
    private static final int STOKRWIDTH = 10;//进度条里边框距离;
    private int mWidth = 0;
    private int mHeight = 0;
    public static final int MAXPROGRESS = 100;//最大进度
    private int type = 0;//进度条类型

    public SelfProgress(Context context) {
        super(context);
        init(context, null);
    }

    public SelfProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SelfProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //将canvas移动到整个空间中心,画圆
        mWidth = getWidth();
        mHeight = getHeight();
        mRadius = (mWidth / 2 > mHeight / 2 ? mHeight / 2 : mWidth / 2) - PADDING;
        canvas.translate(mWidth / 2, mHeight / 2);
        //第一步，画灰色圆
        mPaint.setColor(mContext.getResources().getColor(R.color.gray));
        mPaint.setStrokeWidth(STOKRWIDTH);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(0, 0, mRadius, mPaint);
        //第2步画进度
        Shader shader = new SweepGradient(0, mRadius, new int[]{Color.GREEN, Color.YELLOW, Color.RED}, null);
        int angel = mProgress * 360 / 100;
        Log.i(TAG, String.valueOf(angel));
        mPaint.setShader(shader);
        canvas.drawArc(new RectF(-mWidth / 2 + PADDING, -mWidth / 2 + PADDING, mWidth / 2 - PADDING, mWidth / 2 - PADDING), -90, angel, false, mPaint);
        //显示进度

        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.FILL);
        String proStr = String.valueOf(mProgress * 1.0) + "%";
        float strWidth = mPaint.measureText(proStr);
        canvas.drawText(proStr, -strWidth / 2, 0, mPaint);

    }

    /**
     * 初始化参数
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        mPaint = new Paint();
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SelfProgress);
            int textSize = a.getInt(R.styleable.SelfProgress_SelfProgress_textsize, defaultTextSize);
            Log.i(TAG, "进度条字体大小" + textSize);
            mPaint.setTextSize(textSize);
            type = a.getInt(R.styleable.SelfProgress_SelfProgress_type, 0);
            Log.i(TAG, "进度条类型" + type);
            a.recycle();

        }


    }

    public int getProgress() {
        return mProgress;
    }

    public void setProgress(int progress) {
        if (progress > MAXPROGRESS) {
            return;
        }
        if (mProgress != progress) {
            this.mProgress = progress;
            postInvalidate();
        }


    }
}
