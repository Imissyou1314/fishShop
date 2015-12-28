package com.zhanjixun.activity;

import java.io.File;

import com.zhanjixun.R;
import com.zhanjixun.base.BackActivity;
import com.zhanjixun.data.Constants;
import com.zhanjixun.data.IC;
import com.zhanjixun.data.LoadImage;
import com.zhanjixun.net.UpFileToService;
import com.zhanjixun.utils.BitmapUtils;
import com.zhanjixun.utils.FileUtil;
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

public class ChangeUserHeadImageActivity extends BackActivity implements OnClickListener {

	/* ͷ�� */
	private ImageView headImage;

	/* ���� */
	private Button btnTakePhoto;

	/* ��� */
	private Button btnPhotos;

	/* ͷ��Bitmap */
	private Bitmap head;

	/* ���µĽ�� */
	private boolean result;

	private final static String headImageName = Constants.user.getHeadImage();

	// ���ػ���ͼƬ
	String imgURL = Constants.HOST + "/fishshop/" + headImageName;
	Bitmap bitmap = LoadImage.getInstance().getBitmapFromLruCache(imgURL + LoadImage.BLUR_KEY);

	private String cachePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_userimage);
		initViews();
	}

	/**
	 * ����ҳ��
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
	 * �ǵ�����һ����ʽ��ȡͼƬ
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_photo:// ���������ȡ��Ƭ
//			Intent intent1 = new Intent(Intent.ACTION_PICK, null);
			Intent intent = new Intent(  
                    Intent.ACTION_PICK,  
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			
//			intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
			startActivityForResult(intent, 1);
			break;
		case R.id.btn_tabkephoto:// �����������
			Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

			intent2.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
			startActivityForResult(intent2, 2);// ����ForResult��
			break;
		default:
			break;
		}
	}

	/**
	 * ��ȡIntent ���ص�����
	 */
	@SuppressWarnings("unused")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1:
			if (resultCode == RESULT_OK) {
				cropPhoto(data.getData());// �ü�ͼƬ
			}
			break;
		case 2:
			if (resultCode == RESULT_OK) {
				File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
				cropPhoto(Uri.fromFile(temp));// �ü�ͼƬ
			}
			break;
		case 3:
			if (data != null) {
				Bundle extras = data.getExtras();
				head = extras.getParcelable("data");
				if (head != null) {
					// ȡ��ͼƬ
					setPicToView(head);// ������SD����
					BitmapUtils.BitmapToFile(head, Constants.user.getHeadImage(), Constants.CACHE_DIR);
					File file = new File(cachePath);
					if (null != file) {
						result = UpFileToService.upLoadFile(file, "/fishshop/user_updateUserImg.action", "userId",
								Constants.user.getUserId());
						UpFileToService.getResult();
						headImage.setImageBitmap(head);// ��ImageView��ʾ����
					} else if (result) {
						Toast.makeText(this, "���³ɹ�....", Toast.LENGTH_LONG).show();
						this.deleteFile();//ɾ���ļ�
					} else {
						Toast.makeText(this, "file Not file", Toast.LENGTH_LONG).show();
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
	 * ����ϵͳ�Ĳü�
	 * 
	 * @param uri
	 */
	private void cropPhoto(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);
	}

	/**
	 * ����ҳ��ͼƬ������ͼƬ
	 * 
	 * @param mBitmap
	 */
	private void setPicToView(Bitmap mBitmap) {
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // ���sd�Ƿ����
			return;
		}
		try {
			LoadImage.getInstance().setLruCacheImage(imgURL + LoadImage.BLUR_KEY, mBitmap);
			String urlMD5KeyGlass = StringUtil.MD5(imgURL + LoadImage.BLUR_KEY);
			String urlMD5Key = StringUtil.MD5(imgURL);

			String glassPath = Constants.CACHE_DIR + "/" + urlMD5KeyGlass;
			cachePath = Constants.CACHE_DIR + "/" + urlMD5Key;
			FileUtil.storeBitmap(glassPath, mBitmap);
			FileUtil.storeBitmap(cachePath, mBitmap);
			// ���ػ���ͼƬ
			LoadImage.getInstance().setImage(Constants.user.getHeadImage(), headImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*ɾ���ļ�*/
	private void deleteFile() {
		File delfile = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
		if (null != delfile) {
			delfile.delete();
		}
	}
}
