package foo.bar.jwt.util;

import javax.crypto.SecretKey;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

public class GenerateToken {

	public static void main(String[] args) {
		
		SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512); 

		String base64Key = Encoders.BASE64.encode(key.getEncoded());

		System.out.println(base64Key);

	}
}
