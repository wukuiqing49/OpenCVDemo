package com.wu.ov

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.wu.ov.databinding.ActivityDemoBinding
import org.opencv.android.InstallCallbackInterface
import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc


/**
 *
 * 作者:吴奎庆
 *
 * 时间:2020/11/15
 *
 * 用途:
 */


class DemoActivity : AppCompatActivity(), LoaderCallbackInterface {

    var binding: ActivityDemoBinding? = null
    var isContent = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        System.loadLibrary("native-lib")
        binding = DataBindingUtil.setContentView<ActivityDemoBinding>(this, R.layout.activity_demo)
        binding!!.ivContent.setImageResource( R.drawable.iv_demo)
        binding!!.ivContent2.setImageResource( R.drawable.iv_demo)

        binding!!.btChange.setOnClickListener {
            if (isContent){
                var bm = BitmapFactory.decodeResource(resources, R.drawable.iv_demo)
                var mat = Mat()
                var grymat = Mat()

                Utils.bitmapToMat(bm, mat)

                Imgproc.cvtColor(mat, grymat, Imgproc.COLOR_RGB2GRAY)
                Utils.matToBitmap(grymat, bm)

                binding!!.ivContent2.setImageBitmap(bm)
            }

        }

    }


    override fun onResume() {
        super.onResume()
        if (!OpenCVLoader.initDebug()) {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, this)
        } else {
            this.onManagerConnected(LoaderCallbackInterface.SUCCESS)
        }
    }


    override fun onPackageInstall(operation: Int, callback: InstallCallbackInterface?) {


    }

    override fun onManagerConnected(status: Int) {
        if (status == 0) {
            isContent = true
        }
    }

    //获得Canny边缘
    external fun getEdge(bitmap: Any?)
}