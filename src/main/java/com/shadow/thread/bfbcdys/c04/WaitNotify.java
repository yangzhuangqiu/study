package com.shadow.thread.bfbcdys.c04;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 
等待/通知机制，是指一个线程A执行了对象O的wait()方法进入等待状态，而另一个线程B
执行了对象O的notify()或者notifyAll()方法，线程A收到通知后从对象O的wait()方法返回，进而
执行后续操作。上述两个线程通过对象O来完成交互，而对象上的wait()和notify/notifyAll()的
关系就如同开关信号一样，用来完成等待方和通知方之间的交互工作。

1）使用wait()、notify()和notifyAll()时需要先对调用对象加锁。
2）调用wait()方法后，线程状态由RUNNING变为WAITING，并将当前线程放置到对象的
等待队列。
3）notify()或notifyAll()方法调用后，等待线程依旧不会从wait()返回，需要调用notify()或
notifAll()的线程释放锁之后，等待线程才有机会从wait()返回。

 *
 */
public class WaitNotify {
	//static boolean flag = true;
	static Object lock = new Object();

	public static void main(String[] args) throws Exception {
		Thread waitThread = new Thread(new Wait(), "WaitThread");
		waitThread.start();
		TimeUnit.SECONDS.sleep(1);
		Thread notifyThread = new Thread(new Notify(), "NotifyThread");
		notifyThread.start();
	}

	static class Wait implements Runnable {
		public void run() {
			// 加锁，拥有lock的Monitor
			synchronized (lock) {
				// 当条件不满足时，继续wait，同时释放了lock的锁
				try {
					System.out.println(Thread.currentThread() + " flag is true. wait@ "
							+ new SimpleDateFormat("HH:mm:ss").format(new Date()));
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// 条件满足时，完成工作
				System.out.println(Thread.currentThread() + " flag is false. running@ "
						+ new SimpleDateFormat("HH:mm:ss").format(new Date()));
			}
		}
	}

	static class Notify implements Runnable {
		public void run() {
			// 加锁，拥有lock的Monitor
			synchronized (lock) {
				// 获取lock的锁，然后进行通知，通知时不会释放lock的锁，
				// 直到当前线程释放了lock后，WaitThread才能从wait方法中返回
				System.out.println(Thread.currentThread() + " hold lock. notify @ "
						+ new SimpleDateFormat("HH:mm:ss").format(new Date()));
				lock.notifyAll();
				SleepUtils.second(5);
			}
			// 再次加锁
			synchronized (lock) {
				System.out.println(Thread.currentThread() + " hold lock again. sleep@ "
						+ new SimpleDateFormat("HH:mm:ss").format(new Date()));
				SleepUtils.second(5);
			}
		}
	}
}
