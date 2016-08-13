package com.andrew.zbjcontainer.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.andrew.zbjcontainer.imbundle.ChatActivity;
import com.andrew.zbjcontainer.paybundle.PayActivity;

public class ZbjContainer {
	private static ZbjContainer instance;
	private HashMap<String, ArrayList<ZbjBundle>> bundlePool = new HashMap<String, ArrayList<ZbjBundle>>(
			0);// 消息订阅映射池 key 界面名称(包名.类目)，value 订阅该界面的组件列表
	private List<Activity> activityList = new LinkedList<Activity>();// 界面容器

	private ZbjContainer() {
	}

	public synchronized static ZbjContainer getInstance() {
		if (instance == null) {
			instance = new ZbjContainer();
		}
		return instance;
	}

	/** -------------组件跳转方法-------------- **/
	/**
	 * 去支付了
	 * 
	 * @param id
	 *            前一个界面为支付动作自定义的id
	 * @param zbjBundle
	 *            前一个界面自己
	 * @param bundle
	 *            支付需要带的前一个界面传过来的参数
	 */
	public void goPay(ZbjBundle zbjBundle) {
		go(zbjBundle, PayActivity.class);
	}

	/**
	 * 去聊天了
	 * 
	 * @param id
	 *            前一个界面为支付动作自定义的id
	 * @param zbjBundle
	 *            前一个界面自己
	 * @param bundle
	 *            支付需要带的前一个界面传过来的参数
	 */
	public void goChat(ZbjBundle zbjBundle) {
		go(zbjBundle, ChatActivity.class);
	}

	// 统一跳转方法
	private void go(ZbjBundle zbjBundle, Class<? extends Activity> c) {
		if (!ZbjBundleConfig.bundleMap.contains(c)) {// 没有注册到总配置文件的组件不允许使用
			Toast.makeText(zbjBundle.getUi(), "很抱歉，该功能正常升级维护中，请先去网站使用。",
					Toast.LENGTH_SHORT).show();
			return;
		}
		Intent intent = new Intent(zbjBundle.getUi(), c);
		if (zbjBundle.getData() != null) {
			// 每个组件自己规定一套数据规则，前一个组件必须按照规则将参数写到data里
			intent.putExtras(zbjBundle.getData());
		}
		// 跳转
		zbjBundle.getUi().startActivity(intent);
		// 为两个组件搭建一条消息通讯的桥梁
		addBridge(zbjBundle, c);
	}

	// 前一个组件和即将跳转的界面组成一个多对1的MAP映射，界面处理完将发送消息回来，映射中的多方将会收到通知
	private void addBridge(ZbjBundle zbjFromBundle, Class<? extends Activity> c) {
		String key = c.getCanonicalName();// 跳转的界面的包名+类名座位key
		if (bundlePool.containsKey(key)) {// 如果该界面已有其他组件也在监听，则将当前组件也加入到监听的列表里
			ArrayList<ZbjBundle> bundleList = bundlePool.get(key);
			bundleList.add(zbjFromBundle);
		} else {// 如果该界面没有其他监听组件，则创建监听列表将当前组件也加入
			ArrayList<ZbjBundle> bundleList = new ArrayList<ZbjBundle>(0);
			bundleList.add(zbjFromBundle);
			bundlePool.put(key, bundleList);
		}
	}

	// 新界面处理完业务后如果需要告知上一组件是否成功则需要主动调用该方法，容器会帮忙把消息回传给对该界面业务感兴趣的所有组件
	public void onComplete(int result, ZbjBundle zbjBundle) {
		String key = zbjBundle.getUi().getClass().getCanonicalName();
		if (bundlePool.containsKey(key)) {
			ArrayList<ZbjBundle> bundleList = bundlePool.get(key);
			int size = bundleList.size();
			for (int i = 0; i < size; i++) {
				ZbjBundle zbjBundle2 = bundleList.get(i);
				zbjBundle2.getCallback()
						.onComplete(result, zbjBundle.getData());
			}
			bundlePool.remove(key);
		}
	}

	/** -------------界面生命周期管理 -------------- **/
	// 添加Activity到容器中
	public void addActivity(Activity activity) {
		Log.d("-----addActivity---", activity.getClass().getSimpleName());
		activityList.add(activity);
	}

	// Activity移除
	public void removeActivity(Activity activity) {
		Log.d("-----removeActivity---", activity.getClass().getSimpleName());
		activityList.remove(activity);
		// 界面处理完业务后如果不需要告知对该界面业务感兴趣的所有组件，则该界面关闭时移除所有监听该界面组件的映射
		String key = activity.getClass().getCanonicalName();
		if (bundlePool.containsKey(key)) {
			bundlePool.remove(key);
		}
	}

	// 根据名字关闭Activity
	public void finishActivity(String activityName) {
		int size = activityList.size();
		for (int i = 0; i < size; i++) {
			Activity activity = activityList.get(i);
			if (activity.getClass().getSimpleName().equals(activityName)) {
				Log.d("-----removeActivity---", activityName);
				removeActivity(activity);
				activity.finish();
				return;
			}
		}
	}

}
