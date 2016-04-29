package wff.com.androidsummary;

import android.os.AsyncTask;

/**
 * Created by wufeifei on 2016/4/26.
 */
public class MyAsycTask extends AsyncTask<String, Integer, String> {
    public MyAsycTask() {
        super();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected String doInBackground(String... params) {
        publishProgress(1);
        return null;
    }
}
