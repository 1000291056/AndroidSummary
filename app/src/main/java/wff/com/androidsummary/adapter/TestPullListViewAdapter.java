package wff.com.androidsummary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import wff.com.androidsummary.R;

/**
 * Created by wufeifei on 2016/5/4.
 */
public class TestPullListViewAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mList = new ArrayList<>();

    public TestPullListViewAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        for (int i=0;i<5;i++){
            mList.add("aaaaa");
        }




    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_test_pull_listview, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mText.setText(mList.get(position));
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.text)
        TextView mText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
