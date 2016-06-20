package wff.com.androidsummary.activity;

import android.hardware.Camera;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import wff.com.androidsummary.R;
import wff.com.androidsummary.xutil3tools.BaseActivity;

/**
 * Created by wufeifei on 2016/6/16.
 * 录制视频
 */
@ContentView(R.layout.activity_media_layout)
public class CameraVideoActivity extends BaseActivity {
    @ViewInject(R.id.surfaceView)
    private SurfaceView mSurfaceView;
    private Camera mCamera;
    private MediaRecorder mRecorder;

    @Event(value = {R.id.take_photo, R.id.take_video, R.id.stop_take_video}, type = View.OnClickListener.class)
    private void dealClick(View view) {
        switch (view.getId()) {
            case R.id.take_photo:
                break;
            case R.id.take_video:
                take_video();
                break;
            case R.id.stop_take_video:
                stopVideo();
                break;
        }
    }

    private void take_video() {
        mCamera = android.hardware.Camera.open();
        mRecorder = new MediaRecorder();
        try {
            mCamera.setPreviewDisplay(mSurfaceView.getHolder());
            mCamera.startPreview();
            mCamera.unlock();
            mRecorder.setCamera(mCamera);
            mRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
            mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
            mRecorder.setOutputFile(getOutputMediaFile(MEDIA_TYPE_VIDEO).toString());
            mRecorder.setPreviewDisplay(mSurfaceView.getHolder().getSurface());
            mRecorder.prepare();
            mRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    private void stopVideo() {
        try {
            mRecorder.reset();
            mRecorder.release();
            mRecorder = null;
            mCamera.lock();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    /**
     * Create a file Uri for saving an image or video
     */
    private Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Create a File for saving an image or video
     */
    private File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
}
