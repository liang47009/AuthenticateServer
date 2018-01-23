package com.yunfeng;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;

import org.bouncycastle.util.encoders.Base64Encoder;

public class CertTool {

	public static KeyPair getKeyPair(KeyStore keystore, String alias,
			char[] password) {
		try {
			Key key = keystore.getKey(alias, password);
			if (key instanceof PrivateKey) {
				Certificate cert = keystore.getCertificate(alias);
				PublicKey publicKey = cert.getPublicKey();
				return new KeyPair(publicKey, (PrivateKey) key);
			}
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void export(String type, String alias, char[] password,
			String outFilePath) {
		try {
			KeyStore keystore = KeyStore.getInstance(type);
			Base64Encoder encoder = new Base64Encoder();
			ClassLoader classLoader = CertTool.class.getClassLoader();
			InputStream in = classLoader.getResourceAsStream("bksmy.keystore");
			keystore.load(in, password);
			KeyPair keyPair = getKeyPair(keystore, alias, password);
			PrivateKey privateKey = keyPair.getPrivate();
			FileOutputStream writer = new FileOutputStream(
					new File(outFilePath));
			int encoded = encoder.encode(privateKey.getEncoded(), 0,
					privateKey.getEncoded().length, writer);
			System.out.println("size:" + encoded);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		export("BKS", "clientkey", "a123456".toCharArray(), "c:/");
	}
}
