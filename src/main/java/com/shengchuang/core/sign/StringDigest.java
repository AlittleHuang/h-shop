package com.shengchuang.core.sign;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;

import org.apache.commons.codec.binary.Hex;

import com.alibaba.druid.util.HexBin;

public class StringDigest {

	private final String source;
	private String algorithm;
	private String charset;

	public StringDigest(String source, String algorithm) {
		this.source = source;
		this.algorithm = algorithm;
	}

	public StringDigest(String source, String algorithm, String charset) {
		this.source = source;
		this.algorithm = algorithm;
		this.charset = charset;
	}

	public byte[] digest() {
		return Util.digest(source, algorithm, charset);
	}

	public String toHex() {
		return HexBin.encode(Util.digest(source, algorithm, charset));
	}

	public static byte[] md5Digest(String source) {
		return Util.digest(source, "MD5", "UTF-8");
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public void setCharset(String charsetName) {
		this.charset = charsetName;
	}

	@Override
	public String toString() {
		return toHex();
	}

	public static class Util {

		public static byte[] digest(String source, String algorithm, String charset) {
			if (charset == null) {
				return digest(source, algorithm);
			}
			try {
				return digest(source.getBytes(charset), algorithm);
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}

		public static byte[] digest(String source, String algorithm) {
			return digest(source.getBytes(), algorithm);
		}

		public static byte[] digest(byte[] source, String algorithm) {
			try {
				MessageDigest md = MessageDigest.getInstance(algorithm);
				return md.digest(source);
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			}
		}

		public static String md5(String source) {
			return Hex.encodeHexString(md5Digest(source));
		}

		public static byte[] sha1Digest(String source) {
			return digest(source, "SHA-1", "UTF-8");
		}

		public static String sha1(String source) {
			return Hex.encodeHexString(sha1Digest(source));
		}

		public static String base64Encode(byte[] source) {
			Encoder base64 = Base64.getEncoder();
			return base64.encodeToString(source);
		}

	}
}
