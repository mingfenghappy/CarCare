package com.lida.carcare.camera;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.lida.carcare.R;
import com.lida.carcare.retrofit.HttpMethods;
import com.midian.base.util.Func;

import java.io.File;

public class RectCameraActivity extends Activity implements OnCaptureCallback{

	private MaskSurfaceView surfaceview;
	private ImageView imageView;
//	拍照
	private Button btn_capture;
//	重拍
	private Button btn_recapture;
//	取消
	private Button btn_cancel;
//	确认
	private Button btn_ok;
//	拍照后得到的保存的文件路径
	private String filepath;

	public static Activity instant;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.activity_rect_camera);
		instant = this;
		this.surfaceview = (MaskSurfaceView) findViewById(R.id.surface_view);
		this.imageView = (ImageView) findViewById(R.id.image_view);
		btn_capture = (Button) findViewById(R.id.btn_capture);
		btn_recapture = (Button) findViewById(R.id.btn_recapture);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);

//		设置矩形区域大小
		this.surfaceview.setMaskSize(Func.Dp2Px(this,300), Func.Dp2Px(this,94));
		
//		拍照
		btn_capture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				btn_capture.setEnabled(false);
				btn_ok.setEnabled(true);
				btn_recapture.setEnabled(true);
                btn_cancel.setEnabled(false);
				CameraHelper.getInstance().setFlashlight(CameraHelper.Flashlight.OFF).tackPicture(RectCameraActivity.this);
			}
		});
		
//		重拍
		btn_recapture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				btn_capture.setEnabled(true);
				btn_ok.setEnabled(false);
				btn_recapture.setEnabled(false);
				imageView.setVisibility(View.GONE);
				surfaceview.setVisibility(View.VISIBLE);
				deleteFile();
				CameraHelper.getInstance().setFlashlight(CameraHelper.Flashlight.OFF).startPreview();
			}
		});
		
//		确认
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
//				FileTools.savePhotoToSDCard(Environment.getExternalStorageDirectory()+"/", "test.jpg", BitmapFactory
//						.decodeFile(filepath));
//				GetWordUtil.getCarNumber(RectCameraActivity.this,Environment.getExternalStorageDirectory()+"/test.jpg");
			}
		});
		
//		取消
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				deleteFile();
				RectCameraActivity.this.finish();
			}
		});

	}
	
	/**
	 * 删除图片文件呢
	 */
	private void deleteFile(){
		if(this.filepath==null || this.filepath.equals("")){
			return;
		}
		File f = new File(this.filepath);
		if(f.exists()){
			f.delete();
		}
	}

	@Override
	public void onCapture(boolean success, String filepath) {
		this.filepath = filepath;
		String message = "拍照成功";
		if(!success){
			message = "拍照失败";
			CameraHelper.getInstance().startPreview();
			this.imageView.setVisibility(View.GONE);
			this.surfaceview.setVisibility(View.VISIBLE);
		}else{
			this.imageView.setVisibility(View.VISIBLE);
			this.surfaceview.setVisibility(View.GONE);
			this.imageView.setImageBitmap(BitmapFactory.decodeFile(filepath));
			FileTools.savePhotoToSDCard(Environment.getExternalStorageDirectory()+"/", "test.jpg", BitmapFactory
					.decodeFile(filepath));
			GetWordUtil.getCarNumber(RectCameraActivity.this,Environment.getExternalStorageDirectory()+"/test.jpg",getIntent().getExtras().getString("flag"));
		}
//		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
}
