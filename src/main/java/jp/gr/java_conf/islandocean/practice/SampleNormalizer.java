package jp.gr.java_conf.islandocean.practice;

import java.text.Normalizer;
import java.text.Normalizer.Form;

public class SampleNormalizer {

	public SampleNormalizer() {
	}

	public static void main(String[] args) {
		String org;
		org = "！＃＄％　＆（）「」＊＋－／＠：；＜＞？＿｛｝＝、。ａｂｃ１２３ｱｶﾞﾍﾟｰ";
		System.out.println("org=" + org);
		System.out.println("Normalizer.normalize(org, Form.NFKC)="
				+ Normalizer.normalize(org, Form.NFKC));
	}
}
