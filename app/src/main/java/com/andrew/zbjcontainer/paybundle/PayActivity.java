package com.andrew.zbjcontainer.paybundle;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.andrew.zbjcontainer.R;
import com.andrew.zbjcontainer.container.ZbjBundle;
import com.andrew.zbjcontainer.container.ZbjBundleResult;
import com.andrew.zbjcontainer.container.ZbjContainer;

public class PayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay);
		findViewById(R.id.ok).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 支付完了
				Toast.makeText(PayActivity.this, "支付组件表示支付成功，我要关闭了",
						Toast.LENGTH_SHORT).show();
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						finish();
						ZbjContainer.getInstance().onComplete(
								ZbjBundleResult.RESULT_OK,
								new ZbjBundle(PayActivity.this));
					}
				}, 1000);

			}
		});
		findViewById(R.id.ok1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 支付完了
				Toast.makeText(PayActivity.this, "支付组件表示支付失败，我要关闭了",
						Toast.LENGTH_SHORT).show();
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						finish();
						ZbjContainer.getInstance().onComplete(
								ZbjBundleResult.RESULT_FAILED,
								new ZbjBundle(PayActivity.this));
					}
				}, 1000);

			}
		});
	}
}