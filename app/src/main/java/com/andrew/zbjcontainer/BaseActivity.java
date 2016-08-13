package com.andrew.zbjcontainer;


import android.app.Activity;
import android.os.Bundle;

import com.andrew.zbjcontainer.container.ZbjContainer;

public class BaseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ZbjContainer.getInstance().addActivity(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ZbjContainer.getInstance().removeActivity(this);
	}

}
