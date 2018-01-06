package com.shengchuang.core.sms;

import java.io.Serializable;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import com.shengchuang.core.util.TimeUtil;

/**
 * 短信验证码
 */
public class SmsVerify {

	private static final Map<String, TimeoutTask> TEL_CODE = new ConcurrentHashMap<>();
	private static final Timer TIMER = new Timer();
	private static final SmsVerify SMS_VERIFY = new SmsVerify();

	private static long keepTime = 10 * TimeUtil.MILLIS_PER_MINUTE;// 有效期

	private SmsVerify() {
	}

	public static void put(String tel, String code) {
		SMS_VERIFY.putCode(tel, code, keepTime);
	}

	public static void put(String tel, String code, long keepTime) {
		SMS_VERIFY.putCode(tel, code, keepTime);
	}

	public static String get(String tel) {
		if (TEL_CODE.get(tel) != null)
			return TEL_CODE.get(tel).code;
		return null;
	}

	public static void remove(String tel) {
		TEL_CODE.remove(tel).cancel();
	}

	private void putCode(String tel, String code, long keepTime) {
		TimeoutTask task = TEL_CODE.get(tel);
		if (task != null) {
			task.cancel();
		}
		task = new TimeoutTask(tel, code, keepTime);
		TEL_CODE.put(tel, task);
	}

	class TimeoutTask extends TimerTask implements Serializable {

		private static final long serialVersionUID = 1514620681111L;

		private final String tel;
		private final String code;

		TimeoutTask(String tel, String code, long keepTime) {
			this.tel = tel;
			this.code = code;
			TIMER.schedule(this, keepTime);
		}

		@Override
		public void run() {
			TEL_CODE.remove(tel).cancel();
		}

	}

}
