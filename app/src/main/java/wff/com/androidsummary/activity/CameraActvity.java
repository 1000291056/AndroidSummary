package wff.com.androidsummary.activity;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import wff.com.androidsummary.R;
import wff.com.androidsummary.view.CameraSurfaceView;
import wff.com.androidsummary.xutil3tools.BaseActivity;

/**
 * Created by wufeifei on 2016/6/20.
 */
@ContentView(R.layout.activity_camera_layout)
public class CameraActvity extends BaseActivity {
    private Camera mCamera;
    @ViewInject(R.id.camera_preview)
    private FrameLayout cameraPreviewFrameLayout;
    private CameraSurfaceView mCameraSurfaceView;
    @ViewInject(R.id.picture)
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCameraSurfaceView = new CameraSurfaceView(this, getCamera());
        cameraPreviewFrameLayout.addView(mCameraSurfaceView);
    }

    @Event(value = {R.id.button_capture}, type = View.OnClickListener.class)
    private void dealClick(View view) {
        switch (view.getId()) {
            case R.id.button_capture:
                getPicture();
                break;
        }
    }

    /**
     * 判断设备是否有摄像头
     *
     * @return
     */
    private boolean hasCamera() {
        boolean has = false;
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            has = true;
        }
        return has;
    }

    public Camera getCamera() {
        if (hasCamera()) {
            if (mCamera == null) {
                return mCamera = Camera.open();
            }
            return mCamera;
        }
        return null;
    }

    private void getPicture() {
        mCamera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                if (data != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    mImageView.setImageBitmap(bitmap);
                }

            }
        });
    }
}
