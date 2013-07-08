package mx.com.anzen.urban.conectors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.cert.Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

import org.apache.commons.codec.binary.Base64;

public class UrbanAirshipConnector {

	private static final String URBAN_URL_BASE = "https://go.urbanairship.com";
	private String authcStringEnc;
	public UrbanAirshipConnector() {
		// <application key>:<master secret>
		String user_pass = "YUnEi0B5QqaIfKu8r7jsxA:bd-IO2ISREuhM_0YIMdacQ";
		authcStringEnc = new String(Base64.encodeBase64(user_pass.getBytes()));
	}

	public String send(String path, String payload, String method)
			throws IOException {

		byte[] bytes = payload.getBytes();

		URL url = new URL(URBAN_URL_BASE + path);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setRequestMethod(method);
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setAllowUserInteraction(true);
		conn.setInstanceFollowRedirects(false);
		conn.setRequestProperty("charset", "UTF-8");
		conn.setRequestProperty("content-encoding", "UTF-8");
		conn.setRequestProperty("content-type", "application/json");
		conn.setRequestProperty("content-length",
				"" + Integer.toString(bytes.length));
		conn.setUseCaches(false);
		conn.setRequestProperty("authorization", "Basic " + authcStringEnc);

		OutputStream out = conn.getOutputStream();
		out.write(bytes, 0, bytes.length);
		out.flush();
		out.close();

		print_https_cert(conn);
//		Authenticator au = new Authenticator() {
//			@Override
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication("usuario",
//						"clave".toCharArray());
//			}
//		};
//
//		Authenticator.setDefault(au);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));

		String linea, response = "";
		while ((linea = in.readLine()) != null) {
			response += linea;
		}
		System.out.println(response);
		return response;
	}

	private void print_https_cert(HttpsURLConnection con) {
		if (con != null) {
			try {
				System.out.println("Response Code : " + con.getResponseCode());
				System.out.println("Cipher Suite : " + con.getCipherSuite());
				System.out.println("\n");

				Certificate[] certs = con.getServerCertificates();
				for (Certificate cert : certs) {
					System.out.println("Cert Type : " + cert.getType());
					System.out.println("Cert Hash Code : " + cert.hashCode());
					System.out.println("Cert Public Key Algorithm : "
							+ cert.getPublicKey().getAlgorithm());
					System.out.println("Cert Public Key Format : "
							+ cert.getPublicKey().getFormat());
					System.out.println("\n");
				}
			} catch (SSLPeerUnverifiedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
