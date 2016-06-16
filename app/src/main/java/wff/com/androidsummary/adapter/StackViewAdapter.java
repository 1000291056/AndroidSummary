package wff.com.androidsummary.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import wff.com.androidsummary.R;

/**
 * Created by wufeifei on 2016/5/16.
 */
public class StackViewAdapter extends BaseAdapter {
    private int[] colors = new int[]{Color.GREEN, Color.RED, Color.YELLOW, Color.BLUE};
    private Context mContext;

    public StackViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return colors.length;
    }

    @Override
    public Object getItem(int position) {
        return colors[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_stackview, null);
        view.findViewById(R.id.text).setBackgroundColor(colors[position]);
        return view;
    }
}
