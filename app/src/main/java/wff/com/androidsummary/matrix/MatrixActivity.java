package wff.com.androidsummary.matrix;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import wff.com.androidsummary.R;
import wff.com.androidsummary.xutil3tools.BaseActivity;

/**
 * Created by wufeifei on 2016/6/14.
 */
@ContentView(R.layout.activity_matrix_layout)
public class MatrixActivity extends BaseActivity {
    @ViewInject(R.id.src_image)
    private ImageView src_image;
    @ViewInject(R.id.new_image)
    private ImageView new_image;
    @ViewInject(R.id.translate)
    private Button translateBtn;
    @ViewInject(R.id.rotate)
    private Button rotateBtn;
    @ViewInject(R.id.scale)
    private Button scaleBtn;
    @ViewInject(R.id.skew)
    private Button skewBtn;

    @Event(value = {R.id.translate, R.id.rotate, R.id.scale, R.id.skew}, type = View.OnClickListener.class)
    private void dealClick(View view) {
        switch (view.getId()) {
            case R.id.translate:
                translate(50, 50);
                break;
            case R.id.rotate:
                rorate(30);
                break;
            case R.id.scale:
                scale(2, 2);
                break;
            case R.id.skew:
                skew(0.7f, 0.9f);
                break;
        }
    }

    private void scale(float x, float y) {
        Bitmap src = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Bitmap newBitmap = Bitmap.createBitmap((int) (src.getWidth() * x), (int) (src.getHeight() * y), src.getConfig());
        Canvas canvas = new Canvas(newBitmap);
        Matrix matrix = new Matrix();
        matrix.setScale(x, y);
        canvas.drawBitmap(src, matrix, new Paint());
        new_image.setImageBitmap(newBitmap);
        src.recycle();
    }

    private void rorate(float degree) {
        Bitmap src = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Bitmap newBitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        Canvas canvas = new Canvas(newBitmap);
        Matrix matrix = new Matrix();
        matrix.setRotate(degree);
        canvas.drawBitmap(src, matrix, new Paint());
        new_image.setImageBitmap(newBitmap);
        src.recycle();
    }

    private void translate(float dx, float dy) {
        Bitmap src = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Bitmap newBitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        Canvas canvas = new Canvas(newBitmap);
        Matrix matrix = new Matrix();
        matrix.setTranslate(dx, dy);
        canvas.drawBitmap(src, matrix, new Paint());
        new_image.setImageBitmap(newBitmap);
        src.recycle();
    }

    private void skew(float x, float y) {
        Bitmap src = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Bitmap newBitmap = Bitmap.createBitmap(src.getWidth() + (int) (src.getWidth() * x), src.getHeight() + (int) (src.getHeight() * y), src.getConfig());
        Canvas canvas = new Canvas(newBitmap);
        Matrix matrix = new Matrix();
        matrix.setSkew(x, y);
        canvas.drawBitmap(src, matrix, new Paint());
        new_image.setImageBitmap(newBitmap);
        src.recycle();
    }
}
