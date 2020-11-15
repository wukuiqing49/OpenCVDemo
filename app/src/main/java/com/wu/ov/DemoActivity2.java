package com.wu.ov;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.wu.ov.databinding.ActivityDemo2Binding;

import org.opencv.android.InstallCallbackInterface;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * 作者:吴奎庆
 * <p>
 * 时间:2020/11/15
 * <p>
 * 用途:
 */


class DemoActivity2 extends AppCompatActivity implements LoaderCallbackInterface {
    static {
        System.loadLibrary("native-lib");
    }

    boolean isContent;

    //获得Canny边缘
    native void getEdge(Object bitmap);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDemo2Binding binding = DataBindingUtil.setContentView(this, R.layout.activity_demo_2);
        binding.ivContent.setImageResource(R.drawable.iv_demo);
        binding.ivContent2.setImageResource(R.drawable.iv_demo);

        binding.btChange.setOnClickListener(view -> {
            if (isContent) {
                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.iv_demo);
                Mat mat = new Mat();
                Mat grymat = new Mat();

                Utils.bitmapToMat(bm, mat);

                Imgproc.cvtColor(mat, grymat, Imgproc.COLOR_RGB2GRAY);
                Utils.matToBitmap(grymat, bm);

                binding.ivContent2.setImageBitmap(bm);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, this);
        } else {
            this.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    @Override
    public void onManagerConnected(int status) {
        isContent = true;
    }

    @Override
    public void onPackageInstall(int operation, InstallCallbackInterface callback) {

    }


}
