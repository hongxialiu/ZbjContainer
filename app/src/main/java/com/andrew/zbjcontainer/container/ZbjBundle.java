package com.andrew.zbjcontainer.container;

import android.app.Activity;
import android.os.Bundle;

/**
 * 组件封装类
 * 
 * @author lichengan
 */
public class ZbjBundle {
	private Activity ui;// 界面
	private ZbjBundleCallback callback;// 界面回调
	private Bundle data;// 下一个组件需要的参数

	public ZbjBundle(Activity ui) {
		this.ui = ui;
	}

	public ZbjBundle(Activity ui, ZbjBundleCallback callback) {
		this.ui = ui;
		this.callback = callback;
	}

	public ZbjBundle(Activity ui, Bundle data) {
		this.ui = ui;
		this.data = data;
	}

	public ZbjBundle(Activity ui, Bundle data, ZbjBundleCallback callback) {
		this.ui = ui;
		this.callback = callback;
		this.data = data;
	}

	public Activity getUi() {
		return ui;
	}

	public void setUi(Activity ui) {
		this.ui = ui;
	}

	public ZbjBundleCallback getCallback() {
		return callback;
	}

	public void setCallback(ZbjBundleCallback callback) {
		this.callback = callback;
	}

	public Bundle getData() {
		return data;
	}

	public void setData(Bundle data) {
		this.data = data;
	}
}
