package jp.gr.java_conf.islandocean.practice;

import java.util.HashMap;

public class SampleStringMap {

	public SampleStringMap() {
	}

	public static void main(String[] args) {

		String[] array = "a,b,a,z".split(",");
		System.out.println("String[] array = \"a,b,a,z\".split(\",\");");
		for (int i = 0; i < array.length; ++i) {
			System.out.println("array[" + i + "]=" + array[i]);
		}

		System.out.println();

		if (array[0] == array[2]) {
			System.out.println("array[0]" + "==" + "array[2]");
		} else {
			System.out.println("array[0]" + "!=" + "array[2]");
		}

		System.out.println();

		HashMap map = new HashMap();
		map.put(array[0], "foobar");
		System.out.println("map.put(array[0], \"foobar\");");
		System.out.println("(String) map.get(array[2])="
				+ (String) map.get(array[2]));
	}
}
