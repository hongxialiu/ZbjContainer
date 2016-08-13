package com.andrew.zbjcontainer.orderbundle;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.andrew.zbjcontainer.BaseActivity;
import com.andrew.zbjcontainer.R;
import com.andrew.zbjcontainer.container.ZbjBundle;
import com.andrew.zbjcontainer.container.ZbjBundleCallback;
import com.andrew.zbjcontainer.container.ZbjBundleResult;
import com.andrew.zbjcontainer.container.ZbjContainer;


public class OrderActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		findViewById(R.id.ok).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 我要去支付了，顺便把支付需要的订单数据数据带过去
				Bundle data = new Bundle();
				data.putString("orderId", "1");
				//"orderId", "1" 这种参数规则由下一个组件的组件来制定，以文档的形式
				ZbjContainer.getInstance().goPay(
						new ZbjBundle(OrderActivity.this, data,
								new ZbjBundleCallback() {

									@Override
									public void onComplete(int result,
											Bundle bundle) {
										// 支付完了
										if (result == ZbjBundleResult.RESULT_OK) {
											Toast.makeText(
													OrderActivity.this,
													"虽然不知道哪个SB帮我支付的，但是支付成功了，哈哈哈",
													Toast.LENGTH_SHORT).show();
										} else {
											Toast.makeText(OrderActivity.this,
													"哪个SB帮我支付的，居然支付失败了，呜呜呜",
													Toast.LENGTH_SHORT).show();
										}

									}
								}));

			}
		});
		findViewById(R.id.ok1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 我要去聊天了，顺便把聊天的数据数据带过去
				Bundle data = new Bundle();
				data.putString("toUserid", "1111");
				ZbjContainer.getInstance().goChat(
						new ZbjBundle(OrderActivity.this, data));

			}
		});
	}
}
