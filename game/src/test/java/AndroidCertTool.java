import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.Enumeration;

public class AndroidCertTool {
	// Android SSL BKS证书生成, 以及PFX与JKS证书的转换
	//
	// 一.Android SSL BKS 证书生成过程
	//
	// 　　1.生成服务器jks证书:
	//
	// keytool -genkey -alias server -keystore server.jks
	// 　　2.导出cert证书:
	//
	// keytool -exportcert -alias peer -file peer.cert -keystore peer.jks
	// 　　3.生成Android客户端bks密钥库
	//
	// 　　需要用到 bcprov-ext-jdk15on-151.jar ,
	// 官网:http://www.bouncycastle.org/latest_releases.html
	//
	// 　　将jar包放到 %JAVA_HOME%\jre\lib\security
	//
	// 　　生成私钥库
	//
	// keytool -importcert -keystore keyStore.bks -file peer.cert -storetype BKS
	// -provider org.bouncycastle.jce.provider.BouncyCastleProvider
	// 　　生成公钥库
	//
	// keytool -importcert -trustcacerts -keystore trustStore.bks -file
	// peer.cert -storetype BKS -provider
	// org.bouncycastle.jce.provider.BouncyCastleProvider
	//
	//
	// 　　4.查看密钥库详情
	//
	// keytool -list -v -keystore keyStore.bks -storepass 123456 -storetype BKS
	//
	//
	//
	//
	// 二.PFX与JKS证书的转换
	//
	// 　　工具类:
	//
	// 复制代码
	//
	// public class CertificateConvertUtil {
	//
	// public static final String PKCS12 = "PKCS12";
	// public static final String JKS = "JKS";
	//
	// /**
	// * pkcs12 转 jks
	// * @param input_keystore_file pkcs12证书路径
	// * @param keystore_password pkcs12证书密钥库口令
	// * @param ouput_keystore_file jks证书路径
	// */
	public static void PKCS12ToJKS(String input_keystore_file,
			String keystore_password, String ouput_keystore_file) {
		try {
			KeyStore inputKeyStore = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(input_keystore_file);

			char[] nPassword = null;
			if ((keystore_password == null)
					|| keystore_password.trim().equals("")) {
				nPassword = null;
			} else {
				nPassword = keystore_password.toCharArray();
			}

			inputKeyStore.load(fis, nPassword);
			fis.close();

			System.out.println("keystore type=" + inputKeyStore.getType());

			KeyStore outputKeyStore = KeyStore.getInstance("JKS");

			outputKeyStore.load(null, nPassword);

			Enumeration<String> enums = inputKeyStore.aliases();
			while (enums.hasMoreElements()) {

				String keyAlias = (String) enums.nextElement();
				System.out.println("alias=[" + keyAlias + "]");

				if (inputKeyStore.isKeyEntry(keyAlias)) {
					Key key = inputKeyStore.getKey(keyAlias, nPassword);
					Certificate[] certChain = inputKeyStore
							.getCertificateChain(keyAlias);
					outputKeyStore.setKeyEntry(keyAlias, key, nPassword,
							certChain);
				}
				FileOutputStream out = new FileOutputStream(ouput_keystore_file);
				outputKeyStore.store(out, nPassword);
				out.close();
				outputKeyStore.deleteEntry(keyAlias);

				System.out.println("convert is finished!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//
	// /**
	// * jks 转 pkcs12
	// * @param input_keystore_file jks证书路径
	// * @param keystore_password jks证书密钥库口令
	// * @param ouput_keystore_file pkcs12证书路径
	// */
	public static void JKSToPKCS12(String input_keystore_file,
			String keystore_password, String ouput_keystore_file) {
		try {
			KeyStore inputKeyStore = KeyStore.getInstance("JKS");
			InputStream in = AndroidCertTool.class.getClassLoader().getResourceAsStream(
					input_keystore_file);
//			FileInputStream fis = new FileInputStream(input_keystore_file);

			char[] nPassword = null;
			if ((keystore_password == null)
					|| keystore_password.trim().equals("")) {
				nPassword = null;
			} else {
				nPassword = keystore_password.toCharArray();
			}

			inputKeyStore.load(in, nPassword);
			in.close();

			System.out.println("keystore type=" + inputKeyStore.getType());

			KeyStore outputKeyStore = KeyStore.getInstance("PKCS12");

			outputKeyStore.load(null, nPassword);

			Enumeration<String> enums = inputKeyStore.aliases();
			while (enums.hasMoreElements()) {
				String keyAlias = (String) enums.nextElement();
				System.out.println("alias=[" + keyAlias + "]");

				if (inputKeyStore.isKeyEntry(keyAlias)) {
					Key key = inputKeyStore.getKey(keyAlias, nPassword);
					Certificate[] certChain = inputKeyStore
							.getCertificateChain(keyAlias);
					outputKeyStore.setKeyEntry(keyAlias, key, nPassword,
							certChain);
				}
				FileOutputStream out = new FileOutputStream(ouput_keystore_file);
				outputKeyStore.store(out, nPassword);
				out.close();
				outputKeyStore.deleteEntry(keyAlias);

				System.out.println("convert is finished!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		JKSToPKCS12("server.jks", "a123456", "c:/server.pfx");
	}
	// 复制代码
	// 　　使用工具类进行转换时, 会将 证书Alias 输出到控制台.
	//
	// 　　测试代码:
	//
}
