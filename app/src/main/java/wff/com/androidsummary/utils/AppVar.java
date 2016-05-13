package wff.com.androidsummary.utils;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by wufeifei on 2016/5/5.
 */
public class AppVar {
    private static AppVar instance;
    private int width;
    private int height;

    public static AppVar getInstance() {
        if (instance == null) {
            instance = new AppVar();
        }
        return instance;
    }

    public void init(Context context) {
        getScreenSize(context);
    }

    private void getScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
