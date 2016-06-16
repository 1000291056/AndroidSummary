package wff.com.androidsummary.matrix;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import wff.com.androidsummary.R;
import wff.com.androidsummary.xutil3tools.BaseActivity;

/**
 * Created by wufeifei on 2016/6/14.
 */
@ContentView(R.layout.activity_matrix_layout)
public class MatrixActivity extends BaseActivity implements View.OnTouchListener {
    @ViewInject(R.id.image)
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageView.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
        }
        return true;
    }
    private void translate(){
        Matrix matrix=new Matrix();
        matrix.setTranslate(100,100);
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        Canvas canvas=new Canvas(bitmap);
//        canvas.drawBitmap(bitmap,);
    }
}
