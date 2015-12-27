package com.zhanjixun.net;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.zhanjixun.data.Constants;

import android.os.AsyncTask;
import android.util.Log;

public class UpFileToService extends AsyncTask<File, Integer, String> {

	private final static HttpClient httpClient = new HttpClient();
	private static String result;
	private static File targetFile;

	private static String param;
	private static String paramType;
	private static String targetURL;

	/**
	 * ' 上传文件
	 */
	@Override
	protected String doInBackground(File... params) {
		String result = "";
		for (File file : params) {
			if (null != file) {
				result = result + "<"+  sendHttpRequest(file) + ">";
			} else {
				result = result + "<null>";
			}
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
		//TODO
		Log.v("Misss===>>>", result +"");
	}

	/* 获取返回值 */
	public static String getResult() {
		return result;
	}

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
			if (status == HttpStatus.SC_OK) {
			} else {
			}
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
	@SuppressWarnings("static-access")
	public static boolean upLoadFile(File file, String url, String Type, String params) {
		paramType = Type;
		param = params;
		targetURL = Constants.HOST + url;
		targetFile = file;
		UpFileToService upFile = new UpFileToService();
		if (null != targetFile) {
			upFile.execute(targetFile);
		}
		return null != upFile.getResult() && "".equals(upFile.getResult());
	}
}
