package wff.com.androidsummary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import wff.com.androidsummary.R;

/**
 * Created by wufeifei on 2016/5/4.
 */
public class PullListView extends ListView {
    private static final String TAG = PullListView.class.getName();
    private Context mContext;
    private LayoutInflater mInflater;
    private View mHeadView;
    private View mFootView;
    private int itemHeight;
    private int dividerHeight = 0;
    private boolean lastVisiable = true;//最后一个是否可见
    private boolean firstVisiable = true;//第一个是否可见
    private ScaleAnimation mScaleAnimation;
    private TranslateAnimation mTranslateAnimation;
    /**
     * 正在刷新
     */
    private static final int REFRSHING = 1;
    /**
     * headview隐藏
     */
    private static final int HIDENHEAD = 2;
    /**
     * footview隐藏
     */
    private static final int FOOTNHEAD = 2;
    /**
     * 向上滑动
     */
    private static final int UP = 1;
    /**
     * 向下滑
     */
    private static final int DOWN = 2;

    private int direct;//滑动方向
    private int lastX;
    private int lastY;

    public PullListView(Context context) {
        super(context);
        init(context);
    }

    public PullListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PullListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        mHeadView = mInflater.inflate(R.layout.list_head_view, null);
        mFootView = mInflater.inflate(R.layout.list_foot_view, null);
        mScaleAnimation = new ScaleAnimation(0, 1, 1, 1);
        mScaleAnimation.setDuration(3000);
        mScaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (getHeaderViewsCount() > 0) {
                    removeHeaderView(mHeadView);
                }
                if (getFooterViewsCount() > 0) {
                    removeFooterView(mFootView);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        if (getHeaderViewsCount() > 0) {
            removeHeaderView(mHeadView);
        }
        if (getFooterViewsCount() > 0) {
            removeFooterView(mFootView);
        }

//        addFooterView(mFootView);
//        mHeadView.setVisibility(GONE);
        this.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (!(scrollState == OnScrollListener.SCROLL_STATE_IDLE)) {//处于滑动状态
                    if (firstVisiable) {
//                        mHeadView.setVisibility(VISIBLE);
                        if (getHeaderViewsCount() > 0) {
                            removeHeaderView(mHeadView);
                        }
                        addHeaderView(mHeadView);
                        mHeadView.startAnimation(mScaleAnimation);
                    } else {
                        if (getHeaderViewsCount() > 0) {
                            removeHeaderView(mHeadView);
                        }
                    }
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastVisiable = (totalItemCount == firstVisibleItem + visibleItemCount);
                firstVisiable = (firstVisibleItem == 0);

                if (lastVisiable && (direct == UP)) {
                    if (getFooterViewsCount() > 0) {
                        removeFooterView(mFootView);
                    }
                    addFooterView(mFootView);
                    mFootView.startAnimation(mScaleAnimation);
                }else {
                    if (getFooterViewsCount() > 0) {
                        removeFooterView(mFootView);
                    }
                }
                Log.i(TAG, "滑动方向" + (direct == UP ? "向上" : "向下"));
//                Log.i(TAG, "最后一个是否可见" + lastVisiable);
//                Log.i(TAG, "第一个是否可见" + firstVisiable);

            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) ev.getX();
                lastY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                direct = 0;
                if (ev.getY() > lastY) {
                    direct = DOWN;
                } else {
                    direct = UP;
                }
                break;

        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        try {
            if (adapter.getCount()>0){
                View view = adapter.getView(0, null, this);
                if (view != null) {
                    view.measure(0, 0);
                    itemHeight = view.getMeasuredHeight();
                    dividerHeight = getDividerHeight();
                }
            }
        } catch (Exception e) {

        }

        super.setAdapter(adapter);
    }

    public interface OnRefreshListener {
        /**
         * 向下拉
         */
        public void pullDown();

        public void pullUp();
    }
}
