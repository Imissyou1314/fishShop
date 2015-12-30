package com.zhanjixun.activity;

import java.io.File;

import com.zhanjixun.R;
import com.zhanjixun.base.BackActivity;
import com.zhanjixun.data.Constants;
import com.zhanjixun.data.IC;
import com.zhanjixun.data.LoadImage;
import com.zhanjixun.domain2.BaseResult;
import com.zhanjixun.domain2.User;
import com.zhanjixun.interfaces.OnDataReturnListener;
import com.zhanjixun.net.UpFileToService;
import com.zhanjixun.utils.BitmapUtils;
import com.zhanjixun.utils.FileUtil;
import com.zhanjixun.utils.MyGson;
import com.zhanjixun.utils.StringUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ChangeUserHeadImageActivity extends BackActivity implements OnClickListener ,OnDataReturnListener{

	/* 头像 */
	private ImageView headImage;

	/* 拍照 */
	private Button btnTakePhoto;

	/* 相册 */
	private Button btnPhotos;

	/* 头像Bitmap */
	private Bitmap head;

	private final static String headImageName = Constants.user.getHeadImage();

	// 加载缓存图片
	String imgURL = Constants.HOST + "/fishshop/" + headImageName;
	Bitmap bitmap = LoadImage.getInstance().getBitmapFromLruCache(imgURL + LoadImage.BLUR_KEY);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_userimage);
		initViews();
	}

	/**
	 * 加载页面
	 */
	private void initViews() {
		headImage = (ImageView) findViewById(R.id.Imageuser_headImage);
		btnTakePhoto = (Button) findViewById(R.id.btn_tabkephoto);
		btnPhotos = (Button) findViewById(R.id.btn_photo);

		IC.getInstance().setForegound(headImageName, headImage);
		btnTakePhoto.setOnClickListener(this);
		btnPhotos.setOnClickListener(this);
	}

	/**
	 * 是调用哪一个方式获取图片
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_photo:// 从相册里面取照片
			Intent intent = new Intent(Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, 1);
			break;
		case R.id.btn_tabkephoto:// 调用相机拍照
			Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent2.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
			startActivityForResult(intent2, 2);// 采用ForResult打开
			break;
		default:
			break;
		}
	}

	/**
	 * 获取Intent 返回的数据
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1:
			if (resultCode == RESULT_OK) {
				cropPhoto(data.getData());// 裁剪图片
			}
			break;
		case 2:
			if (resultCode == RESULT_OK) {
				File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
				cropPhoto(Uri.fromFile(temp));// 裁剪图片
			}
			break;
		case 3:
			if (data != null) {
				Bundle extras = data.getExtras();
				head = extras.getParcelable("data");
				if (head != null) {
					// 取出图片
					
					BitmapUtils.BitmapToFile(head, Constants.CACHE_DIR, "UserImage.jpg");
					File file = new File(Constants.CACHE_DIR+ "/"  + "UserImage.jpg");
					if (null != file) {
						UpFileToService.upLoadFile("UpFile", this, file, "/fishshop/user_updateUserImg.action", "userId",
								Constants.user.getUserId());
						headImage.setImageBitmap(head);// 用ImageView显示出来
					}
				}
			} 
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 调用系统的裁剪
	 * 
	 * @param uri
	 */
	private void cropPhoto(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);
	}

	/**
	 * 设置页面图片并保存图片
	 * 
	 * @param mBitmap
	 */
	private void setPicToView(Bitmap mBitmap, String Path) {
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
			return;
		}
		try {
			LoadImage.getInstance().setLruCacheImage(Path + LoadImage.BLUR_KEY, mBitmap);
			String urlMD5KeyGlass = StringUtil.MD5(Path + LoadImage.BLUR_KEY);
			String urlMD5Key = StringUtil.MD5(Path);

			String glassPath = Constants.CACHE_DIR + "/" + urlMD5KeyGlass;
			String cachePath = Constants.CACHE_DIR + "/" + urlMD5Key;
			FileUtil.storeBitmap(glassPath, mBitmap);
			FileUtil.storeBitmap(cachePath, mBitmap);
			// 加载缓存图片
			LoadImage.getInstance().setImage(Constants.user.getHeadImage(), headImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* 删除文件 */
	private void deleteFile() {
		File delfile = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
		if (null != delfile) {
			delfile.delete();
		}
	}
	
	/**
	 * 回调函数
	 */
	@Override
	public void onDataReturn(String taskTag, BaseResult result, String json) {
		if (result.getServiceResult()) {
			User user = MyGson.getInstance().fromJson(result.getResultParam()
					.get("user"), User.class);
			Constants.user.setHeadImage(user.getHeadImage());
			String Path = Constants.HOST + "/fishshop/" + user.getHeadImage();
			setPicToView(head, Path);// 保存在SD卡中
			this.deleteFile();// 删除文件
			Toast.makeText(this, "更新成功....", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "操作失败....", Toast.LENGTH_LONG).show();
		}
	}
}
