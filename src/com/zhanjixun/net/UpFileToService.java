package com.zhanjixun.net;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.json.JSONObject;

import com.zhanjixun.data.Constants;
import com.zhanjixun.domain2.BaseResult;
import com.zhanjixun.interfaces.OnDataReturnListener;
import com.zhanjixun.utils.LogUtils;
import com.zhanjixun.utils.MyGson;
import com.zhanjixun.utils.StringUtil;

import android.os.AsyncTask;
import android.util.Log;

public class UpFileToService extends AsyncTask<File, Integer, String> {

	private final static HttpClient httpClient = new HttpClient();
	private OnDataReturnListener dataReturnListener;
	private String Tasktag ;
	private static String result;
	private static File targetFile;
	private static BaseResult jsonResult = null;
	

	private static String param;
	private static String paramType;
	private static String targetURL;
	
	public UpFileToService(String Tasktag, OnDataReturnListener dataReturnListener) {
		this.dataReturnListener = dataReturnListener;
		this.Tasktag = Tasktag;
	}

	/**
	 * ' 上传文件
	 */
	@Override
	protected String doInBackground(File... params) {
		String result = "";
		for (File file : params) {
			if (null != file)
				result = sendHttpRequest(file);
		}
		return result;
	}

	/**
	 * 准备上传文件
	 */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@SuppressWarnings("static-access")
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		this.result = result;
		
		LogUtils.d(result);//TODO
		
		if (!StringUtil.isEmptyString(result)) {
			try {
				this.jsonResult = MyGson.getInstance().fromJson(result, BaseResult.class);
			} catch (Exception e) {
				this.jsonResult = Constants.JSON_ERROR;
				e.printStackTrace();
			}
		} else {
			this.jsonResult = Constants.SERVER_ERROR;
		}
		try {
			JSONObject jo = new JSONObject(result).getJSONObject("resultParm");
			Map<String, String> map = new HashMap<String, String>();
			Iterator<String> keys = jo.keys();
			
			while (keys.hasNext()) {
				String k = keys.next();
				map.put(k, jo.getString(k));
			}
			this.jsonResult.setResultParam(map);
		} catch (Exception e) {
			e.printStackTrace();
	} 
		if (dataReturnListener != null) {
			dataReturnListener.onDataReturn(Tasktag, jsonResult, result);
		}
	}

	/* 获取返回值 */
	public static String getResult() {
		return result;
	}
	
	/*放回一个BaseResult类型*/
	public static BaseResult getBaseResult() {
		return jsonResult;
	}
    
	/**
	 * 发上服务器的文件
	 * @param file
	 * @return
	 */
	private String sendHttpRequest(File file) {
		String string = null;
		PostMethod filePost = new PostMethod(targetURL);
		try {
			Part[] parts = { new FilePart("userFile.file", targetFile), new StringPart(paramType, param, "utf-8") };
			HttpMethodParams params = filePost.getParams();
			params.setContentCharset("utf-8");
			filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(50000);
			
			Log.v("filePost", filePost.toString());
			Log.v("param", param);
			Log.v("paramType", paramType);
			int status = httpClient.executeMethod(filePost);
			//TODO
			Log.v("status--->", status + "");
			
			InputStream in = filePost.getResponseBodyAsStream();
			byte[] readStream = readStream(in);
			string = new String(readStream);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			filePost.releaseConnection();
		}
		return string;
	}

	/**
	 * 读取流
	 * 
	 * @param inStream
	 * @return 字节数组
	 * @throws Exception
	 */
	private byte[] readStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}

	/**
	 * 上传文件
	 * 
	 * @param file
	 * @param url
	 * @return
	 */
	public static void upLoadFile( String TaskTag, OnDataReturnListener dataReturnListener,
			File file, String url, String Type, String params) {
		paramType = Type;
		param = params;
		targetURL = Constants.HOST + url;
		targetFile = file;
		if (0 != file.getTotalSpace()) {
			UpFileToService upFile = new UpFileToService(TaskTag, dataReturnListener);
			
			Log.v("fileSize", file.getTotalSpace() + "：：：：");
			Log.v("Path", file.getPath());
			
			upFile.execute(targetFile);
		} else {
			return ;
		}
	}
}
