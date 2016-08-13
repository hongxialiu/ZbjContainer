package com.andrew.zbjcontainer.container;

import java.util.ArrayList;
import android.app.Activity;

import com.andrew.zbjcontainer.orderbundle.OrderActivity;
import com.andrew.zbjcontainer.paybundle.PayActivity;

/**
 * 组件总配置
 * 
 * @author lichengan
 * 
 *         将来考虑通过API来动态获取
 */
public class ZbjBundleConfig {
	public static ArrayList<Class<? extends Activity>> bundleMap = new ArrayList<Class<? extends Activity>>();
	static {
		// 订单组件
		bundleMap.add(OrderActivity.class);
		// 支付组件
		bundleMap.add(PayActivity.class);
		// 聊天组件因为未完成 不加入到配置中
		// bundleMap.add(ChatActivity.class);
	}
}
