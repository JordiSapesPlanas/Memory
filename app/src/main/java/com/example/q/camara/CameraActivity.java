package com.example.q.camara;

import android.content.Intent;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.SurfaceHolder.Callback;

public class CameraActivity extends ActionBarActivity implements View.OnTouchListener{

    SurfaceView sView;
    SurfaceHolder surfaceHolder;
    int screenWidth, screenHeight;
    Camera camera;
    boolean isPreview = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();

        display.getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

//        Camera.CameraInfo cameraInfo = null;
//        Camera.getCameraInfo(1, cameraInfo);
//        System.out.println(cameraInfo.toString());

        sView = (SurfaceView) findViewById(R.id.surfaceView);
        sView.setOnTouchListener(this);

        surfaceHolder = sView.getHolder();

        surfaceHolder.addCallback(new Callback()
        {
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height)
            {
                System.out.println(format + ", "+ width + ", "+height);
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder)
            {
                initCamera();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder)
            {
                if (camera != null)
                {
                    if (isPreview) camera.stopPreview();
                    camera.release();
                    camera = null;
                }
            }
        });
    }

    private void initCamera()
    {
        if (!isPreview)
        {
            camera = Camera.open(0);
            camera.setDisplayOrientation(90);
        }
        if (camera != null && !isPreview)
        {
            try
            {
                Camera.Parameters parameters = camera.getParameters();
                parameters.setPreviewSize(screenWidth, screenHeight);
                parameters.setPreviewFpsRange(4, 10);
                parameters.setPictureFormat(ImageFormat.JPEG);
                parameters.set("jpeg-quality", 85);
                parameters.setPictureSize(screenWidth, screenHeight);

                camera.setPreviewDisplay(surfaceHolder);

                camera.startPreview();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            isPreview = true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if(event.getAction() == event.ACTION_DOWN){
            Intent intent = new Intent(this,SearchOpponentActivity.class);

            Bundle b = new Bundle();
            b.putFloat("getX",event.getX());
            b.putFloat("getY",event.getY());

            intent.putExtra("camera",b);

            startActivity(intent);
            this.finish();
        }

        return true;
    }
}
