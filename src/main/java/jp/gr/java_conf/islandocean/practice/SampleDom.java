package jp.gr.java_conf.islandocean.practice;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;

public class SampleDom {

	public static void main(String[] args) {

		System.out.println("user.home=" + System.getProperty("user.home"));

		String address = "http://www.sony.co.jp/";
		// String address = "http://www.ntt.co.jp/";
		// String address = "http://www.japan-reit.com/list/rimawari/";
		List<String> list = null;
		try {
			list = IOUtil
					.readRemoteText(address, StandardCharsets.UTF_8.name());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(1);
		}
		for (String str : list) {
			System.out.println(str);
		}

		try {
			DOMParser parser = new DOMParser();
			System.out.println("new DOMParser() success.");

			// parser.parse(str);
			parser.parse(address);
			System.out.println("parser.parse() success.");

			Document doc = parser.getDocument();
			System.out.println("parser.getDocument() success.");

			System.out.println("doc=" + doc);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
