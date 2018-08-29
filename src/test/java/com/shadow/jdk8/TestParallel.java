package com.shadow.jdk8;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.junit.Test;

public class TestParallel {

	@Test
	public void test() {

		System.out.println(1_000_000);

	}


	@Test
	public void test1() {

		System.out.println("processors : "+Runtime.getRuntime().availableProcessors());
		
		System.out.println("Sequential sum done in:" + measureSumPerf(ParallelStreams::sequentialSum, 10_000_000) + " msecs");
		
		System.out.println("Iterative sum done in:" +
				measureSumPerf(ParallelStreams::iterativeSum, 10_000_000) + " msecs");
		
		System.out.println("Parallel sum done in: " +
				measureSumPerf(ParallelStreams::parallelSum, 10_000_000) + " msecs" );
		
		System.out.println("range sum done in:" +
				measureSumPerf(ParallelStreams::rangedSum, 10_000_000) +
				" msecs");
		
		System.out.println("Parallel range sum done in:" +
				measureSumPerf(ParallelStreams::parallelRangedSum, 10_000_000) +
				" msecs");
		
	}
	
	
	private int countWords(Stream<Character> stream) {
		
		WordCounter wordCounter = stream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
		
		return wordCounter.getCounter();
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
