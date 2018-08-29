package com.shadow;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class TestMergeFile {

	@Test
	public void test() {

		String filePath = "E:\\home\\yangzhuangqiu\\desktop\\test\\polyvdownload";
		File file = new File(filePath);

		File[] list = file.listFiles();

		for (File f : list) {

			String name = f.getName().split("_")[0];
			if (32 == name.length()) {

				merge(f);
				break;

			}

		}

	}

	private void merge(File file) {

		File[] list = file.listFiles();

		File descFile = null;
		List<OrderFile> ofList = new ArrayList<>();

		for (File f : list) {

			String name = f.getName();
			if (name.endsWith(".json")) {
				descFile = f;
				continue;
			}

			if (name.endsWith(".ts")) {

				name = StringUtils.substringAfterLast(name, "_");
				name = StringUtils.substringBefore(name, ".");

				ofList.add(new OrderFile(Integer.parseInt(name), f));

			}

		}

		if (null != descFile) {
			System.out.println(descFile.getName());
		}

		Collections.sort(ofList, new Comparator<OrderFile>() {
			@Override
			public int compare(OrderFile o1, OrderFile o2) {
				return o1.getOrder() - o2.getOrder();
			}
		});

		// SequenceInputStream sis = new SequenceInputStream();

		File finalFile = new File("E:\\home\\yangzhuangqiu\\desktop\\test\\test.ts");

		ofList.forEach(of -> {

			try {
				byte[] data = FileUtils.readFileToByteArray(of.getFile());

				FileUtils.writeByteArrayToFile(finalFile, data, true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}

	@Test
	public void test1() {

		System.out.println("a24911d8d7f60d0e5229f1719447c0d8_1".length());
	}

	@Test
	public void test2() {

		Runnable r = new Runnable() {

			@Override
			public void run() {
				System.out.println("run");

			}
		};
		int i = 0;
		Runnable r2 = () -> {
			System.out.println(i);
		};
		Object o = r2;
		Predicate<Integer> p = a -> a > 3;

		List<OrderFile> list = new ArrayList<>();

		// Stream.iterate(0, f)
	}

	

	@Test
	public void test3() {

		try (Stream<String> lines = Files.lines(Paths.get("C:\\Users\\yangzhuangqiu\\Desktop\\keyword71.txt"),
				Charset.forName("utf8"))) {

			// String result = lines.collect(Collectors.joining(","));
			// System.out.println(result);
			int sum = lines.mapToInt(line -> Integer.parseInt(line)).sum();
			System.out.println(sum);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static class OrderFile {

		private int order;

		private File file;

		public OrderFile(int order, File file) {
			super();
			this.order = order;
			this.file = file;
		}

		public int getOrder() {
			return order;
		}

		public File getFile() {
			return file;
		}

	}
	
	public long measureSumPerf(Function<Long, Long> adder, long n) {
		long fastest = Long.MAX_VALUE;
		for (int i = 0; i < 10; i++) {
			long start = System.nanoTime();
			long sum = adder.apply(n);
			long duration = (System.nanoTime() - start) / 1_000_000;
			//System.out.println("Result: " + sum);
			if (duration < fastest)
				fastest = duration;
		}
		return fastest;
	}

}
