package com.shengchuang.core.sign;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import org.springframework.core.io.ClassPathResource;

public interface SecurityUtil {

	/**
	 * 加密算法RSA
	 */
	public static final String ALGORITHM_RSA = "RSA";

	public static String base64Encode(byte[] source) {
		Encoder base64Encoder = Base64.getEncoder();
		return base64Encoder.encodeToString(source);
	}

	public static byte[] base64Decode(String src) {
		Decoder decoder = Base64.getMimeDecoder();
		return decoder.decode(src);
	}

	public static PrivateKey getPrivateKeyFromPKCS8(String path) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
			String key = readKeyFile(path);
			byte[] encodedKey = org.apache.commons.codec.binary.Base64.decodeBase64(key);
			return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static PublicKey getPublicKey(String path) throws InvalidKeySpecException, IOException {
		String key = readKeyFile(path);
		byte[] encodedKey = base64Decode(key);
		KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
		} catch (NoSuchAlgorithmException e) {
			/* 不会有异常 */
		}
		return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
	}

	public static String readKeyFile(String path) throws IOException {
		String str;
		ClassPathResource resource = new ClassPathResource(path);
		File file = resource.getFile();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String s = br.readLine();
			str = "";
			s = br.readLine();
			while (s.charAt(0) != '-') {
				str += s + "\n";
				s = br.readLine();
			}
			return str;
		}
	}

}