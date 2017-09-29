package com.jixiang.argo.lvzheng.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 线程池工具类， depend on Executors
 */
public class ThreadPoolUtils {
	 private volatile static ExecutorService executorPool = null;
	 
	 private static Object lock = new Object();
	 
	 public static ExecutorService getExecutorPool() {
		if (null == executorPool) {
			synchronized (lock) {
				if (null == executorPool) {
					executorPool = new ScheduledThreadPoolExecutor(10);
				}
			}
		}
		return executorPool;
	 }
}
