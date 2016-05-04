package wff.com.androidsummary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import wff.com.androidsummary.R;

/**
 * Created by wufeifei on 2016/5/4.
 */
public class PullListView extends ListView {
    private Context mContext;
    private LayoutInflater mInflater;
    private View mHeadView;
    private View mFootView;

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
        if (getHeaderViewsCount() > 0) {
            removeHeaderView(mHeadView);
        }
        if (getFooterViewsCount() > 0) {
            removeFooterView(mFootView);
        }
        addHeaderView(mHeadView);
        addFooterView(mFootView);
    }
}
