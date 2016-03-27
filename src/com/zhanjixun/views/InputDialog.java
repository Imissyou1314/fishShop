package com.zhanjixun.views;

import java.util.HashMap;
import java.util.Map;

import com.zhanjixun.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;

@SuppressLint("UseSparseArrays")
public class InputDialog {

	private ThemeDialog dialog;
	private Map<String, String> result = new HashMap<String, String>();

	public InputDialog(Context context) {
		dialog = new ThemeDialog(context);
		dialog.setDialogSize(0.85, 0.4);
		dialog.setPositiveButton("确定", null);
		dialog.setNegativeButton("取消", null);
	}

	public void setPositiveButton(String text,
			final OnPositiveButtonClickListener l) {
		OnClickListener onClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				l.onPositiveButtonClick(result);
			}
		};
		dialog.setPositiveButton(text, onClickListener);
	}

	public InputDialog addInputItem(String id, String hint, int inputType) {
		LinearLayout inflate = (LinearLayout) View.inflate(dialog.getContext(),
				R.layout.input_dialog, null);
		EditText t = (EditText) inflate.getChildAt(0);
		t.setHint(hint);
		t.setInputType(inputType);
		t.addTextChangedListener(new MyTextChangeListener(id));
		
		LinearLayout contentView = dialog.getContextView();
		contentView.setGravity(Gravity.CENTER_VERTICAL);
		contentView.addView(inflate);
		return this;
	}

	public InputDialog addInputItem(String id, String hint) {
		this.addInputItem(id, hint, InputType.TYPE_CLASS_TEXT);
		return this;
	}

	public String getText(String id) {
		return result.get(id);
	}

	public void show() {
		dialog.show();
	}

	public void dismiss() {
		dialog.dismiss();
	}

	private class MyTextChangeListener implements TextWatcher {

		private String id;

		public MyTextChangeListener(String id) {
			this.id = id;
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			result.put(id, s.toString());
		}
	}

	public interface OnPositiveButtonClickListener {
		/**
		 * Map：key：id value：Edittext context
		 * 
		 * @param result
		 */
		public void onPositiveButtonClick(Map<String, String> result);

	}

}
