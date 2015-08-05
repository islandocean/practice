package jp.gr.java_conf.islandocean.practice;

import java.util.Arrays;
import java.util.EnumMap;

public class SampleEnum {

	enum MyMonth {
		JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER,
	}

	public static void main(String[] args) {
		System.out.println("----- enum");
		Arrays.stream(MyMonth.values()).forEach(
				month -> System.out.println(month));
		System.out.println();

		EnumMap<MyMonth, String> map = new EnumMap<MyMonth, String>(
				MyMonth.class);
		map.put(MyMonth.FEBRUARY, "二月");
		map.put(MyMonth.AUGUST, "八月");
		System.out.println("----- map");
		map.forEach((key, value) -> {
			System.out.printf("key: %s , value:%s%n", key, value);
		});
		System.out.println();

		System.out.println("----- misc");
		for (MyMonth month : MyMonth.values()) {
			if (month == MyMonth.JANUARY) {
				System.out.println("m == " + month);
			}
		}
		System.out.println("map.get(MyMonth.FEBRUARY)="
				+ map.get(MyMonth.FEBRUARY));
		System.out.println("MyMonth.JULY.ordinal()=" + MyMonth.JULY.ordinal());
	}
}