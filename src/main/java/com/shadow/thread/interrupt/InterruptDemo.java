package com.shadow.thread.interrupt;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class InterruptDemo {

	public static void main(String[] args) {
		test1();
	}
	
	private static void test() {
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					
					System.out.println(Thread.currentThread().getName() + " thread is running...");
					long time = System.currentTimeMillis();
					while(System.currentTimeMillis() - time < 1000) {
						//程序1秒
					}
					
				}
			}
		});
		
		t.start();
		//中断线程
		t.interrupt();
		System.out.println("中断线程");
	}
	
	private static void test1() {
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				System.out.println("start to sleep:" + System.currentTimeMillis());
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				System.out.println("weak up:" + System.currentTimeMillis());
			}
		});
		
		t.start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		//中断线程
		t.interrupt();
		
	}
	
	private static void test2() {
		
		Lock lock = new ReentrantLock();
		
		//CountDownLatch
		
	}
}




