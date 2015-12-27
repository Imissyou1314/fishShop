package com.zhanjixun.activity;

import java.io.File;

import com.zhanjixun.R;
import com.zhanjixun.base.BackActivity;
import com.zhanjixun.data.Constants;
import com.zhanjixun.data.IC;
import com.zhanjixun.data.LoadImage;
import com.zhanjixun.net.UpFileToService;
import com.zhanjixun.utils.StringUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class ChangeUserHeadImageActivity extends BackActivity implements OnClickListener{
	
	/*ͷ��*/
	private ImageView headImage;
	
	/*����*/
	private Button btnTakePhoto;
	
	/*���*/
	private Button btnPhotos;
	
	/*ͷ��Bitmap */ 
	private Bitmap head;
    
	/*���µĽ��*/
	private boolean result; 
	
	private final static String headImageName = Constants.user.getHeadImage();
	
	//���ػ���ͼƬ
	String imgURL = Constants.HOST + "/fishshop/" + headImageName;
	Bitmap bitmap = LoadImage.getInstance().getBitmapFromLruCache(
			imgURL + LoadImage.BLUR_KEY);
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_userimage);
		
		Log.v("handImageName====��", headImageName + " ::::" + imgURL);
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
        case R.id.btn_photo://���������ȡ��Ƭ   
            Intent intent1 = new Intent(Intent.ACTION_PICK, null);  
            intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");  
            startActivityForResult(intent1, 1);  
            break;  
        case R.id.btn_tabkephoto://�����������   
            Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  
            intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),  
                            "head.jpg")));  
            startActivityForResult(intent2, 2);//����ForResult��   
            break;  
        default:  
            break;  
        }  
	}
	
	
	/**
	 * ��ȡIntent ���ص�����
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        switch (requestCode) {  
        case 1:  
            if (resultCode == RESULT_OK) {  
                cropPhoto(data.getData());//�ü�ͼƬ   
            }  
            break;  
        case 2:  
            if (resultCode == RESULT_OK) {  
                File temp = new File(Environment.getExternalStorageDirectory()  
                        + "/head.jpg");  
                cropPhoto(Uri.fromFile(temp));//�ü�ͼƬ   
            }  
            break;  
        case 3:  
            if (data != null) {  
                Bundle extras = data.getExtras();  
                head = extras.getParcelable("data");  
                if(head!=null){  
                    /**  
                     * �ϴ�����������  
                     */  
                	File file = new File(Constants.CACHE_DIR + "/head.jpg");
                	Log.v("Image", file.toString());
                	result = UpFileToService.upLoadFile(file, "/fishshop/user_updateUserImg.action", "userId", Constants.user.getUserId());
                    UpFileToService.getResult();
                	Log.v("rsult", UpFileToService.getResult() + "Miss===>");
                	Log.v("result", result + "");
                	
                	setPicToView(head);//������SD����   
                	
                    headImage.setImageBitmap(head);//��ImageView��ʾ����   
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
     * @param mBitmap
     */
    private void setPicToView(Bitmap mBitmap) {  
         String sdStatus = Environment.getExternalStorageState();    
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // ���sd�Ƿ����     
               return;    
           }    
//        FileOutputStream b = null;  
        //ͼƬ��ַ
//        File file = new File(Constants.CACHE_DIR);  
//        file.mkdirs();// �����ļ���   
//        String fileName =Constants.CACHE_DIR + Constants.user.getHeadImage();//ͼƬ����   
        
        try {  
           // b = new FileOutputStream(fileName); 
            /*���뻺��*/
        	String key = StringUtil.MD5(imgURL);
    		Bitmap bitmap = LoadImage.getInstance().getLruCache().put(key, head);
    		//TODO ��Ҫ�����SD����
            LoadImage.getInstance().setImage( Constants.user.getHeadImage(), headImage);
           // mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// ������д���ļ�   
        } catch (Exception e) {  
            e.printStackTrace();  
//        } finally {  
//            try {  
                //�ر���   
//                b.flush();  
//                b.close();  
//            } catch (IOException e) {  
//                e.printStackTrace();  
//            }  
        }  
    }
}
