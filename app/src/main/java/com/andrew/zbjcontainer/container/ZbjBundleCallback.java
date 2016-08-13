package com.andrew.zbjcontainer.container;

import android.os.Bundle;

/**
 * 组件界面回调
 * 
 * @author lichengan
 * 
 *         如果该界面需要对下一个组件业务成功失败有所操作则创建该回调扔给容器
 */
public abstract class ZbjBundleCallback {

	public abstract void onComplete(int result, Bundle bundle);

}